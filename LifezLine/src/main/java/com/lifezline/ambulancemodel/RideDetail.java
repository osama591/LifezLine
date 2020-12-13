package com.lifezline.ambulancemodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "RIDEDETAIL")
@Table(name = "RideDetail")

public class RideDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1231906462981284597L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	Integer id;

	String ridenumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RideDetail() {
		// TODO Auto-generated constructor stub
	}

	public String getRidenumber() {
		return ridenumber;
	}

	public void setRidenumber(String ridenumber) {
		this.ridenumber = ridenumber;
	}

	public AgentDetail getAgent() {
		return agent;
	}

	public void setAgent(AgentDetail agent) {
		this.agent = agent;
	}

	public CustomerDetail getCustdetail() {
		return customerdetail;
	}

	public void setCustdetail(CustomerDetail custdetail) {
		this.customerdetail = custdetail;
	}

	@OneToOne(targetEntity = RideDetail.class)
	AgentDetail agent;

	@OneToOne(targetEntity = RideDetail.class)
	CustomerDetail customerdetail;

}
