package com.fortune.service;

import com.fortune.model.Facility;
import com.fortune.model.User;

public interface FacilityService {
	public Facility findfacilityByName(String name);
	
	public Facility getFacility(User user);
	

	public void saveFacility(Facility facility);
	
	
}
