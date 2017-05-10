package com.fortune.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "facility")
public class Facility {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "facility_id")
	private int id;

	@Column(name = "name")
	@NotEmpty(message = "*Please provide your name")
	private String name;

	@Column(name = "density")
	@NotEmpty(message = "*Please provide your name")
	private String density;

	@Column(name = "waterSupplyQuality")
	@NotEmpty(message = "*Please provide your name")
	private String waterSupplyQuality;
	

	@Column(name = "user_email")
	@NotEmpty(message = "*Please provide  email of user")
	private String user_email;

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	

	

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getWaterSupplyQuality() {
		return waterSupplyQuality;
	}

	public void setWaterSupplyQuality(String waterSupplyQuality) {
		this.waterSupplyQuality = waterSupplyQuality;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
