package com.puneet.reservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.reservation.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
