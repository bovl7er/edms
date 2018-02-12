package com.cmxv.bussinesslayer.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.cmxv.bussinessinterfaceslayer.UserService;
import com.cmxv.modellayer.DBentities.UserBase;
import com.cmxv.modellayer.DBentities.UserExt;
import com.cmxv.modellayer.DBentities.UserWithRights;
import com.cmxv.modellayer.collections.Users;

@WebService
@SOAPBinding(style = Style.RPC)
public class UserServiceWSDL extends SpringBeanAutowiringSupport {
	
	@Autowired UserService userService;
	
	@PostConstruct
	public void init() {    
	     SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this); 
	}
	
	public UserExt getUserById(int id) { 
		
		UserExt result = userService.getUserById(id);
		return result;
	}


	public Users getAllUsers() {
		List<UserBase> users = userService.getAllUsers();
		Users usersEntity = new Users();
		usersEntity.setCollection(users);
		return usersEntity;
	}


	public void deleteUser(int id) {
		userService.deleteUser(id);
	}

	
	public int createOrUpdate(UserExt user) {
		
		return userService.createOrUpdate(user);
	}

	
	public UserBase getUserByLoginAndPass(String login, String pass) {
		
		return userService.getUserByLoginAndPass(login, pass);
	}

	
	public UserWithRights getUserWithRights(String username, String password) {
		return userService.getUserWithRights(username, password);
	}

}
