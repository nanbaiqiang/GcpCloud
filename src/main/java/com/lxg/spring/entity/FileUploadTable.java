package com.lxg.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "file_upload")
@IdClass(FileUploadTableID.class)
public class FileUploadTable {

	@Id
	@Column(name = "file_name")
	public String fileName;
	
	@Id
	@Column(name = "date")
	public String date;

	@Id
	@Column(name = "uplaod_user")
	public String uplaodUser;

	@Column(name = "status")
	public String status;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUplaodUser() {
		return uplaodUser;
	}

	public void setUplaodUser(String uplaodUser) {
		this.uplaodUser = uplaodUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	
}
