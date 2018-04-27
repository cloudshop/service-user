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

    @Query(value = " select ifnull(u.avatar,\"\") AS avatar,ifnull(u.nickname,\"\") AS nickname ,ifnull(u.phone,\"\") AS phone,ifnull(u.id,\"\") AS id,ifnull(u.userid,\"\") AS userid from user_annex AS u where u.userid=:id",nativeQuery = true)
    Map userInfo(@Param("id")Long id);

    @Query(value = "select ifnull(s.name,\"\") AS name, ifnull(s.phone,\"\") AS phone from user_annex AS s where s.userid=:id",nativeQuery = true)
    List<Map> shareUserList(@Param("id")Long id);

    @Query(value = "UPDATE user_annex  SET jhi_type =4  WHERE userid =:id ",nativeQuery = true)
    void UpdaeUserStatus(@Param("id")Long id);

    /**
     * 根据用户的id查询邀请人的ID
     * @param id
     * @return
     */
    @Query(value = "SELECT\n" +
        "\tDISTINCT(b.inviter_id)\n" +
        "FROM\n" +
        "\tuser_annex a,\n" +
        "\tuser_annex b\n" +
        "WHERE a.inviter_id=b.inviter_id AND b.userid=:id",nativeQuery = true)
    Long findInvitationUser(@Param("id")Long id);
}
