package com.authservice.proxyentity.train;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.authservice.constant.TrainConstant;
import com.authservice.entity.Traveller;

@FeignClient(name = TrainConstant.SERVICE, url = TrainConstant.URL)
public interface TrainProxyController {

	@GetMapping(TrainConstant.GET_ALL_TRAIN)
	public ResponseEntity<List<Train>> getAllTrain();

	@GetMapping(TrainConstant.GET_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<Optional<Train>> getTrainByTrainId(@PathVariable long TrainId);

	@PutMapping(TrainConstant.ADD_SEATS_BY_TRAIN_ID)
	public ResponseEntity<Train> addSeatsByTrainId(@PathVariable long TrainId);

	@PostMapping(TrainConstant.SAVE_TRAIN)
	public ResponseEntity<Train> saveTrain(@RequestBody Train Train);

	@PostMapping(TrainConstant.BOOK_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<TrainBookingDetails> bookTrain(@PathVariable long id, @RequestBody List<Traveller> travellers,
			@PathVariable String seatType, @PathVariable String boardingStation, @PathVariable String username);

	@GetMapping(TrainConstant.GET_TRAIN_BOOKING_DETAILS_BY_ID)
	public ResponseEntity<TrainBookingDetails> getTrainBookingDetailsByTrainBookingId(@PathVariable long id);

	@GetMapping(TrainConstant.GET_TRAIN_BOOKING_DETAILS_BY_USERNAME)
	public ResponseEntity<List<TrainBookingDetails>> getTrainBookingDetailsByUserName(@PathVariable String username);

	@PutMapping(TrainConstant.UPDATE_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<Train> updateTrainByTrainId(@PathVariable long TrainId, @RequestBody Train Train);

	@DeleteMapping(TrainConstant.DELETE_TRAIN_BY_TRAIN_ID)
	public ResponseEntity<String> deleteTrainByTrainId(@PathVariable long TrainId);

	@PutMapping(TrainConstant.CANCEL_PAYMENT_BY_TRAIN_BOOKING_ID)
	public ResponseEntity<TrainBookingDetails> cancelPaymentByTrainBookingId(@PathVariable long TrainId);

	@GetMapping(TrainConstant.GET_ALL_CITY_NAMES)
	public ResponseEntity<List<List<String>>> getAllCityNames();

	@GetMapping(TrainConstant.PAYMENT_STATUS_CHANGE_BY_TRAIN_BOOKING_ID)
	public ResponseEntity<TrainBookingDetails> paymentStatusChangeByTrainBookingId(@PathVariable long bookingid);

	@GetMapping(TrainConstant.GET_ALL_AVAILABLE_TRAINS)
	public ResponseEntity<List<Train>> getAllAvailableTrains(@PathVariable String from, @PathVariable String to,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date departure);
}
