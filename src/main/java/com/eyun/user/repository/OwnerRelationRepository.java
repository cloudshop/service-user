package com.eyun.user.repository;

import com.eyun.user.domain.OwnerRelation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OwnerRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerRelationRepository extends JpaRepository<OwnerRelation, Long>, JpaSpecificationExecutor<OwnerRelation> {

}
