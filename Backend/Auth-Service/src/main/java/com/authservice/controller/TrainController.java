package com.authservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.constant.TrainConstant;
import com.authservice.entity.Traveller;
import com.authservice.proxyentity.train.Train;
import com.authservice.proxyentity.train.TrainBookingDetails;
import com.authservice.proxyentity.train.TrainProxyController;

@RestController
@RequestMapping(TrainConstant.TRAIN)
@CrossOrigin(TrainConstant.CROSS_ORIGIN)
public class TrainController {

	@Autowired
	private TrainProxyController trainProxy;

	@GetMapping(TrainConstant.GET_ALL_TRAIN)
	public ResponseEntity<List<Train>> getAllTrain() {
		return trainProxy.getAllTrain();
	}

	@GetMapping(TrainConstant.GET_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<Optional<Train>> getTrainByTrainId(@PathVariable long TrainId) {
		return trainProxy.getTrainByTrainId(TrainId);
	}

	@PutMapping(TrainConstant.ADD_SEATS_BY_TRAIN_ID)
	public ResponseEntity<Train> addSeatsByTrainId(@PathVariable long TrainId) {
		return trainProxy.addSeatsByTrainId(TrainId);
	}

	@PostMapping(TrainConstant.SAVE_TRAIN)
	public ResponseEntity<Train> saveTrain(@RequestBody Train Train) {
		return trainProxy.saveTrain(Train);
	}

	@PostMapping(TrainConstant.BOOK_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<TrainBookingDetails> bookTrain(@PathVariable long id, @RequestBody List<Traveller> travellers,
			@PathVariable String seatType, @PathVariable String boardingStation, @PathVariable String username) {
		return trainProxy.bookTrain(id, travellers, seatType, boardingStation, username);
	}

	@GetMapping(TrainConstant.GET_TRAIN_BOOKING_DETAILS_BY_ID)
	public ResponseEntity<TrainBookingDetails> getTrainBookingDetailsByTrainBookingId(@PathVariable long id) {
		return trainProxy.getTrainBookingDetailsByTrainBookingId(id);
	}

	@GetMapping(TrainConstant.GET_TRAIN_BOOKING_DETAILS_BY_USERNAME)
	public ResponseEntity<List<TrainBookingDetails>> getTrainBookingDetailsByUserName(@PathVariable String username) {
		return trainProxy.getTrainBookingDetailsByUserName(username);
	}

	@PutMapping(TrainConstant.UPDATE_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<Train> updateTrainByTrainId(@PathVariable long TrainId, @RequestBody Train Train) {
		return trainProxy.updateTrainByTrainId(TrainId, Train);
	}

	@DeleteMapping(TrainConstant.DELETE_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<String> deleteTrainByTrainId(@PathVariable long TrainId) {
		return trainProxy.deleteTrainByTrainId(TrainId);

	}

	@PutMapping(TrainConstant.CANCEL_PAYMENT_BY_TRAIN_BOOKING_ID)
	public ResponseEntity<TrainBookingDetails> cancelPaymentByTrainBookingId(@PathVariable long TrainId) {
		return trainProxy.cancelPaymentByTrainBookingId(TrainId);
	}

	@GetMapping(TrainConstant.GET_ALL_CITY_NAMES)
	public ResponseEntity<List<List<String>>> getAllCityNames() {
		return trainProxy.getAllCityNames();
	}

	@GetMapping(TrainConstant.PAYMENT_STATUS_CHANGE_BY_TRAIN_BOOKING_ID)
	public ResponseEntity<TrainBookingDetails> paymentStatusChangeByTrainBookingId(@PathVariable long bookingid) {
		return trainProxy.paymentStatusChangeByTrainBookingId(bookingid);
	}

	@GetMapping(TrainConstant.GET_ALL_AVAILABLE_TRAINS)
	public ResponseEntity<List<Train>> getAllAvailableTrains(@PathVariable String from, @PathVariable String to,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date departure) {
		return trainProxy.getAllAvailableTrains(from, to, departure);
	}
}
