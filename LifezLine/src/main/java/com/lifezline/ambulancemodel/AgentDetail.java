package com.lifezline.ambulancemodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "AGENTDETAIL")
@Table(name = "AgentDetail")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2574806492958443234L;

	@OneToOne(targetEntity = RideDetail.class)
	RideDetail ridedetail;

	String ridenumber;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	Integer id;
	@NonNull
	String agent_FirstName = "";

	String agent_LastName = "";

	@Email
	String agent_email = "";

	@NonNull

	@JsonAlias({ "agent_number", "agent_PhoneNumber", "agent_mobile_number" })

	String agent_Phone_number = "";

	@NonNull
	@JsonProperty("Password")

	String agent_Password = "";

	String agent_accept = "";

	String agent_City = "";

	// String agent_Invite_Code = "";
	@NonNull

	String agent_Vehcile_Number = "";

	@JsonAlias({ "agent_latitude" })
	String agent_latitude = "";

	@JsonAlias({ "agent_longitude" })
	String agent_longitude = "";

	@NonNull
	@Lob

	@JsonProperty("agent_CNIC_Front")
	String agent_CNIC_front = "";

	@NonNull
	@Lob
	@JsonProperty("agent_CNIC_Back")
	String agent_CNIC_Back = "";

	@JsonAlias({ "agent_on_ride" })
	String AgentRideStatus = "";

	@OneToOne(targetEntity = AgentDetail.class)

	CustomerDetail customerdetail;

	String agent_reached_source = "";

	public String getAgent_reached_source() {
		return agent_reached_source;
	}

	public void setAgent_reached_source(String agent_reached_source) {
		this.agent_reached_source = agent_reached_source;
	}

	public String getAgent_accept() {
		return agent_accept;
	}

	public void setAgent_accept(String agent_accept) {
		this.agent_accept = agent_accept;
	}

	public String getAgentRideStatus() {
		return AgentRideStatus;
	}

	public void setAgentRideStatus(String agentRideStatus) {
		AgentRideStatus = agentRideStatus;
	}

	public String getAgent_latitude() {
		return agent_latitude;
	}

	public void setAgent_latitude(String agent_latitude) {
		this.agent_latitude = agent_latitude;
	}

	public String getAgent_longitude() {
		return agent_longitude;
	}

	public void setAgent_longitude(String agent_longitude) {
		this.agent_longitude = agent_longitude;
	}

	AgentDetail() {
	}

	public RideDetail getRidedetail() {
		return ridedetail;
	}

	public void setRidedetail(RideDetail ridedetail) {
		this.ridedetail = ridedetail;
	}

	public String getRidenumber() {
		return ridenumber;
	}

	public void setRidenumber(String ridenumber) {
		this.ridenumber = ridenumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgent_CNIC_Back() {
		return agent_CNIC_Back;
	}

	public void setAgent_CNIC_Back(String agent_CNIC_Back) {
		this.agent_CNIC_Back = agent_CNIC_Back;
	}

	public String getAgent_FirstName() {
		return agent_FirstName;
	}

	public void setAgent_FirstName(String agent_FirstName) {
		this.agent_FirstName = agent_FirstName;
	}

	public String getAgent_LastName() {
		return agent_LastName;
	}

	public void setAgent_LastName(String agent_LastName) {
		this.agent_LastName = agent_LastName;
	}

	public String getAgent_email() {
		return agent_email;
	}

	public void setAgent_email(String agent_email) {
		this.agent_email = agent_email;
	}

	public String getAgent_Phone_number() {
		return agent_Phone_number;
	}

	public void setAgent_Phone_number(String agent_Phone_number) {
		this.agent_Phone_number = agent_Phone_number;
	}

	public String getAgent_Password() {
		return agent_Password;
	}

	public void setAgent_Password(String agent_Password) {
		this.agent_Password = agent_Password;
	}

	public String getAgent_City() {
		return agent_City;
	}

	public void setAgent_City(String agent_City) {
		this.agent_City = agent_City;
	}

	public String getAgent_Vehcile_Number() {
		return agent_Vehcile_Number;
	}

	public void setAgent_Vehcile_Number(String agent_Vehcile_Number) {
		this.agent_Vehcile_Number = agent_Vehcile_Number;
	}

	public String getAgent_CNIC_front() {
		return agent_CNIC_front;
	}

	public void setAgent_CNIC_front(String agent_CNIC_front) {
		this.agent_CNIC_front = agent_CNIC_front;
	}

	public CustomerDetail getCustomerdetail() {
		return customerdetail;
	}

	public void setCustomerdetail(CustomerDetail customerdetail) {
		this.customerdetail = customerdetail;
	}

	@Override
	public String toString() {
		return "AgentDetail [ridedetail=" + ridedetail + ", ridenumber=" + ridenumber + ", id=" + id
				+ ", agent_FirstName=" + agent_FirstName + ", agent_LastName=" + agent_LastName + ", agent_email="
				+ agent_email + ", agent_Phone_number=" + agent_Phone_number + ", agent_Password=" + agent_Password
				+ ", agent_accept=" + agent_accept + ", AgentRideStatus=" + AgentRideStatus + ", agent_City="
				+ agent_City + ", agent_Vehcile_Number=" + agent_Vehcile_Number + ", agent_latitude=" + agent_latitude
				+ ", agent_longitude=" + agent_longitude + ", agent_CNIC_front=" + agent_CNIC_front
				+ ", agent_CNIC_Back=" + agent_CNIC_Back + ", customerdetail=" + customerdetail + "]";
	}
}
