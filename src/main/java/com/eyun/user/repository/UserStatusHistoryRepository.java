package com.eyun.user.repository;

import com.eyun.user.domain.UserStatusHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserStatusHistoryRepository extends JpaRepository<UserStatusHistory, Long>, JpaSpecificationExecutor<UserStatusHistory> {

}
