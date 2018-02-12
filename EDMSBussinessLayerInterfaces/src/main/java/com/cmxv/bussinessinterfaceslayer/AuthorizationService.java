package com.cmxv.bussinessinterfaceslayer;


public interface AuthorizationService {

    public boolean validate(String login, String pass);

   // public User getValidUser(String login);

}
