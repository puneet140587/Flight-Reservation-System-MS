package com.puneet.reservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puneet.reservation.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

}
 