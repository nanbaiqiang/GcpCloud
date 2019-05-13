package com.lxg.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lxg.spring.entity.LineTable;

@Repository
public interface LineRepository extends JpaRepository<LineTable,Integer> {
	
	List<LineTable> findLineTableByYear(String year);

}