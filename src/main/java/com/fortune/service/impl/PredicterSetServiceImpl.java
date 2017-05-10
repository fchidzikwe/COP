package com.fortune.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortune.model.PredicterSet;
import com.fortune.repository.PredicterSetRepository;
import com.fortune.service.PredicterSetService;


@Service("predicterSetService")
public class PredicterSetServiceImpl implements PredicterSetService{
	
	@Autowired
	PredicterSetRepository predicterSetRepository;

	@Override
	public void savePredicterSet(PredicterSet predicterSet) {
		predicterSetRepository.save(predicterSet);
		
	}

	@Override
	public List<PredicterSet> findAll() {
		return predicterSetRepository.findAll();
	}



}
