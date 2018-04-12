package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.Messages;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Messages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {

    @Query("select messages from Messages messages where messages.user.login = ?#{principal.username}")
    List<Messages> findByUserIsCurrentUser();

}
