package com.eyun.user.repository;

import com.eyun.user.domain.UserAnnex;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the UserAnnex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAnnexRepository extends JpaRepository<UserAnnex, Long> {
    @Query("select distinct user_annex from UserAnnex user_annex left join fetch user_annex.userTypes")
    List<UserAnnex> findAllWithEagerRelationships();

    @Query("select user_annex from UserAnnex user_annex left join fetch user_annex.userTypes where user_annex.id =:id")
    UserAnnex findOneWithEagerRelationships(@Param("id") Long id);

}
