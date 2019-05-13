package com.lxg.spring.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "line")
@IdClass(LineTableID.class)
public class LineTable  implements java.io.Serializable{
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "YEAR")
    private String year;

	@Id
    @Column(name = "POLICY_NUM")
    private String policyNum;

    @Column(name = "COUNT")
    private String count;
    
    public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPolicyNum() {
		return policyNum;
	}

	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

    
}
