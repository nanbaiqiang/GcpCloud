package com.lxg.spring.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "donut")
public class DonutTable implements java.io.Serializable{
	

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private String value;
	
    @Column(name = "formatted")
    private String formatted;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormatted() {
		return formatted;
	}

	public void setFormatted(String formatted) {
		this.formatted = formatted;
	}


	

}
