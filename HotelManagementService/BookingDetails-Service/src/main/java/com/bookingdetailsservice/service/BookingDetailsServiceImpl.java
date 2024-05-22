package com.bookingdetailsservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingdetailsservice.entity.BookingDetails;
import com.bookingdetailsservice.entity.HotelRooms;
import com.bookingdetailsservice.exception.BookingDetailsNotFoundException;
import com.bookingdetailsservice.externalclass.Hotel;
import com.bookingdetailsservice.externalclass.Room;
import com.bookingdetailsservice.proxy.RoomProxy;
import com.bookingdetailsservice.repository.BookingDetailsRepository;
import com.bookingdetailsservice.repository.HotelRepository;
import com.bookingdetailsservice.repository.RoomRepository;

@Service
public class BookingDetailsServiceImpl implements BookingDetailsService {

	@Autowired
	BookingDetailsRepository bookingrepo;

	@Autowired
	RoomRepository roomrepo;
	@Autowired
	RoomProxy rproxy;
	@Autowired
	HotelRepository hrepo;

	@Override
	public BookingDetails BookRoom(String name, BookingDetails bookingdetails) {

		int count = bookingrepo.findAll().size();
		long MIN_ID = 100000;
		bookingdetails.setBookingId(count == 0 ? MIN_ID : MIN_ID + count);

		bookingdetails.setName(name);
		long daysBetween = 0;
		daysBetween = DateUtils.daysBetween(bookingdetails.getBooked_from(), bookingdetails.getBooked_to());
		System.out.println("Days between: " + daysBetween);
		double amt = hrepo.findAll().stream()
				.filter(h -> h.getHotelName().equalsIgnoreCase(bookingdetails.getHotelname()))
				.collect(Collectors.toList()).get(0).getRooms().stream()
				.filter(r -> r.getRoom_no() == bookingdetails.getRoomno()).collect(Collectors.toList()).get(0)
				.getRate_per_day();

		bookingdetails.setAmount(daysBetween * amt);
		bookingdetails.setPaymentStatus("Payment has to be done");
		return bookingrepo.save(bookingdetails);

	}

	@Override
	public String removeBookingDetails(long bookingId) {
		if (bookingrepo.findByBookingId(bookingId).isPresent()) {
			bookingrepo.deleteByBookingId(bookingId);
			return "Booking details are deleted";
		} else
			throw new BookingDetailsNotFoundException("Booking details are not found");
	}

	@Override
	public List<BookingDetails> showAllBookingDetails() {
		List<BookingDetails> list = bookingrepo.findAll();
		if (bookingrepo.findAll().isEmpty())
			throw new BookingDetailsNotFoundException("Booking details are not found");
		return list;
	}

	@Override
	public BookingDetails showBookingDetailsbyId(long bookingId) {
		if (bookingrepo.findByBookingId(bookingId).isPresent()) {
			BookingDetails bd = bookingrepo.findByBookingId(bookingId).get();
			return bd;
		} else
			throw new BookingDetailsNotFoundException("Booking details are not found");
	}

	@Override
	public BookingDetails paymentstatuschange(long bookingId) {
		if (bookingrepo.findByBookingId(bookingId).isPresent()) {
			BookingDetails bd = bookingrepo.findByBookingId(bookingId).get();
			bd.setPaymentStatus("Payment done");
			return bookingrepo.save(bd);
		} else
			throw new BookingDetailsNotFoundException("Booking details are not found");

	}

	@Override
	public List<HotelRooms> AvailableRoom(String roomType, String city, Date fromDate, Date toDate) {
		String roomtype = roomType.replace("_", " ");
		List<Hotel> hotels = hrepo.findAll().stream().filter(h -> h.getCity().equalsIgnoreCase(city))
				.collect(Collectors.toList());

		List<HotelRooms> availableRooms = new ArrayList<>();

		for (Hotel hotel : hotels) {
			
			List<Room> rooms = hotel.getRooms().stream()
					.filter(r -> r.getRoomtype().equalsIgnoreCase(roomtype) && isRoomAvailable(r, fromDate, toDate))
					.collect(Collectors.toList());
			if(!rooms.isEmpty()) {
				HotelRooms hotelRooms=new HotelRooms();
				hotelRooms.setAddress(hotel.getAddress());
				hotelRooms.setDescription(hotel.getDescription());
				hotelRooms.setHotelName(hotel.getHotelName());
				hotelRooms.setRooms(rooms);
				availableRooms.add(hotelRooms);
			}
		
		}

		return availableRooms;
	}

	private boolean isRoomAvailable(Room room, Date fromDate, Date toDate) {

		for (BookingDetails booking : bookingrepo.findAll()) {
			Date bookedFrom = booking.getBooked_from();
			Date bookedTo = booking.getBooked_to();
			if ((fromDate.before(bookedTo) || fromDate.equals(bookedTo))
					&& (toDate.after(bookedFrom) || toDate.equals(bookedFrom))) {
				return false;
			}
		}

		return true;
	}

}