package com.cmxv.ui.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmxv.bussinessinterfaceslayer.RoleService;
import com.cmxv.modellayer.DBentities.RoleBase;

@Component
//@FacesConverter(forClass=Role.class,value = "roleConverter")
public class RoleConverter implements Converter {

    @Autowired
    RoleService roleService;
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Конвертация в объект типа RoleBase
     *
     * @param context контекст приложения
     * @param component
     * @param value ид роли
     * @return 
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int id = Integer.parseInt(value);
        RoleBase role = new RoleBase();
        role.setRoleId(id);
        return role;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Конвертация в строку
     *
     * @param context контекст приложения
     * @param component
     * @param value роль
     * @return 
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((RoleBase) value).getRoleId());

    }
//--------------------------------------------------------------------------------------------------------------------
}
