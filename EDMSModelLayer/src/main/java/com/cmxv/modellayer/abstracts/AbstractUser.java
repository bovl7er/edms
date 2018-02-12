package com.cmxv.modellayer.abstracts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@MappedSuperclass
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public abstract class AbstractUser {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "US_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private Integer userId;
	
	@Column(name = "USER_FIO")
    private String userFio;

    @Column(name = "USER_LOGIN")
    private String userLogin;

    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name = "USER_REMOVE_STATE")
    private char userRemoveState;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFio() {
        return userFio;
    }

    public void setUserFio(String userFio) {
        this.userFio = userFio;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public char getUserRemoveState() {
        return userRemoveState;
    }

    public void setUserRemoveState(char userRemoveState) {
        this.userRemoveState = userRemoveState;
    }
}
