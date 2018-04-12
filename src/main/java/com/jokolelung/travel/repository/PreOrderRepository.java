package com.jokolelung.travel.repository;

import com.jokolelung.travel.domain.PreOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PreOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreOrderRepository extends JpaRepository<PreOrder, Long> {

    @Query("select pre_order from PreOrder pre_order where pre_order.user.login = ?#{principal.username}")
    List<PreOrder> findByUserIsCurrentUser();

}
