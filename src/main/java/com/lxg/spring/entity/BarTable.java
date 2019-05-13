package com.lxg.spring.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "bar")
@IdClass(BarTableID.class)
public class BarTable implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "YEAR")
	public String year;

	@Id
	@Column(name = "POLICY_ID")
	public int policyId;

	@Column(name = "POLICY_NAME")
	public String policyName;

	@Column(name = "POLICY_COUNT")
	public String policyCount;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getPolicyCount() {
		return policyCount;
	}

	public void setPolicyCount(String policyCount) {
		this.policyCount = policyCount;
	}

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
}
