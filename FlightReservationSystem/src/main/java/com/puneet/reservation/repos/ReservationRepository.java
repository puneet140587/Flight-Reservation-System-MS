package com.puneet.reservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.reservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
