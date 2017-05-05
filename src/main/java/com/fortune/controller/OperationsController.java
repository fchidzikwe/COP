package com.fortune.controller;

import java.io.File;
import java.util.Arrays;


import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.nn.AcceptInputs;
import com.fortune.nn.Diagnosis;
import com.fortune.nn.Utilities;

@Controller
public class OperationsController {
	

	AcceptInputs inputs = new AcceptInputs();
	
	@RequestMapping(value = "/diagnise",method=  RequestMethod.POST)
	public ModelAndView diagniseCholera(@RequestParam("diarrhoea") String diarrhoea,
										@RequestParam("vommiting") String vommiting, 
										@RequestParam("dehydration") String dehydration, 
										@RequestParam("blood_pressure") String blood_pressure, 
										@RequestParam("leg_cramps") String leg_cramps, 
										@RequestParam("gender") String gender, 
										@RequestParam("sunken_eyes") String sunken_eyes, 
										@RequestParam("age") int age
										
			
			
			){
		
		System.out.println("***************************************************************************");
		System.out.println("******************************************************************************");
		
		System.out.println("from page");
		
		System.out.println("diarrhoea :"+diarrhoea +" "+ "vommiting :"+vommiting
				+" "+ "dehydration :"+dehydration   +" "+ "blood_pressure :"+blood_pressure
				+" "+ "leg_cramps :"+leg_cramps   +" "+ "gender :"+gender
				+" "+ "sunken_eyes :"+sunken_eyes   +" "+ "age :"+age
				
				
				
				);
		
		ModelAndView modelAndView = new ModelAndView("diagnosisPage");
		
		double[] output = new double[4];
		
		double passedGender = inputs.normaliseGender(gender);
		double passedDiarrhoea = inputs.normaliseDDD(diarrhoea);
		double passedVommiting = inputs.normaliseDDD(vommiting);
		double passedDehydration = inputs.normaliseDDD(dehydration);
		double passedBloodPressure = inputs.normaliseBP(blood_pressure);
		double passedLegCramps = inputs.normaliseLegCramps(leg_cramps);
		double passedSunkenEyes = inputs.normaliseSunkenEyes(sunken_eyes);
		double passedAge = inputs.normaiseAge(age);
		
		double symptomInputs[] = new double[]{passedDiarrhoea,passedVommiting,passedDehydration,passedAge,passedBloodPressure,passedLegCramps,passedGender,passedSunkenEyes};
		
		System.out.println("***********************input array is as below*****************");
		Arrays.stream(symptomInputs).forEach(num -> System.out.println(num));
		
		//load training network
		BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence
				.loadObject(new File(Utilities.pathToEncogPersistance()));
		//pass input and diagnise
		network.compute(symptomInputs, output);
		
		System.out.println("***********************output array is as below*****************");
		Arrays.stream(output).forEach(num -> System.out.println(num));
		
		Diagnosis.getIndexOfMax(output);
		
		
		String diagnosis = Diagnosis.getDiagnosis(output);
		
		
		System.out.println(diagnosis);
		
		//TODO
		
		//create a method that will take facility(location and date ) and save to db
		
		/*if diagnosis is :
		 *  1. SEVERE - save to db with value of 8
		 *  2. MODERATE - save to db with value 3
		 *  3. MILD - save to db with value of 1
		 * 
		 * 
		 * */
		
		modelAndView.addObject("status", diagnosis);
		
		
		return modelAndView;
	}

}
