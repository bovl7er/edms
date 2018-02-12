package com.cmxv.weblayer.admin.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cmxv.bussinessinterfaceslayer.RightService;
import com.cmxv.bussinessinterfaceslayer.RoleService;
import com.cmxv.exceptions.EDMSApplicationException;
import com.cmxv.modellayer.DBentities.Right;
import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.RoleExt;
import org.apache.log4j.Logger;

@Component
@Scope(value = "request")
@ManagedBean
public class RoleManager {
    
    private static final Logger log = Logger.getLogger(RoleManager.class);

    @Autowired
    private RightService rightService;

    @Autowired
    private RoleService roleServise;

    private int roleId;

    private int selectedRoleId;

    private String editRoleLabel = "Создание новой роли";

    private String roleName;

    private List<Right> allRights;

    private List<String> selectedRights;

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Сохрание роли в БД
     *
     */
    public void saveRole() {
        FacesContext context = FacesContext.getCurrentInstance();

        RoleExt role = new RoleExt();
        role.setRoleId(roleId);
        role.setRoleName(roleName);

        List<Right> rightsList = new ArrayList<Right>();
        //Получение доступных действий для роли
        for (String rightId : selectedRights) {
            Right right = new Right();
            right.setRightId(Integer.parseInt(rightId));
            rightsList.add(right);
        }

        role.setRights(rightsList);
        //Создание или обновление роли
        try {
            roleId = roleServise.createOrupdate(role);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Роль успешно сохранена", ""));
            setSelectedRoleId(roleId);

        } catch (EDMSApplicationException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Роль не прошла валидацию!", ex.getMessage()));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка!!!", "Роль не была сохранена по причине: " + e.getMessage() + " обратитесь к разработчику!"));
            log.error("Ошибка сохранения роли в БД", e);

        } finally {
            if (roleId == 0) {
                cleanRoleFields();
                initCreateRole();

            } else {
                selectedRoleId = roleId;
                initRoleEdit();
            }
        }
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Очистка полей ролей
     *
     */
    private void cleanRoleFields() {
        roleId = 0;
        roleName = "";
        selectedRights = new ArrayList<String>();
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Удаление роли
     *
     */
    public void deleteRole() {
        if (selectedRoleId == 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Не выбрана роль для удаления", ""));
            return;
        }
        roleServise.delete(selectedRoleId);
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Инициирование действий по редактированию роли
     *
     */
    public void initRoleEdit() {
        RoleExt role = roleServise.getRoleById(selectedRoleId);

        roleId = role.getRoleId();
        roleName = role.getRoleName();

        editRoleLabel = "Редактирование роли " + roleName;

        List<Right> roleRights = role.getRights();
        if (roleRights != null) {
            if (selectedRights == null) {
                selectedRights = new ArrayList<String>();
            }

            for (Right right : roleRights) {
                selectedRights.add(String.valueOf(right.getRightId()));
            }
        }
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Инициирование действий по созданию новой роли
     *
     */
    public void initCreateRole() {
        editRoleLabel = "Создание новой роли";
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение всех ролей
     *
     * @return список ролей
     */
    public List<RoleBase> getAllRoles() {
        return roleServise.getAllRoles();
    }
//--------------------------------------------------------------------------------------------------------------------

    public String getEditRoleLabel() {
        return editRoleLabel;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setEditRoleLabel(String editRoleLabel) {
        this.editRoleLabel = editRoleLabel;
    }
//--------------------------------------------------------------------------------------------------------------------

    public String getRoleName() {
        return roleName;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
//--------------------------------------------------------------------------------------------------------------------

    public int getRoleId() {
        return roleId;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
//--------------------------------------------------------------------------------------------------------------------

    public List<Right> getAllRights() {
        allRights = rightService.getAllRights();
        return allRights;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setAllRights(List<Right> allRights) {
        this.allRights = allRights;
    }
//--------------------------------------------------------------------------------------------------------------------

    public List<String> getSelectedRights() {
        return selectedRights;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setSelectedRights(List<String> selectedRights) {
        this.selectedRights = selectedRights;
    }
//--------------------------------------------------------------------------------------------------------------------

    public int getSelectedRoleId() {
        return selectedRoleId;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setSelectedRoleId(int selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }
//--------------------------------------------------------------------------------------------------------------------    
}
