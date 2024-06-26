package com.trainservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TrainSeats {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seatId;
	private String seatNo;
	private String seatType;
	private double seatPrice;
	private String coachNo;
	private boolean bookingStatus;
}
