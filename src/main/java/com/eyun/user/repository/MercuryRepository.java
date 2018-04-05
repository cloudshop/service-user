package com.eyun.user.repository;

import com.eyun.user.domain.Mercury;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mercury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MercuryRepository extends JpaRepository<Mercury, Long> {

}
