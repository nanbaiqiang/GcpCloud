package com.lxg.spring.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class ClientTable {
	
	@Id
	//@GeneratedValue(strategy = IDENTITY)
	@Column(name = "client_num")
	public String clientNum;
	
	@Column(name = "id_num")
	public String idNum;

	@Column(name = "name")
	public String name;

	@Column(name = "id_type")
	public String idType;
	
	@Column(name = "country")
	public String country;
	
	@Column(name = "adress")
	public String adress;

	@Column(name = "mobil")
	public String mobil;
	
	
	@Column(name = "gender")
	public String gender;
	
	@Column(name = "brith")
	public String brith;

	@Column(name = "remark")
	public String remark;


	public String getClientNum() {
		return clientNum;
	}

	public void setClientNum(String clientNum) {
		this.clientNum = clientNum;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBrith() {
		return brith;
	}

	public void setBrith(String brith) {
		this.brith = brith;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	
	
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
	
}
