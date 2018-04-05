package com.eyun.user.repository;

import com.eyun.user.domain.MercuryStatusHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MercuryStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MercuryStatusHistoryRepository extends JpaRepository<MercuryStatusHistory, Long> {

}
