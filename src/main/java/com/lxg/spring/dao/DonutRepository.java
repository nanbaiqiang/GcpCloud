package com.lxg.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lxg.spring.entity.DonutTable;


	public interface  DonutRepository extends JpaRepository<DonutTable,Integer> {
		

	}
