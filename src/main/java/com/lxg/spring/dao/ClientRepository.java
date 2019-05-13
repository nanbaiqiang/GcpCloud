package com.lxg.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lxg.spring.entity.ClientTable;


public interface  ClientRepository extends JpaRepository<ClientTable,Integer> {
	
	List<ClientTable> findClientTableByClientNum(String clientNum);
	
	List<ClientTable> findClientTableByName(String name);

}
