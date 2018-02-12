package com.cmxv.bussinessinterfaceslayer;


import java.util.List;

import com.cmxv.modellayer.DBentities.UserBase;
import com.cmxv.modellayer.DBentities.UserExt;
import com.cmxv.modellayer.DBentities.UserWithRights;

public interface UserService {
    
	public List<UserBase> getAllUsers();
	
   // public void updateUser(UserExt user);

    public void deleteUser(int id);
    
   // public void createUser(String userFio, String userLogin, String userPassword);

    public int createOrUpdate(UserExt user);
    
    public UserExt getUserById(int id);

    public UserBase getUserByLoginAndPass(String login,String pass);


	public UserWithRights getUserWithRights(String username, String password);

    //public List<DBUser> getDeletedUsers(char userRemoveState);
}
