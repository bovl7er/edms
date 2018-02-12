package com.cmxv.bussinesslayer.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmxv.bussinessinterfaceslayer.RoleService;
import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.RoleExt;

@Path(value = "role")
@Component
public class RoleServiceRest {

	@Autowired RoleService roleService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "getAll")
	public List<RoleBase> getAllRoles() {
		return roleService.getAllRoles();
		// TODO Auto-generated method stub
		//return null;
	}

	/*@Override
	public RoleExt getRoleById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int createOrupdate(RoleExt role) {
		// TODO Auto-generated method stub
		return 0;
	}*/

}
