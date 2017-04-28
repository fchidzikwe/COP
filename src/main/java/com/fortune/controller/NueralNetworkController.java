package com.fortune.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.nn.TestNetworks;

@Controller
public class NueralNetworkController {
	
	
	@RequestMapping(value = "/train", method = RequestMethod.GET)
	public ModelAndView trainNetwork(){
		
		ModelAndView model = new ModelAndView();
		TestNetworks.trainRProp();
		
	    model.setViewName("uploadCSV");
        model.addObject("message", "Network successfully trained!!!!!!!");
		
		return model;
		
	}

}
