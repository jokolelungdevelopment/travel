package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.Offer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select offer from Offer offer where offer.user.login = ?#{principal.username}")
    List<Offer> findByUserIsCurrentUser();

}
