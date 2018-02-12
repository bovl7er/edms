package com.cmxv.datainterfaceslayer.daointerfaces;
import com.cmxv.general.MultiGetParams;
import com.cmxv.modellayer.DBentities.*;

import java.util.List;

public interface UserDAO {
    
	public List<UserBase> getAllUsers();
	
	public UserExt getUserById(int id);
	
	public UserBase getUserByLoginAndPass(String login, String pass);
	
	public void deleteUserById(int id);
	
	public int createOrUpdate(UserExt user);

	public UserBase getUserByLogin(String login);

	public UserBase getUserBaseById(int id);


	public UserWithRights getUserWithRights(String username, String password);

	List<UserExt> getMultipleUserExt(MultiGetParams params);

	Long getCountOfUsers();
}
