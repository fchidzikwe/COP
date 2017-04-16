package com.fortune.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class FileController{
	
	   @Autowired
	    JobLauncher jobLauncher;

	    @Autowired
	    Job job;
	
	
	@RequestMapping(value = "/uploadCsv", method = RequestMethod.POST)
	public ModelAndView uploadCsv() throws Exception{
		
	Logger logger = LoggerFactory.getLogger(this.getClass());
	        try {
	            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
	                    .toJobParameters();
	            jobLauncher.run(job, jobParameters);
	        } catch (Exception e) {
	            logger.info(e.getMessage());
	        }
	        ModelAndView modelAndView = new ModelAndView();
	        
				modelAndView.addObject("successUploaDMessage", "CSV UPLOADED SUCCESSFULLY");
				modelAndView.setViewName("admin/home");
	        
			
	        return modelAndView;

	}
	
	@RequestMapping(value="/testupload", method = RequestMethod.GET)
	public ModelAndView homePage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/UploadTrainingData");
		return modelAndView;
	}
	
	@RequestMapping(value="/testUpload", method = RequestMethod.GET)
	public ModelAndView uploadPage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("testUpload");
		return modelAndView;
	}
}
