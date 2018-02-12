package com.cmxv.bussinesslayer.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmxv.bussinessinterfaceslayer.RoleService;
import com.cmxv.datainterfaceslayer.daointerfaces.RoleDao;
import com.cmxv.exceptions.EDMSApplicationException;
import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.RoleExt;

@Repository
public class RoleServiceImpl implements RoleService {
	{System.out.println("init role service");}
    @Autowired
    private RoleDao roleDao;
//--------------------------------------------------------------------------------------------------------------------	

    /**
     * Получение списка ролей
     *
     * @return список ролей
     */
    @Override
    public List<RoleBase> getAllRoles() {

        List<RoleBase> result = roleDao.getAllRoles();

        return result;
    }
//--------------------------------------------------------------------------------------------------------------------	

    /**
     * Получение роли по ее ид
     *
     * @param id ид роли
     * @return роль
     */
    @Override
    public RoleExt getRoleById(int id) {
        RoleExt role = roleDao.getRoleById(id);
        return role;
    }
//--------------------------------------------------------------------------------------------------------------------	

    /**
     * Удаление роли по ид
     *
     * @param id ид роли
     */
    @Override
    public void delete(int id) {
        roleDao.delete(id);
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
     * Создание или обновление роли
     *
     * @param role объект роли
     * @return ид созданной или обновленной роли
     */
    @Override

    public int createOrupdate(RoleExt role) {
        //Проверка валидности названия роли
        checkStringField(role.getRoleName(), "^[a-zA-Z0-9_-]{3,15}$", "Имя роли");

        if (role.getRoleId() != null && role.getRoleId() != 0) {
            RoleBase existRole = roleDao.getRoleBaseById(role.getRoleId());
            if (!existRole.getRoleName().equals(role.getRoleName())) {
                RoleBase roleBase = roleDao.getRoleByName(role.getRoleName());
                if (roleBase != null) {
                    throw new EDMSApplicationException("Нельзя переименовать роль " + existRole.getRoleName() + ", роль с именем " + role.getRoleName() + " уже существует.");
                }
            }
        } else {
            RoleBase roleBase = roleDao.getRoleByName(role.getRoleName());
            if (roleBase != null) {
                throw new EDMSApplicationException("Нельзя создать роль с именем " + role.getRoleName() + ". Роль с таким именем уже существует.");
            }
        }

        return roleDao.createOrUpdate(role);
    }
//--------------------------------------------------------------------------------------------------------------------	
}
