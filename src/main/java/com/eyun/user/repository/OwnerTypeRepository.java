package com.eyun.user.repository;

import com.eyun.user.domain.OwnerType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OwnerType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerTypeRepository extends JpaRepository<OwnerType, Long> {

}
