package com.cmxv.bussinessinterfaceslayer;

import java.util.List;

import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.RoleExt;

public interface RoleService {
	List<RoleBase> getAllRoles();

	
	
	RoleExt getRoleById(int id);

	void delete(int id);
	
	int createOrupdate(RoleExt role);
}
