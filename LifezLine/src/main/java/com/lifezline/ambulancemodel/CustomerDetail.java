package com.lifezline.ambulancemodel;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "CustomerDetail")
@Entity(name = "CustomerDetail")
public class CustomerDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1285498376924314522L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@OneToOne(targetEntity = RideDetail.class)
	RideDetail ridedetail;

	String ridenum;

	@OneToOne(targetEntity = AgentDetail.class)
	AgentDetail agent;


	
	@JsonAlias({"customerDestLatitude"})
	String cust_dest_lat;

	
	@JsonAlias({"customerDestLongitude"})
	String cust_dest_long;

	@JsonAlias("customerSourcePlace")
	String cust_source_place;


	@JsonAlias({"customerDestPlace"})
	String cust_dest_place;

	
	@JsonAlias({"customerLongitude"})
	String cust_long;

	@JsonAlias({"customerLatitude"})
	String cust_lat;

	@JsonProperty("Custnumber")
	String Custnumber;

	
	@JsonAlias("Custnumber")
	String MobileNumber;

	@JsonProperty("password")
	String password;

	@JsonProperty("EmergencyContactNumber")
	String EmergencyContactNumber;

	@JsonProperty("Country")
	String Country;

	@Email
	@JsonProperty("EmailId")
	String EmailId;

	@NonNull
	@JsonProperty("CustFirstname")
	
	String CustFirstname;

	@JsonProperty("CustLastname")

	String CustLastname;

	@DateTimeFormat
	Date registerationtime;

	@NonNull
	@JsonProperty("cnic")
	String cust_cnic;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRidenum() {
		return ridenum;
	}

	public void setRidenum(String ridenum) {
		this.ridenum = ridenum;
	}

	public AgentDetail getAgent() {
		return agent;
	}

	public void setAgent(AgentDetail agent) {
		this.agent = agent;
	}

	public String getCust_dest_lat() {
		return cust_dest_lat;
	}

	public void setCust_dest_lat(String cust_dest_lat) {
		this.cust_dest_lat = cust_dest_lat;
	}

	public String getCust_dest_long() {
		return cust_dest_long;
	}

	public void setCust_dest_long(String cust_dest_long) {
		this.cust_dest_long = cust_dest_long;
	}

	public String getCust_source_place() {
		return cust_source_place;
	}

	public void setCust_source_place(String cust_source_place) {
		this.cust_source_place = cust_source_place;
	}

	public String getCust_dest_place() {
		return cust_dest_place;
	}

	public void setCust_dest_place(String cust_dest_place) {
		this.cust_dest_place = cust_dest_place;
	}

	public String getCust_long() {
		return cust_long;
	}

	public void setCust_long(String cust_long) {
		this.cust_long = cust_long;
	}

	public String getCust_lat() {
		return cust_lat;
	}

	public void setCust_lat(String cust_lat) {
		this.cust_lat = cust_lat;
	}

	public String getCustnumber() {
		return Custnumber;
	}

	public void setCustnumber(String custnumber) {
		Custnumber = custnumber;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmergencyContactNumber() {
		return EmergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		EmergencyContactNumber = emergencyContactNumber;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	public String getCustFirstname() {
		return CustFirstname;
	}

	public void setCustFirstname(String custFirstname) {
		CustFirstname = custFirstname;
	}

	public String getCustLastname() {
		return CustLastname;
	}

	public void setCustLastname(String custLastname) {
		CustLastname = custLastname;
	}

	public Date getRegisterationtime() {
		return registerationtime;
	}

	public void setRegisterationtime(Date registerationtime) {
		this.registerationtime = registerationtime;
	}

	public String getCust_cnic() {
		return cust_cnic;
	}

	public void setCust_cnic(String cust_cnic) {
		this.cust_cnic = cust_cnic;
	}

	public RideDetail getRidedetail() {
		return ridedetail;
	}

	public void setRidedetail(RideDetail ridedetail) {
		this.ridedetail = ridedetail;
	}

}
