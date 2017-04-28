package com.fortune.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.nn.NormaliseTrainingData;


@Controller
public class NormaliseController {

	@RequestMapping("/normalise")
	public ModelAndView normalise(){
		//NormaliseCsv norm = new NormaliseCsv();
		NormaliseTrainingData norm = new NormaliseTrainingData();
		norm.doThings();
		ModelAndView m = new ModelAndView("uploadCSV");
		
		return m;
	}
	
	
}
