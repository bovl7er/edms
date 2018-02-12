package com.cmxv.datainterfaceslayer.daointerfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.RoleExt;

@Repository
public interface RoleDao {
	List<RoleBase> getAllRoles();

	RoleExt getRoleById(int id);

	int createOrUpdate(RoleExt role);

	void delete(int id);

	RoleBase getRoleByName(String roleName);

	RoleBase getRoleBaseById(int id);
}
