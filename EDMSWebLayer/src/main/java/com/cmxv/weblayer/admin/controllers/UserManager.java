package com.cmxv.weblayer.admin.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cmxv.bussinessinterfaceslayer.RoleService;
import com.cmxv.bussinessinterfaceslayer.UserService;

import com.cmxv.exceptions.EDMSApplicationException;
import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.UserBase;
import com.cmxv.modellayer.DBentities.UserExt;
import com.cmxv.webservices.proxy.UserServiceWSDL;
import com.cmxv.webservices.proxy.UserServiceWSDLService;

import org.apache.log4j.Logger;

@Component
@Scope(value = "request")
@ManagedBean
public class UserManager {
    
    private static final Logger log = Logger.getLogger(UserManager.class);

    private int selectedUserId;

    private String username;

    private int userId;

    private String password;

    private boolean userRemoveState = false;

    private String fio;

    private DualListModel<RoleBase> rolesModel;

    private String userLabel = "Создание нового пользователя";

    /**
     * Контейнер всех пользователей на форме
     */
    private List<UserBase> allUsers;

    /**
     * Пользователь, которого редактируем
     */
    //private UserExt editedUser;
    @Autowired
    private RoleService roleService;

    /*@Autowired
    private UserService userService;*/
    
   // @Autowired
    private UserServiceWSDLService usis = new UserServiceWSDLService();
    private UserServiceWSDL userServiceWSDL =  usis.getUserServiceWSDLPort();
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Удаление пользователя
     *
     */
    public void deleteUser() {
        if (selectedUserId == 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Не выбран юзер для удаления", ""));
            return;
        }
        int usId = selectedUserId;
        userServiceWSDL.deleteUser(usId);
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Сохрание роли в БД
     *
     */
    public void saveUser() {
        UserExt user = new UserExt();
        user.setUserId(userId);

        user.setUserRemoveState(userRemoveState ? 'Y' : 'N');

        user.setUserFio(fio);
        user.setRoles(getRolesModel().getTarget());

        user.setUserLogin(username);
        user.setUserPassword(password);
        FacesContext context = FacesContext.getCurrentInstance();
        /**
         * Если ловим ошибку валидации, тогда не меняем данные пользователя.
         * Если ловим другую ошибку, то возвращаем все поля к тому виду, что был
         * до редактирования.
         */
        try {
            //Создание или обновление пользователя
            userId = userServiceWSDL.createOrUpdate(user);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Пользователь успешно сохранен", ""));

            setSelectedUserId(userId);

        } catch (EDMSApplicationException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Пользователь не прошел валидацию! Введите поля заново!", ex.getMessage()));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка!!!", "Пользователь не был сохранен по причине: " + e.getMessage() + " обратитесь к разработчику!"));
            log.error("Ошибка сохранения пользователя", e);
        } finally {
            if (userId == 0) {
                initCreateUser();
                cleanUserInfo();
            } else {
                userLabel = "Редактирование пользователя " + username;
                setSelectedUserId(userId);
                setUserInfo(userId);
            }
        }

    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Инициирование действий по редактированию пользователя
     *
     */
    public void initUserEdit() {
        userId = getSelectedUserId();

        setUserInfo(userId);
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Заполнение информации по конкретному пользователю
     *
     */
    private void setUserInfo(int userId) {
        //Получение информации
        UserExt user = userServiceWSDL.getUserById(userId);
        fio = user.getUserFio();
        password = user.getUserPassword();
        username = user.getUserLogin();
        userRemoveState = user.getUserRemoveState() == 'Y';
        userLabel = "Редактирование пользователя " + username;
        //Получение ролей пользователя и все возможных ролей
        List<RoleBase> userRoles = user.getRoles();
        List<RoleBase> allRoles = roleService.getAllRoles();
        if(userRoles != null) {
        	allRoles.removeAll(userRoles);
        }
        

        setRolesModel(new DualListModel<>(allRoles, userRoles));
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Очистка информации по пользователю
     *
     */
    private void cleanUserInfo() {
        fio = "";
        password = "";
        username = "";
        userRemoveState = false;

        List<RoleBase> userRoles = new ArrayList<RoleBase>();
        List<RoleBase> allRoles = roleService.getAllRoles();

        setRolesModel(new DualListModel<RoleBase>(allRoles, userRoles));
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Инициирование действий по созданию нового пользователя
     *
     */
    public void initCreateUser() {
        userLabel = "Создание нового пользователя";
    }
//--------------------------------------------------------------------------------------------------------------------

    public String getUsername() {
        return username;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setUsername(String username) {
        this.username = username;
    }
//--------------------------------------------------------------------------------------------------------------------

    public String getPassword() {
        return password;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setPassword(String password) {
        this.password = password;
    }
//--------------------------------------------------------------------------------------------------------------------

    public String getFio() {
        return fio;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setFio(String fio) {
        this.fio = fio;
    }
//--------------------------------------------------------------------------------------------------------------------

    public String getUserLabel() {
        return userLabel;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }
//--------------------------------------------------------------------------------------------------------------------

    public int getSelectedUserId() {
        return selectedUserId;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }
//--------------------------------------------------------------------------------------------------------------------

    public List<UserBase> getAllUsers() {
        return allUsers = (List<UserBase>) userServiceWSDL.getAllUsers().getCollection();
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setAllUsers(List<UserBase> allUsers) {
        this.allUsers = allUsers;
    }
//--------------------------------------------------------------------------------------------------------------------

    public int getUserId() {
        return userId;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setUserId(int userId) {
        this.userId = userId;
    }
//--------------------------------------------------------------------------------------------------------------------

    public DualListModel<RoleBase> getRolesModel() {
        if (rolesModel == null) {
            rolesModel = new DualListModel<RoleBase>(roleService.getAllRoles(), new ArrayList<RoleBase>());
        }

        return rolesModel;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setRolesModel(DualListModel<RoleBase> rolesModel) {
        this.rolesModel = rolesModel;
    }
//--------------------------------------------------------------------------------------------------------------------

    public boolean isUserRemoveState() {
        return userRemoveState;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setUserRemoveState(boolean userRemoveState) {
        this.userRemoveState = userRemoveState;
    }
//--------------------------------------------------------------------------------------------------------------------    
}
