package com.puneet.reservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.puneet.reservation.entities.User;
import com.puneet.reservation.repos.UserRepository;
import com.puneet.reservation.services.SecurityService;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private SecurityService securityService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/showReg")
	public String showRegistrationPage( ) {
		LOGGER.info("inside showRegistrationPage()");
		return "login/registerUser";
	}
	
	@GetMapping("/showLogin")
	public String showLoginPage( ) {
		LOGGER.info("inside showLoginPage()");
		return "login/login";
	}
	
	/*@PostMapping(value ="registerUser")*/
	@RequestMapping(value = "/registerUser", method = { RequestMethod.GET, RequestMethod.POST })
	public String register(@ModelAttribute("user") User user ) {
		LOGGER.info("inside register()"+user);
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return "login/login";
	}
	
	/*@PostMapping("/login")*/
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login (@RequestParam("email") String email, @RequestParam("password") String password, ModelMap modelMap) {
		LOGGER.info("inside login and the email is: " +email);		
		/*User user = userRepository.findByEmail(email);*/
		boolean loginResponse = securityService.login(email, password);
		
		if(loginResponse) {
			return "findFlights";
		} else {
			modelMap.addAttribute("msg", "Invalid credentials ! Please try again");	
		}
		return "login/login";
		  
	}
	
	

}
