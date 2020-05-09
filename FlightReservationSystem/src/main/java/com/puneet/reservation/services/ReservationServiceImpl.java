package com.puneet.reservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puneet.reservation.dto.ReservationRequest;
import com.puneet.reservation.entities.Flight;
import com.puneet.reservation.entities.Passenger;
import com.puneet.reservation.entities.Reservation;
import com.puneet.reservation.repos.FlightRepository;
import com.puneet.reservation.repos.PassengerRepository;
import com.puneet.reservation.repos.ReservationRepository;
import com.puneet.reservation.util.EmailUtil;
import com.puneet.reservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

	
	@Value("${com.puneet.reservation.itinerary.dirpath}")
	private String ITINERARY_DIR; 

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	
	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {
		
		LOGGER.info("Inside bookFlight()");
		
		Long flightId = request.getFlightId();
		
		LOGGER.info("Fetching the flightId: " + flightId);
		
		Flight flight = flightRepository.findById(flightId).get();
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		
		LOGGER.info("Saving the passenger " + passenger);
		
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		
		LOGGER.info("Saving the Reservation " + reservation);
		
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = ITINERARY_DIR + savedReservation.getId()+".pdf";
		
		LOGGER.info("Generating the itenarary");
		
		pdfGenerator.generateItinerary(savedReservation, filePath);
		
		LOGGER.info("Emailing the itenarary");
		
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
	
		return savedReservation;
	}

}
