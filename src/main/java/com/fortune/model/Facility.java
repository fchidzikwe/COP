package com.fortune.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.validator.constraints.NotEmpty;

import com.fortune.enums.Density;
import com.fortune.enums.WaterSupply;

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
	@Enumerated(EnumType.STRING)
	private Density density;

	@Column(name = "waterSupplyQuality")
	@Enumerated(EnumType.STRING)
	private WaterSupply waterSupplyQuality;
	

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
	
	
	


	public Density getDensity() {
		return density;
	}

	public void setDensity(Density density) {
		this.density = density;
	}

	
	public WaterSupply getWaterSupplyQuality() {
		return waterSupplyQuality;
	}

	public void setWaterSupplyQuality(WaterSupply waterSupplyQuality) {
		this.waterSupplyQuality = waterSupplyQuality;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Facility() {

	}

	public Facility(String name, Density density, WaterSupply waterSupplyQuality) {

		this.name = name;
		this.density = density;
		this.waterSupplyQuality = waterSupplyQuality;

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
