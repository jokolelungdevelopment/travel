package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.Discussion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Discussion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    @Query("select discussion from Discussion discussion where discussion.user.login = ?#{principal.username}")
    List<Discussion> findByUserIsCurrentUser();

}
