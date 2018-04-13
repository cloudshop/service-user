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


    @Query(nativeQuery = true, value = " SELECT  m.city,m.provice,m.img_license,m.img_facade ,m.img_introduces from  mercury AS m where langitude > :minlngInt and langitude < :maxlngInt  OR lantitude > :minlatInt  and  lantitude < :maxlatInt")
    List<MercuryDTO> findNearMerchantsList(@Param("minlngInt")double minlngInt, @Param("maxlngInt")double maxlngInt,
                                           @Param("minlatInt")double minlatInt, @Param("maxlatInt")double maxlatInt);





    @Query(nativeQuery = true,value = "SELECT m.status FROM mercury AS m LEFT JOIN owner_relation o ON m.id=o.user_annex_id  LEFT JOIN user_annex u ON u.id=o.user_annex_id where u.id=:id")
    MercuryDTO checkMercuryStatus(@Param("id")Long id);
}
