package com.eyun.user.repository;

import com.eyun.user.domain.Authentication;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Authentication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long>, JpaSpecificationExecutor<Authentication> {
/*
	@Query(value = "SELECT * FROM `withdraw_deposit` WHERE DATE(`created_time`) >= DATE(?1) AND DATE(`created_time`) <= DATE(?2) LIMIT ?3,?4 ",nativeQuery = true)
	public List<WithdrawDeposit> findSubDetilByPage(String frist,String last,Integer page,Integer size);*/
	
	@Query(value = "SELECT * FROM `authentication` WHERE DATE(`created_time`) >= DATE(?1) AND DATE(`created_time`) <= DATE(?2) LIMIT ?3,?4 ",nativeQuery = true)
	public List<Authentication> findSubAutherntication(String frist,String last,Integer page,Integer size);
}
