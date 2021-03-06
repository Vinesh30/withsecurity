package caseStudy.searchMicroservices.SecurityConfig;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import caseStudy.searchMicroservices.models.UserDetailsmodel;
import caseStudy.searchMicroservices.repo.UserRepository;



@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetailsmodel secModel = repo.findById(username).get();
		return new User(secModel.getUserName(), secModel.getPassword(), new ArrayList<>());
	}

	

}