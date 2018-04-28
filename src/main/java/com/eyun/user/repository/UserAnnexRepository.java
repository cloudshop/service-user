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



    @Query(value = " select ifnull(u.avatar,\"\") AS avatar,ifnull(u.nickname,\"\") AS nickname ,ifnull(u.phone,\"\") AS phone,ifnull(u.id,\"\") AS id,ifnull(u.id,\"\") AS userid from user_annex AS u where u.id=:id",nativeQuery = true)
    Map userInfo(@Param("id")Long id);

    @Query(value = "select ifnull(s.name,\"\") AS name, ifnull(s.phone,\"\") AS phone from user_annex AS s where s.id=:id",nativeQuery = true)
    List<Map> shareUserList(@Param("id")Long id);



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
        "WHERE a.inviter_id=b.inviter_id AND b.id=:id",nativeQuery = true)
    Long findInvitationUser(@Param("id")Long id);



  /*  @Query(value = "SELECT * FROM user_annex",nativeQuery = true)
    UserAnnex findProviders(@Param("invitationID")Long invitationID, @Param("userid")Long userid);

*/
    UserAnnex findByid(Long id);
}
