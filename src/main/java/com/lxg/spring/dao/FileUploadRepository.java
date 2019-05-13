package com.lxg.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lxg.spring.entity.FileUploadTable;


public interface  FileUploadRepository extends JpaRepository<FileUploadTable,Integer> {
	
}

