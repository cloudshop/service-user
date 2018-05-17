package com.eyun.user.repository;

import com.eyun.user.domain.Authentication;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Authentication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long>, JpaSpecificationExecutor<Authentication> {

}
