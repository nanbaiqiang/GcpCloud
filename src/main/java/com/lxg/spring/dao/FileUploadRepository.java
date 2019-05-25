package com.lxg.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lxg.spring.entity.ClientTable;
import com.lxg.spring.entity.FileUploadTable;


public interface  FileUploadRepository extends JpaRepository<FileUploadTable,Integer> {
	
	List<FileUploadTable> findFileUploadTableByUplaodUser(String uplaodUser);
	
}

