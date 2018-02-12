package com.cmxv.bussinesslayer.services;

import com.cmxv.bussinessinterfaceslayer.UserService;
import com.cmxv.datainterfaceslayer.daointerfaces.UserDAO;
import com.cmxv.exceptions.EDMSApplicationException;
import com.cmxv.modellayer.DBentities.UserBase;
import com.cmxv.modellayer.DBentities.UserExt;
import com.cmxv.modellayer.DBentities.UserWithRights;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userBaseDAO;

    
    

    
  //--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение пользователя по ид
     *
     * @param id ид пользователя
     * @return пользователя
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserExt getUserById(int id) {
        return userBaseDAO.getUserById(id);
    }
    
//-------------------------------------------------------------------------------------------------------------------- 
    /**
     * Удаление пользователя по ид
     *
     * @param id ид пользователя
     */
    @Override
    public void deleteUser(int id) {
        userBaseDAO.deleteUserById(id);
    }
//--------------------------------------------------------------------------------------------------------------------    
    /**
     * Получение всех пользователей
     *
     * @return список пользователей
     */
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<UserBase> getAllUsers() {

    	ArrayList<UserBase> users = (ArrayList<UserBase>) userBaseDAO.getAllUsers();
        //UserBase[] list = new UserBase[];
        //;
    	//users.to
    	return    users;
    }
//--------------------------------------------------------------------------------------------------------------------    
    /**
     * Проверка валидности полученнной строки в соответствии с регулярным
     * выражением,переданой вторым параметром
     *
     * @param fieldVal значение строки
     * @param regexp регулярное выражение для проверки
     * @param fieldName название поля
     */
    private void checkStringField(String fieldVal, String regexp, String fieldName) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(fieldVal);
        if (!matcher.matches()) {
            throw new EDMSApplicationException(fieldName + " не соответсивует требованиям системы " + regexp);
        }
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Создание или обновление пользователя
     *
     * @param user объект пользователя
     * @return ид созданной или обновленной пользователь
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int createOrUpdate(UserExt user) {
        //Проверка валидности логина
        checkStringField(user.getUserLogin(), "^[a-zA-Z0-9_-]{3,15}$", "Логин");
        //Проверка валидности пароля
        checkStringField(user.getUserPassword(), "^[a-zA-Z0-9_-]{3,15}$", "Пароль");
        //Проверка валидности ФИО пользователя
        checkStringField(user.getUserFio(), "^[a-zA-Zа-яА-я\\s]+$", "ФИО");

        if (user.getUserId() != null && user.getUserId() != 0) {
            UserBase existUser = userBaseDAO.getUserBaseById(user.getUserId());
            if (!existUser.getUserLogin().equals(user.getUserLogin())) {
                UserBase userBase = userBaseDAO.getUserByLogin(user.getUserLogin());
                if (userBase != null) {
                    throw new EDMSApplicationException("Не удается переименовать пользователя, пользователь с логином " + user.getUserLogin() + " уже существует");
                }
            }

        } else {
            UserBase existUser = userBaseDAO.getUserByLogin(user.getUserLogin());
            if (existUser != null) {
                throw new EDMSApplicationException("Пользователь с логином " + user.getUserLogin() + " уже существует");
            }
        }

        int userId = userBaseDAO.createOrUpdate(user);
        return userId;
    }

//--------------------------------------------------------------------------------------------------------------------    
    /**
     * Получение пользователя по логину и паролю
     *
     * @param login логин пользователя
     * @param pass пароль пользователя
     * @return пользователя
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserBase getUserByLoginAndPass(String login, String pass) {
        return userBaseDAO.getUserByLoginAndPass(login, pass);
    }
//--------------------------------------------------------------------------------------------------------------------    
    /**
     * Получение пользователя с правами
     *
     * @param username имя пользователя
     * @param password ароль пользователя
     * @return пользователя
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserWithRights getUserWithRights(String username, String password) {
        return userBaseDAO.getUserWithRights(username, password);
    }
//--------------------------------------------------------------------------------------------------------------------

}
