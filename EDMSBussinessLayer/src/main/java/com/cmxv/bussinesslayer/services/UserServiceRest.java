package com.cmxv.bussinesslayer.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmxv.bussinessinterfaceslayer.UserService;
import com.cmxv.datainterfaceslayer.daointerfaces.UserDAO;
import com.cmxv.general.MultiGetParams;
import com.cmxv.modellayer.DBentities.UserBase;
import com.cmxv.modellayer.DBentities.UserExt;
import com.cmxv.modellayer.DBentities.UserWithRights;

@Path(value = "user")
@Component
public class UserServiceRest {
	
	@Autowired 
	private UserService userservice;
	@Autowired 
	private UserDAO userDao;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRowsCount")
	public long getRowsCount() {
		// TODO Auto-generated method stub
		//System.out.println("getRowsCount");
		return userDao.getCountOfUsers();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAll")
	public List<UserBase> getAllUsers() {
		// TODO Auto-generated method stub
		System.out.println("getalluser");
		return userservice.getAllUsers();
	}
	
	
	@POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/multiget")
	public List<UserExt> multipleGetUsers(MultiGetParams params) {
		System.out.println(params);
		// TODO Auto-generated method stub
		//System.out.println("multiget");
		//MultiGetParams params = new MultiGetParams();
		return userDao.getMultipleUserExt(params);
		//return null;
	}


	@DELETE
	@Path("{userId}")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Path("getAll")
	public void deleteUser(@PathParam("userId") int id) {
		// TODO Auto-generated method stub
		userservice.deleteUser(id);
		
	}

	@POST
	//Consumes(MediaType.APPLICATION_JSON)
	
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	//@Path("{userExt}")
	public int createOrUpdate(/*@PathParam("userExt")*/ UserExt user) {
		// TODO Auto-generated method stub
		return userservice.createOrUpdate(user);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userId}")
	public UserExt getUserById(@PathParam("userId") int id) {
		// TODO Auto-generated method stub
		return userservice.getUserById(id);
	}


	public UserBase getUserByLoginAndPass(String login, String pass) {
		// TODO Auto-generated method stub
		return userservice.getUserByLoginAndPass(login, pass);
	}


	public UserWithRights getUserWithRights(String username, String password) {
		// TODO Auto-generated method stub
		return userservice.getUserWithRights(username, password);
	}

}
