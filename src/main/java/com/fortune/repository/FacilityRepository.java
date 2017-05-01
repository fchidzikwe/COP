package com.fortune.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fortune.model.Facility;
import com.fortune.model.User;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
	Facility findByName(String email);
	
	Facility findByUser(User user);
	
}
