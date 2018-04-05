package com.eyun.user.repository;

import com.eyun.user.domain.UserStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

}
