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


    @Query(nativeQuery = true, value = " SELECT ifnull(m.city,\"\")  from  mercury AS m where langitude > :minlng and langitude < :maxlng  OR lantitude > :minlat  and  lantitude < :maxlat")
    List<MercuryDTO> findNearMerchantsList(@Param("minlng")double minlng, @Param("maxlng")double maxlng,
                                           @Param("minlat")double minlat, @Param("maxlat")double maxlat);


    @Query(nativeQuery = true,value = "SELECT m.status FROM mercury AS m LEFT JOIN owner_relation o ON m.id=o.user_annex_id  LEFT JOIN user_annex u ON u.id=o.user_annex_id where u.id=:id")
    Map checkMercuryStatus(@Param("id")Long id);


    @Query(nativeQuery = true, value = "SELECT m.name,m.city,m.img_license,m.id,SQRT( POW(111.2 * (lantitude - :Lantitude ), 2) + POW(111.2 * (:Langitude - langitude) * COS(lantitude / 57.3), 2))   AS distance FROM mercury AS m HAVING distance < 25 ORDER BY distance")
    List<Map> findNearMerchants(@Param("Langitude")Double Langitude, @Param("Lantitude")Double Lantitude);

    @Query(nativeQuery = true,value = "SELECT m.id FROM mercury AS m LEFT JOIN owner_relation o ON m.id=o.mercury_id LEFT JOIN user_annex u ON u.id=o.user_annex_id where u.id=:id")
    Map getUserIdMercuryId(@Param("id")Long id);
}
