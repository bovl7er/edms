package com.cmxv.general;

public class UserFilterParams {
	private String userFio;
	private UserFilterParams userFioFiltering;
	private String userLogin;
	private UserFilterParams userLoginFiltering;
	public String getUserFio() {
		return userFio;
	}
	public void setUserFio(String userFio) {
		this.userFio = userFio;
	}
	public UserFilterParams getUserFioFiltering() {
		return userFioFiltering;
	}
	public void setUserFioFiltering(UserFilterParams userFioFiltering) {
		this.userFioFiltering = userFioFiltering;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public UserFilterParams getUserLoginFiltering() {
		return userLoginFiltering;
	}
	public void setUserLoginFiltering(UserFilterParams userLoginFiltering) {
		this.userLoginFiltering = userLoginFiltering;
	}
}
