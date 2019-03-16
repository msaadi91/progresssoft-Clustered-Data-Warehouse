package com.progresssoft.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Mohammad-Alsaadi
 *
 */
@Entity
@Table(name = "invalid_deal")
public class InValidDeal extends  DealModel implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String reason;

	/**
	 * @return the reason
	 *//*
	@Transient
	public String getReason() {
		return reason;
	}

	*//**
	 * @param reason the reason to set
	 *//*
	public void setReason(String reason) {
		this.reason = reason;
	}*/
	
	

}
