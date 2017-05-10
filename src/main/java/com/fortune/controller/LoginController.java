package com.fortune.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.model.Facility;
import com.fortune.model.User;
import com.fortune.nn.Utilities;
import com.fortune.service.FacilityService;
import com.fortune.service.StorageService;
import com.fortune.service.UserService;
import com.fortune.service.UserValidator;

@Controller
public class LoginController {

	@Autowired
	StorageService storageService;

	@Autowired
	FacilityService facilityService;

	@Autowired
	private UserService userService;

	@Autowired
	UserValidator userValidator;

	List<String> files = new ArrayList<String>();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("testLogin");
		return modelAndView;
	}

	@RequestMapping(value = "/processlogin", method = RequestMethod.POST)
	public ModelAndView loginProcess(@RequestParam String email) {

		System.out.println("******************************in process login");
		ModelAndView modelAndView = new ModelAndView();
		User loggedInUser = userService.findUserByEmail(email);

		System.out.println("*********************8user found is " + loggedInUser.getName());

		// getRole from user
		String userRole = loggedInUser.getRoles();

		if (userRole.equalsIgnoreCase("user")) {

			System.out.println("user found form userService " + loggedInUser.getName());

			// Object serialisation

			System.out.println("*******************object serialisation start here");

			try {
				FileOutputStream fos = new FileOutputStream(Utilities.NNDIR + "/user.ser");
				ObjectOutputStream ous = new ObjectOutputStream(fos);

				ous.writeObject(loggedInUser);
				ous.close();
				fos.close();

				System.out.println("******logged in user is saved to " + Utilities.NNDIR);

			} catch (IOException e) {
				e.printStackTrace();
			}

			modelAndView.addObject("greetings", "HI " + loggedInUser.getName() + "You are logged in as SIS");

			// modelAndView.addObject("welcome", loggedInUser.getName() + " " +
			// loggedInUser.getLastName());
			System.out.println("after setting message");

			System.out.println("after setting home page");

			modelAndView.setViewName("diagnosisPage");

			return modelAndView;

		}

		else {

			modelAndView.addObject("greetings", "Hi " + loggedInUser.getName() + "You are logged in as ADMIN");
			modelAndView.setViewName("HomePage");
			return modelAndView;
		}
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");

		return modelAndView;

	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			Model model) {

		// userValidator.validate(user, bindingResult);

		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {

			user.setRoles("USER");
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/facilityRegistration", method = RequestMethod.GET)
	public ModelAndView facilityRegistration() {
		ModelAndView modelAndView = new ModelAndView();
		Facility facility = new Facility();
		modelAndView.addObject("facility", facility);
		modelAndView.setViewName("facilityRegistration");
		return modelAndView;
	}

	@RequestMapping(value = "/predicterPage", method = RequestMethod.GET)
	public ModelAndView getPredicterPage() {
		ModelAndView model = new ModelAndView();

		model.setViewName("predicterPage");

		return model;

	}
	
	@RequestMapping(value="/predict", method=RequestMethod.POST)
	public ModelAndView predict(@RequestParam("facility_name") String facility_name){
		ModelAndView model = new ModelAndView();
		
		
		
		
		
		
		//select a date range of 3 days
		
		 Timestamp timestamp = new Timestamp(new Date().getTime());
		 
		    Timestamp currentTimestamp = new Timestamp(new Date().getTime());
		    
		    System.out.println("current "+currentTimestamp);
		    Calendar cal = Calendar.getInstance();
		    cal.setTimeInMillis(currentTimestamp.getTime());
		 
		  
		 
		    // subract  3 days
		    cal.setTimeInMillis(timestamp.getTime());
		    cal.add(Calendar.DAY_OF_MONTH, - 5);
		    timestamp = new Timestamp(cal.getTime().getTime());
		    System.out.println("subracted: "+timestamp);
		
		
		return null;
	}

	@RequestMapping(value = "/facilityRegistration", method = RequestMethod.POST)
	public ModelAndView createNewFacility(@Valid Facility facility, BindingResult bindingResult,
			@RequestParam("user_email") String user_email, @RequestParam("density") String density,
			@RequestParam("waterSupplyQuality") String waterSupplyQuality) {

		ModelAndView modelAndView = new ModelAndView();

		// check if facility exists
		Facility facilityExists = facilityService.findfacilityByName(facility.getName());
		if (facilityExists != null) {
			bindingResult.rejectValue("name", "error.facility",
					"There is already a facility registered with the name provided");
		}

		// check if there is error when binding

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("facilityRegistration");
			System.out.println("binding error " + bindingResult.getAllErrors());
		}

		// set facility details and save facility
		else {

			facility.setUser_email(user_email);
			facility.setUser(userService.findUserByEmail(user_email));

			switch (density) {

			case "one":
				facility.setDensity("LOW");
				break;

			case "two":
				facility.setDensity("MEDIUM");
				break;

			case "three":
				facility.setDensity("HIGH");

				break;

			}

			switch (waterSupplyQuality) {

			case "one":
				facility.setWaterSupplyQuality("POOR");
				break;

			case "two":
				facility.setWaterSupplyQuality("STANDARD");
				break;

			case "three":
				facility.setWaterSupplyQuality("GOOD");
				break;

			}

			facilityService.saveFacility(facility);

			modelAndView.addObject("successMessage", "Facility has been registered successfully");
			modelAndView.addObject("facility", new Facility());
			modelAndView.setViewName("facilityRegistration");

		}

		return modelAndView;
	}

	/*
	 * @RequestMapping(value="/home", method = RequestMethod.GET) public
	 * ModelAndView UserHomePage(){ ModelAndView modelAndView = new
	 * ModelAndView(); Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); User user =
	 * userService.findUserByEmail(auth.getName());
	 * modelAndView.addObject("userName", "Welcome " + user.getName() + " " +
	 * user.getLastName() + " (" + user.getEmail() + ")");
	 * modelAndView.addObject("adminMessage","Content Available Only for Users "
	 * ); modelAndView.setViewName("HomePage"); return modelAndView; }
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView AdminHomePage() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName",
				"Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users ");
		modelAndView.setViewName("HomePage");
		return modelAndView;
	}

}