package com.cmxv.bussinesslayer.services;

import com.cmxv.bussinessinterfaceslayer.AuthorizationService;
import com.cmxv.datainterfaceslayer.daointerfaces.UserDAO;
import com.cmxv.modellayer.DBentities.UserBase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    
    private static final Logger log = Logger.getLogger(AuthorizationServiceImpl.class);

    @Autowired
    private UserDAO userBaseDAO;

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Проверка валидности логина и пароля пользователя
     *
     * @param login логин пользователя
     * @param pass пароль пользователя
     * @return страницу для перехода при различных вариантов валидации
     */
    @Override
    public boolean validate(String login, String pass) {

        try {
            UserBase user = userBaseDAO.getUserByLoginAndPass(login, pass);
            return user != null;
        } catch (Exception e) {
            log.error("Ошибка при проверке валидности логина и пароля", e);
            return false;
        }
    }
//--------------------------------------------------------------------------------------------------------------------
}
