package com.fortune.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortune.model.Facility;

import com.fortune.model.User;
import com.fortune.repository.FacilityRepository;

import com.fortune.service.FacilityService;


@Service("facilityService")
public class FacilityServiceImpl implements FacilityService{
	
	@Autowired
	FacilityRepository facilityRepository;

	@Override
	public Facility findfacilityByName(String name) {
		
		return facilityRepository.findByName(name);
	}

	@Override
	public Facility getFacility(User user) {
		
		return facilityRepository.findByUser(user);
	}

	@Override
	public void saveFacility(Facility facility) {
		facilityRepository.save(facility);
		
	}





}