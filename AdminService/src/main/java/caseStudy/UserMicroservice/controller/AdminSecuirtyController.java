package caseStudy.UserMicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import caseStudy.UserMicroservice.SecurityConfig.JwtUtil;
import caseStudy.UserMicroservice.SecurityConfig.MyUserDetailsService;
import caseStudy.UserMicroservice.models.AdminDetails;
import caseStudy.UserMicroservice.service.AdminService;
import io.swagger.annotations.ApiOperation;

@RestController
public class AdminSecuirtyController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private AdminService userService;

	@Autowired
	private MyUserDetailsService userDetailsService;

	// Authenticates the admin using user name and password and returns JWT
	// token
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestParam String username, @RequestParam String password)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(jwt);
	}
	@PostMapping("/signup")
	@ApiOperation(value = "To Add Admin Details") 
	public String saveUser(@RequestBody AdminDetails adminDetails) {
		try {
			this.userService.addAdmin(adminDetails);
			return "Admin Login Successfully ";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Operation Failed";
	}


	

}