package com.eyun.user.repository;

import com.eyun.user.domain.MercuryStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MercuryStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MercuryStatusRepository extends JpaRepository<MercuryStatus, Long> {

}
