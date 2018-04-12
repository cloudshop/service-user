package com.eyun.user.repository;

import com.eyun.user.domain.Mercury;
import com.eyun.user.service.dto.MercuryDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Map;


/**
 * Spring Data JPA repository for the Mercury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MercuryRepository extends JpaRepository<Mercury, Long>, JpaSpecificationExecutor<Mercury>{


    @Query(nativeQuery = true, value = " SELECT  name,cityfrom  mercury where langitude > :minlng and langitude < :maxlng  and lantitude > :minlat  and  lantitude < :maxlat")
    List<MercuryDTO> findNearMerchantsList(@Param("minlat")double minlat, @Param("maxlat")double maxlat,
                                    @Param("minlng")double minlng, @Param("maxlng")double maxlng);
}
