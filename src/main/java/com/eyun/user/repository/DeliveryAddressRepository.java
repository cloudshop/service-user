package com.eyun.user.repository;

import com.eyun.user.domain.DeliveryAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeliveryAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

}
