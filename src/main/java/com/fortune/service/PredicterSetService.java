package com.fortune.service;

import java.util.List;

import com.fortune.model.PredicterSet;

public interface PredicterSetService {

	public void savePredicterSet(PredicterSet predicterSet);
	
	
	public List<PredicterSet>findAll();
}


