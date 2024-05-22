package com.bookingdetailsservice.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetails {

	@Id
	private long bookingId;
	private String name;
	private int roomno;
	private String hotelname;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date booked_from;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date booked_to;
	private int no_of_adults;
	private int no_of_children;
	private double amount;
	private String paymentStatus;

}