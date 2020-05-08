package com.puneet.reservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.reservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
