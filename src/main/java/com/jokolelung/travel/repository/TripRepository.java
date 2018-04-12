package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.Trip;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Trip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("select trip from Trip trip where trip.user.login = ?#{principal.username}")
    List<Trip> findByUserIsCurrentUser();

}
