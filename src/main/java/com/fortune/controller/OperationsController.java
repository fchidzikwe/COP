package com.fortune.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Date;


import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.model.Facility;
import com.fortune.model.PredicterSet;
import com.fortune.model.User;
import com.fortune.nn.AcceptInputs;
import com.fortune.nn.Diagnosis;
import com.fortune.nn.Utilities;
import com.fortune.service.FacilityService;
import com.fortune.service.PredicterSetService;
import com.fortune.service.UserService;

@Controller
public class OperationsController {

	@Autowired
	UserService userService;

	@Autowired
	PredicterSetService predicterSetService;

	@Autowired
	FacilityService facilityService;

	AcceptInputs inputs = new AcceptInputs();

	@RequestMapping(value = "/diagnise", method = RequestMethod.POST)
	public ModelAndView diagniseCholera(@RequestParam("diarrhoea") String diarrhoea,
			@RequestParam("vommiting") String vommiting, @RequestParam("dehydration") String dehydration,
			@RequestParam("blood_pressure") String blood_pressure, @RequestParam("leg_cramps") String leg_cramps,
			@RequestParam("gender") String gender, @RequestParam("sunken_eyes") String sunken_eyes,
			@RequestParam("age") int age, Authentication auth

	) {

		System.out.println("***************************************************************************");
		System.out.println("******************************************************************************");

		System.out.println("from page");

		System.out.println("diarrhoea :" + diarrhoea + " " + "vommiting :" + vommiting + " " + "dehydration :"
				+ dehydration + " " + "blood_pressure :" + blood_pressure + " " + "leg_cramps :" + leg_cramps + " "
				+ "gender :" + gender + " " + "sunken_eyes :" + sunken_eyes + " " + "age :" + age

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

		double symptomInputs[] = new double[] { passedDiarrhoea, passedVommiting, passedDehydration, passedAge,
				passedBloodPressure, passedLegCramps, passedGender, passedSunkenEyes };

		System.out.println("***********************input array is as below*****************");
		Arrays.stream(symptomInputs).forEach(num -> System.out.println(num));

		// load training network
		BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence
				.loadObject(new File(Utilities.pathToEncogPersistance()));
		// pass input and diagnise
		network.compute(symptomInputs, output);

		System.out.println("***********************output array is as below*****************");
		Arrays.stream(output).forEach(num -> System.out.println(num));

		Diagnosis.getIndexOfMax(output);

		String diagnosis = Diagnosis.getDiagnosis(output);

		System.out.println(diagnosis);

		PredicterSet predictorSet = new PredicterSet();

		if (diagnosis.equalsIgnoreCase("no cholera")) {
			
			modelAndView.addObject("status","CHOLERA:"+ diagnosis.toUpperCase());

		}

		else {

			// TODO

			// create a method that will take facility(location and date ) and
			// save to db

			/*
			 * if diagnosis is : 1. SEVERE - save to db with value of 8 2.
			 * MODERATE - save to db with value 3 3. MILD - save to db with
			 * value of 1
			 * 
			 * 
			 */

			

		
			
			User currentUser =null;
			try{
				FileInputStream fis = new FileInputStream(Utilities.NNDIR +"/user.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				currentUser = (User)ois.readObject();
				ois.close();
				fis.close();
				
			}
			catch(IOException e){
				e.printStackTrace();
				
			}
			catch(ClassNotFoundException cnfe){
				System.out.println("User serialised object not found");
				cnfe.printStackTrace();
			}
			
			Facility currentFacility = facilityService.getFacility(currentUser);

			
			predictorSet.setFacility(currentFacility.getName());
			
			
			// setting cholera case weight

			switch (diagnosis.toLowerCase()) {
			case "severe":
				predictorSet.setCholeraCaseWeight(8);
				break;
			case "moderate":
				predictorSet.setCholeraCaseWeight(3);
				break;
			case "mild":
				predictorSet.setCholeraCaseWeight(1);
				break;

			default:
				break;
			}
			

			// setting density in predicter set
			switch (currentFacility.getDensity()) {
			case "LOW":
				predictorSet.setDensity(1);
				break;
			case "MEDIUM":
				predictorSet.setDensity(3);
				break;

			case "HIGH":
				predictorSet.setDensity(8);

			default:
				break;
			}

			// setting watersupply in predicter set
			switch (currentFacility.getWaterSupplyQuality()) {
			case "POOR":
				predictorSet.setWaterSupply(8);
				break;
			case "STANDARD":
				predictorSet.setWaterSupply(3);
				break;

			case "GOOD":
				predictorSet.setWaterSupply(1);
				break;

			default:
				break;
			}

			System.out.println("facility form userService :" + currentFacility);

			Date date = new Date();

			// setting predictor set

			predictorSet.setCreated(date);
			
			
			predicterSetService.savePredicterSet(predictorSet);
			
			modelAndView.addObject("status", "CHOLERA: "+""+diagnosis.toUpperCase() +" reocrd will be saved to database".toUpperCase());

		}

		

		return modelAndView;
	}

}
