package com.fortune.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fortune.enums.Density;
import com.fortune.enums.WaterSupply;
import com.fortune.model.Facility;
import com.fortune.model.User;
import com.fortune.service.FacilityService;
import com.fortune.service.StorageService;
import com.fortune.service.UserService;
import com.fortune.utils.FacilityEnumUtils;

@Controller
public class LoginController {

	@Autowired
	StorageService storageService;

	@Autowired
	FacilityService facilityService;

	@Autowired
	private UserService userService;

	List<String> files = new ArrayList<String>();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("testLogin");
		return modelAndView;
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
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
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

	@SuppressWarnings("unused")
	@RequestMapping(value = "/facilityRegistration", method = RequestMethod.POST)
	public ModelAndView createNewFacility(@Valid User user, BindingResult bindingResultUser, @Valid Facility facility,
			BindingResult bindingResultFacility, @RequestParam("user_email") String user_email,
			@RequestParam("density") String density, @RequestParam("waterSupply") String waterSupply) {
		
		
		String densityValue = FacilityEnumUtils.processDensity(density);
		String waterSupplyValue = FacilityEnumUtils.processWaterSupply(waterSupply);

		
		System.out.println("*************density :"+densityValue);
		
		
		System.out.println("*************water Supply :"+waterSupplyValue);
	
 	 ModelAndView modelAndView = new ModelAndView();
		
		User userFacility = userService.findUserByEmail(user_email);
		
		System.out.println("*******************hi :"+userFacility.getName());
		// user.setEmail(email);

		if (userFacility == null) {
			bindingResultFacility.rejectValue("email", "error.user", "No user with email specified found");
			System.out.println("no user found");
		}
		if (bindingResultFacility.hasErrors()) {
			modelAndView.setViewName("facilityRegistration");
			System.out.println("binding error " + bindingResultFacility.getAllErrors());
		} else {

			facility.setUser(userFacility);
			facility.setUser_email(user_email);
			facility.setDensity(Density.valueOf(densityValue.toUpperCase().trim()));
			facility.setWaterSupplyQuality(WaterSupply.valueOf(waterSupplyValue.toUpperCase().trim()));
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
		modelAndView.setViewName("admin/");
		return modelAndView;
	}

}