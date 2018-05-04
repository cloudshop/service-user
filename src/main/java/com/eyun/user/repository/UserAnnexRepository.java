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

    List<UserAnnexDTO>  findByinviterId(Long inviterId);
}
