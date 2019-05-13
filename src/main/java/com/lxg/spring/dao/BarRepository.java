package com.lxg.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lxg.spring.entity.BarTable;

public interface  BarRepository extends JpaRepository<BarTable,Integer> {
	
	List<BarTable> findLineTableByYear(String year);

}
