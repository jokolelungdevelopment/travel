package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.Inbox;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Inbox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InboxRepository extends JpaRepository<Inbox, Long> {

    @Query("select inbox from Inbox inbox where inbox.sender.login = ?#{principal.username}")
    List<Inbox> findBySenderIsCurrentUser();

    @Query("select inbox from Inbox inbox where inbox.receiver.login = ?#{principal.username}")
    List<Inbox> findByReceiverIsCurrentUser();

}
