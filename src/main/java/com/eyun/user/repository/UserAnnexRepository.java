package com.eyun.user.repository;

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.service.dto.UserAnnexDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Spring Data JPA repository for the UserAnnex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAnnexRepository extends JpaRepository<UserAnnex, Long>, JpaSpecificationExecutor<UserAnnex> {

    @Query(value = " select ifnull(u.phone,\"\") AS phone,ifnull(u.avatar,\"\") AS avatar,ifnull(u.nickname,\"\") AS nickname from user_annex AS u where userid=:id",nativeQuery = true)
    Map userInfo(@Param("id")Long id);

}
