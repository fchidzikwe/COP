package com.fortune.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;


@Controller
public class SaveCSvController {
	 private static final Logger LOGGER = LoggerFactory.getLogger(SaveCSvController.class);

	 
	  ModelAndView model = new ModelAndView();
	    @Autowired
	    private Environment env;

	    @RequestMapping(value = "/uploadcsv", method = RequestMethod.GET)
	    public ModelAndView uploadCsv() {
	    	
	    	LOGGER.error("getting upload page");
	        LOGGER.info("Request Received for upload Page ");
	        
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("uploadCSV");
	        return modelAndView;
	    }

	    /**
	     * POST /uploadCsv -> receive and locally save a file.
	     *
	     * @param uploadFile The uploaded file as Multipart file parameter in the
	     *                   HTTP request. The RequestParam name must be the same of the attribute
	     *                   "name" in the input tag with type file.
	     */
	    @RequestMapping(value = "/uploadcsv", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	    public ModelAndView uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile) {
	        LOGGER.info("Request Received for File Upload ");
	        
	    	LOGGER.error("in posting file");
	        try {
	            String filename = uploadFile.getOriginalFilename();
	            String directory = env.getProperty("spring.config.location");
	            String uploadFilePath = Paths.get("." + File.separator + directory, filename).toString();
	            
	            System.out.println("csv file saved in "+ uploadFilePath);
	        	LOGGER.error("filename : "+filename);
	        	LOGGER.error("upload dir: "+directory);
	        	LOGGER.error("upload filepath :"+uploadFilePath);
	        	
	            final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(uploadFilePath)));
	            stream.write(uploadFile.getBytes());
	            LOGGER.error(uploadFile.getBytes().toString());
	           
	            
	            
	            model.setViewName("uploadCSV");
	            model.addObject("message", "You successfully uploaded " + uploadFile.getOriginalFilename() + "!");
	            
	            System.out.println("******************************finished writing to file");
	            stream.close();
	            
	        } catch (Exception e) {
	            LOGGER.error(e.getMessage());
	      
	            model.addObject("message", "FAIL to upload " + uploadFile.getOriginalFilename() + "!");
	        }
	        
	        return model;
	       
	    }
}
