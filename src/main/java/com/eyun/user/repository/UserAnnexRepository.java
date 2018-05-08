package com.eyun.user.repository;

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.service.dto.UserAnnexDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Spring Data JPA repository for the UserAnnex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAnnexRepository extends JpaRepository<UserAnnex, Long>, JpaSpecificationExecutor<UserAnnex> {




    @Query(value = "select ifnull(s.name,\"\") AS name, ifnull(s.phone,\"\") AS phone from user_annex AS s where s.id=:id",nativeQuery = true)
    List<Map> shareUserList(@Param("id")Long id);







    UserAnnex findByid(Long id);

    List<UserAnnex>  findByinviterId(Long inviterId);

    @Query(value = "SELECT u.id FROM mercury AS m LEFT JOIN owner_relation o ON m.id=o.user_annex_id  LEFT JOIN user_annex u ON u.id=o.user_annex_id where m.id=:id",nativeQuery = true)
    Long ShopIdFindByUserid(@Param("id")Long id);
}
