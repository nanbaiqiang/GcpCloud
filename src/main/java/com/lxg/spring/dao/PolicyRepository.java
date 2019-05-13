package com.lxg.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lxg.spring.entity.ClientTable;
import com.lxg.spring.entity.PolicyTable;

@Repository
public interface PolicyRepository extends JpaRepository<PolicyTable,Integer> {
	
	List<PolicyTable> findPolicyTableByClientNumber(String clientNum);
	
}