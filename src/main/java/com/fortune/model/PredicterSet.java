package com.fortune.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "predicter")
public class PredicterSet {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "waterSupply")
	private double waterSupply;

	@Column(name = "facilityname")
	private String facility;

	@Column(name = "density")
	private double density;

	@Column(name = "choleraCaseWeight")
	private double choleraCaseWeight;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWaterSupply() {
		return waterSupply;
	}

	public void setWaterSupply(double waterSupply) {
		this.waterSupply = waterSupply;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getCholeraCaseWeight() {
		return choleraCaseWeight;
	}

	public void setCholeraCaseWeight(double choleraCaseWeight) {
		this.choleraCaseWeight = choleraCaseWeight;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "PredicterSet [id=" + id + ", waterSupply=" + waterSupply + ", facility=" + facility + ", density="
				+ density + ", choleraCaseWeight=" + choleraCaseWeight + ", created=" + created + "]";
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PredicterSet other = (PredicterSet) obj;
		if (choleraCaseWeight != other.choleraCaseWeight)
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (density != other.density)
			return false;
		if (facility != other.facility)
			return false;
		if (id != other.id)
			return false;
		if (waterSupply != other.waterSupply)
			return false;
		return true;
	}
	
	
	

}
