package com.lxg.spring.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(PolicyTableID.class)
@Table(name = "policy")
public class PolicyTable {
	

	@Id
	//@GeneratedValue(strategy = IDENTITY)
	@Column(name = "client_number")
	public String clientNumber;
	
	@Id
	@Column(name = "policy_number")
	public String policyId;
	
	@Id
	@Column(name = "rid")
	public String rid;
	
	@Column(name = "policy_name")
	public String policyName;
	
	@Column(name = "status")
	public String status;
	
	@Column(name = "currency")
	public String currency;
	
	@Column(name = "installed_premium")
	public String summary;

	@Column(name = "issue_date")
	public String issueDate;
	
	@Column(name = "product_type")
	public String productType;

	
	public String getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}


}
