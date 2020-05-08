package com.puneet.reservation.services;

import com.puneet.reservation.dto.ReservationRequest;
import com.puneet.reservation.entities.Reservation;

public interface ReservationService {
	
	public Reservation bookFlight(ReservationRequest request);

}
