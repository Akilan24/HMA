package com.hotelservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hotelservice.entity.Hotel;
import com.hotelservice.exception.HotelDetailsNotFoundException;
import com.hotelservice.repository.HotelRepository;
import com.hotelservice.service.HotelServiceImpl;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

	@Mock
	private HotelRepository hotelRepository;

	@InjectMocks
	private HotelServiceImpl hotelService;

	private Hotel hotel;

	private Hotel updatedhotel;

	@BeforeEach
	void setUp() {
		hotel = new Hotel(123456, "Bangalore", "Hotel1", "123 Main Street", "Hotel Description", "hotel@example.com",
				"9876543210", "8765432109", "https://www.hotelwebsite.com", "image", null);
		updatedhotel = new Hotel(123456, "Bangalore", "Hotel", "123 Main Street", "Hotel Description",
				"hotel@example.com", "9876548455", "8765474210", "https://www.hotelwebsite.com", "image", null);

	}

	@Test
	void testGetHotelsSuccess() {
		List<Hotel> hotels = new ArrayList<>();
		hotels.add(hotel);
		when(hotelRepository.findAll()).thenReturn(hotels);
		assertEquals(hotels, hotelService.getAllHotel());
	}

	@Test
	void testGetHotelsNotFound() {
		when(hotelRepository.findAll()).thenReturn(new ArrayList<>());
		assertThrows(HotelDetailsNotFoundException.class, () -> hotelService.getAllHotel());
	}

	@Test
	void testGetHotelByHotelIdSuccess() {
		when(hotelRepository.findByHotelId(123456)).thenReturn(Optional.of(hotel));
		assertEquals(hotel, hotelService.getHotelByHotelId(123456));
	}

	@Test
	void testGetHotelByHotelIdNotFound() {
		when(hotelRepository.findByHotelId(123456)).thenReturn(Optional.empty());
		assertThrows(HotelDetailsNotFoundException.class, () -> hotelService.getHotelByHotelId(123456));
	}

	@Test
	void testAddHotelSuccess() throws Exception {
		when(hotelRepository.save(hotel)).thenReturn(hotel);
		assertEquals(hotel, hotelService.addHotel(hotel));
	}

	@Test
	void testAddHotelFailure() throws Exception {
		when(hotelRepository.save(hotel)).thenThrow(new RuntimeException());
		assertThrows(Exception.class, () -> hotelService.addHotel(hotel));
	}

	@Test
	void testUpdateHotelSuccess() {
		when(hotelRepository.findByHotelId(123456)).thenReturn(Optional.of(hotel));
		when(hotelRepository.save(hotel)).thenReturn(updatedhotel);
		assertEquals(updatedhotel, hotelService.updateHotel(hotel));
	}

	@Test
	void testUpdateHotelNotFound() {
		when(hotelRepository.findByHotelId(123456)).thenReturn(Optional.empty());
		assertThrows(HotelDetailsNotFoundException.class, () -> hotelService.updateHotel(hotel));
	}

	@Test
	void testDeleteHotelSuccess() {
		when(hotelRepository.findByHotelId(123456)).thenReturn(Optional.of(hotel));
		assertEquals("Hotel details are deleted", hotelService.deleteHotelByHotelId(123456));
	}

	@Test
	void testDeleteHotelNotFound() {
		when(hotelRepository.findByHotelId(123456)).thenReturn(Optional.empty());
		assertThrows(HotelDetailsNotFoundException.class, () -> hotelService.deleteHotelByHotelId(123456));
	}

	@Test
	void testGetHotelByHotelNameSuccess() {
		when(hotelRepository.findByHotelName("Hotel")).thenReturn(Optional.of(hotel));
		assertEquals(hotel, hotelService.getHotelByHotelName("Hotel"));
	}

	@Test
	void testGetHotelByHotelNameNotFound() {
		when(hotelRepository.findByHotelName("Hotel")).thenReturn(Optional.empty());
		assertThrows(HotelDetailsNotFoundException.class, () -> hotelService.getHotelByHotelName("Hotel"));
	}

	@Test
	void testGetHotelByCitySuccess() {
		List<Hotel> hotels = new ArrayList<>();
		hotels.add(new Hotel(123456, "Bangalore", "Hotel1", "123 Main Street", "Hotel Description", "hotel@example.com",
				"9876543210", "8765432109", "https://www.hotelwebsite.com", "image", null));
		hotels.add(new Hotel(123457, "Bangalore", "Hotel1", "123 Main Street", "Hotel Description", "hotel@example.com",
				"9876543210", "8765432109", "https://www.hotelwebsite.com", "image", null));
		when(hotelRepository.findAllByCity("City")).thenReturn(Optional.of(hotels));
		assertEquals(hotels, hotelService.getHotelNamesByCity("City"));
	}

	@Test
	void testGetHotelByCityNotFound() {
		when(hotelRepository.findAllByCity("City")).thenReturn(Optional.empty());
		assertThrows(HotelDetailsNotFoundException.class, () -> hotelService.getHotelNamesByCity("City"));
	}
}
