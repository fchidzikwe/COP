package com.fortune.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.fortune.utils.DataBaseConnection;
import java.sql.Connection;

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

	@RequestMapping(value = "/predict", method = RequestMethod.GET)
	public ModelAndView getPredicterPage() {
		ModelAndView model = new ModelAndView();

		model.setViewName("predict");

		return model;

	}
	
	@RequestMapping(value="/predict", method=RequestMethod.POST)
	public ModelAndView predict(@RequestParam("facilityname") String facility_name ,
								@RequestParam("dateFrom") String dateFrom,
								@RequestParam("dateTo") String dateTo){
		ModelAndView model = new ModelAndView();
		
		
		double density, waterSupply;
		
		Facility facility = facilityService.findfacilityByName(facility_name);
		
		String d = facility.getDensity(); //get facility density
		String w = facility.getWaterSupplyQuality();
		
		switch (d) {
		case "HIGH":
			density = 1;
			break;
		case "MEDIUM":
			density = 0.25;
			break;
		case "LOW":
			density =0;
			break;

		default:
			break;
		}
		
		
		switch (w) {
		case "POOR":
			waterSupply = 1;
			break;
			
		case "STANDARD":
			waterSupply = 0.25;
			break;
			
		case "GOOD":
			waterSupply=0;
			break;

		default:
			break;
		}
		
	
		
		
		
	    DataBaseConnection dbCon = new DataBaseConnection();
        Connection con = dbCon.getConnection();

        
        
        
        
        try{
        String selectSQL = "SELECT `id`, `choleraCaseWeight`, `created`, `density`, "
        		+ "`facilityname`, `waterSupply` , COUNT(`id`) as Cases FROM `predicter` "
        		+ "WHERE `facilityname` ='?' AND `created` BETWEEN "
        		+ "'?' AND '?' GROUP BY `id`";
        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setString(1, facility_name);
        preparedStatement.setString(2, dateFrom);
        preparedStatement.setString(3, dateTo);

        System.out.println(preparedStatement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
       }
        con.close();
    
        }
        catch(SQLException e){
        	e.printStackTrace();
        }
			
			
	
		
		
		
		 
		  
		  model.addObject("status", facility_name + " to " +dateTo + " from "+dateFrom);
		model.setViewName("predict");
		
		
		return model;
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
	
	
	@RequestMapping(value="/adminHome",method=RequestMethod.POST)
	public ModelAndView AdminHome(){
		ModelAndView model = new ModelAndView();
		model.setViewName("HomePage");
		return model;
	}

}