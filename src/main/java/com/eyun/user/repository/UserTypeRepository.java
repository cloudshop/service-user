package com.eyun.user.repository;

import com.eyun.user.domain.UserType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

}
