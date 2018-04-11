package com.eyun.user.repository;

import com.eyun.user.domain.Mercury;
import com.eyun.user.service.dto.MercuryDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Mercury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MercuryRepository extends JpaRepository<Mercury, Long>, JpaSpecificationExecutor<Mercury>{


    @Query(nativeQuery = true, value = "SELECT * from mercury ")
    List<MercuryDTO> findNearMerchantsList();
}
