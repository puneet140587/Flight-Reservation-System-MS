package com.puneet.reservation.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.puneet.reservation.dto.ReservationRequest;
import com.puneet.reservation.entities.Flight;
import com.puneet.reservation.entities.Reservation;
import com.puneet.reservation.repos.FlightRepository;
import com.puneet.reservation.services.ReservationService;

@Controller
public class ReservationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/showCompleteReservation")
	public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap ) {
		
		LOGGER.info("Inside showCompleteReservation() invoked with flight Id: "+ flightId );
		
		Flight flight = flightRepository.findById(flightId).get();  //--> Either of these repository method will work as per Spring 5 
		//Flight flight = flightRepository.findById(flightId).orElse(null);
		modelMap.addAttribute("flight", flight);
		
		LOGGER.info("Flight is:" + flight);
		
		return "completeReservation";
	}
	
	@PostMapping(value = "/completeReservation")
	public String completeReservation (ReservationRequest request, ModelMap  modelMap) {
		
		LOGGER.info("Inside completeReservation() " + request );
		
		Reservation reservation = reservationService.bookFlight(request);
		modelMap.addAttribute("msg", "Reservation Created Successfully with id "+ reservation.getId());
		return "reservationConfirmation";
	}
	
	
	

}
