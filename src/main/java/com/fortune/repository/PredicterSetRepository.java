package com.fortune.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.fortune.model.PredicterSet;



@Repository("predicterSetRepository")
public interface PredicterSetRepository extends JpaRepository <PredicterSet, Long>{
	PredicterSet findPredicterSetById(String role);

}
