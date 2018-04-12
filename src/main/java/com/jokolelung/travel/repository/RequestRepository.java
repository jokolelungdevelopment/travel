package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.Request;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select request from Request request where request.user.login = ?#{principal.username}")
    List<Request> findByUserIsCurrentUser();

}
