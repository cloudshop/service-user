package com.eyun.user.repository;

import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the DeliveryAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>, JpaSpecificationExecutor<DeliveryAddress> {



    @Query(value = " SELECT * FROM delivery_address AS d LEFT JOIN user_annex u ON d.user_annex_id=u.id WHERE u.id:id",nativeQuery = true)
    List<DeliveryAddressDTO> findDeliveryAddressList(@Param("id") Long id);

}
