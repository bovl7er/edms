package com.cmxv.modellayer.DBentities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RIGHTS")
public class Right implements Serializable {

	private static final long serialVersionUID = -8951382059917244222L;

	@Id
	@Column(name="RIGHT_ID")
	private int rightId;
	
	@Column(name="VALUE")
	private String value;

	public int getRightId() {
		return rightId;
	}

	public void setRightId(int rightId) {
		this.rightId = rightId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
