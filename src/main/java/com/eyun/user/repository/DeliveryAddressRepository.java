package com.eyun.user.repository;

import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Map;


/**
 * Spring Data JPA repository for the DeliveryAddress entity.
 * @author Jack_wen
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>, JpaSpecificationExecutor<DeliveryAddress> {


    @Query(value = " SELECT ifnull(d.city,\"\") AS city, ifnull(d.phone,\"\") AS phone,ifnull(d.contact,\"\") AS contact,ifnull(d.id,\"\") AS id,isnull(d.default_address) AS default_address   FROM delivery_address AS d LEFT JOIN user_annex u ON d.user_annex_id=u.id WHERE u.id=:id",nativeQuery = true)
    List<Map> findDeliveryAddressList(@Param("id")Long id);


    @Query(value = "DELETE FROM delivery_address WHERE id = :id AND user_annex_id= :userId",nativeQuery = true)
    void deleteAddress(@Param("id")Long id,@Param("id")Long userId);

}
