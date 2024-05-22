package com.bookingdetailsservice.entity;

import java.util.List;

import com.bookingdetailsservice.externalclass.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRooms {
	private String hotelName;
	private String address;
	private String description;
	private List<Room> rooms;
}
