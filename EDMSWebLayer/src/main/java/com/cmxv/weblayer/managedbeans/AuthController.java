package com.cmxv.weblayer.managedbeans;
import com.cmxv.weblayer.sessionUtil.SessionBean;
import com.cmxv.bussinessinterfaceslayer.AuthorizationService;
import com.cmxv.bussinessinterfaceslayer.UserService;
import com.cmxv.modellayer.DBentities.Right;
import com.cmxv.modellayer.DBentities.RoleExt;
import com.cmxv.modellayer.DBentities.UserExt;
import com.cmxv.modellayer.DBentities.UserWithRights;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
@ManagedBean
@RequestScoped
public class AuthController {    
    private String pwd;
    private String user;
    private String userFio;
    @Autowired
    private AuthorizationService authorizationService;
    
    @Autowired
    UserService userService;
    
    public String getPwd() {
        return pwd;
    }
 
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }
 
    public void setUser(String user) {
        this.user = user;
    }
    
//--------------------------------------------------------------------------------------------------------------------

    public boolean checkRights(String rightName) {
    	
    	UserWithRights user = SessionBean.getUser();
    	if(user != null && user.getRoles() != null) {
    		
    		for(RoleExt role : user.getRoles()) {
    			if(role.getRights() != null) {
    				
    				for(Right right : role.getRights()) {
    					if(right.getValue().equals(rightName)) {
    						return true;
    						
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Получения ФИО пользователя,из текущей сессии
     * @return ФИО пользователя
     */
    public String getUserFio() {
        //Получение текущего пользователя из сессии
        UserWithRights userBase = SessionBean.getUser();
        userFio = userBase.getUserFio();
       
        return userFio;
    }
    
//--------------------------------------------------------------------------------------------------------------------

    public void setUserFio(String userFio) {
        this.userFio = userFio;
    }
    
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Валидация при логине пользователя в приложении
     * @return страницу для перехода при различных вариантов валидации
     */
    public String validateUsernamePassword() {
        //Проверка валидности введенных логина и пароля
         boolean valid = authorizationService.validate(user, pwd);
          if (valid) {
             HttpSession session = SessionBean.getSession();
             //Получение авторизованного пользователя и пишем его в сессию
             UserWithRights authorizedUser = userService.getUserWithRights(user, pwd);
             session.setAttribute("sessionUser", authorizedUser);
            return "main";
          } else {
            //Описание ошибки валидации
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd"
                                    + " or You are deleted by the Administrator",
                            "Please try enter correct username and Password"));
            return "login";
        }
    	
    	
    }
    
//--------------------------------------------------------------------------------------------------------------------
 
    /**
      * Выход в окно логина
      * @return страницу логина
      */
    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.invalidate();
        return "login";
    }
    
//--------------------------------------------------------------------------------------------------------------------

}


 
