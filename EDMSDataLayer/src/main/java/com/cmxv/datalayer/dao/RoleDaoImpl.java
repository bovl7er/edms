package com.cmxv.datalayer.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cmxv.datainterfaceslayer.daointerfaces.RoleDao;
import com.cmxv.modellayer.DBentities.Right;
import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.RoleExt;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

//--------------------------------------------------------------------------------------------------------------------	
    /**
     * Получение всех ролей
     *
     * @return список всех ролей
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<RoleBase> getAllRoles() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criterea = session.createCriteria(RoleBase.class);
        criterea.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criterea.list();
    }
//--------------------------------------------------------------------------------------------------------------------	
    /**
     * Получение роли по ее ид
     *
     * @param id ид роли
     * @return роль по ид
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RoleExt getRoleById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criterea = session.createCriteria(RoleExt.class).add(Restrictions.eq("roleId", id));

        RoleExt role = (RoleExt) criterea.uniqueResult();
        Hibernate.initialize(role.getRights());

        return role;
    }
//--------------------------------------------------------------------------------------------------------------------	
    /**
     * Получение роли по ее ид
     *
     * @param id ид роли
     * @return роль по ид
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RoleBase getRoleBaseById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criterea = session.createCriteria(RoleBase.class).add(Restrictions.eq("roleId", id));
        RoleBase role = (RoleBase) criterea.uniqueResult();
        return role;
    }
//--------------------------------------------------------------------------------------------------------------------	
    /**
     * Получение роли по ее имени
     *
     * @param roleName имя роли в БД
     * @return роль по имени
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RoleBase getRoleByName(String roleName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criterea = session.createCriteria(RoleBase.class).add(Restrictions.eq("roleName", roleName));
        RoleBase role = (RoleBase) criterea.uniqueResult();
        return role;
    }
//--------------------------------------------------------------------------------------------------------------------	
    /**
     * Создание или обновление роли
     *
     * @param role объект роли
     * @return ид созданной или обновленной роли
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int createOrUpdate(RoleExt role) {
        Session session = sessionFactory.getCurrentSession();

        if (role.getRights() != null && role.getRights().size() > 0) {

            ArrayList<Right> roleRights = new ArrayList<Right>();
            for (Right right : role.getRights()) {
                roleRights.add((Right) session.load(Right.class, right.getRightId()));
            }

            role.setRights(roleRights);
        }

        if (role.getRoleId() == null || role.getRoleId() == 0) {
            session.save(role);
        } else {
            session.update(role);

        }
        return role.getRoleId();
    }
//--------------------------------------------------------------------------------------------------------------------	
    /**
     * Удаление роли
     *
     * @param id ид роли
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        RoleBase role = (RoleBase) session.load(RoleBase.class, id);
        session.delete(role);
    }
//--------------------------------------------------------------------------------------------------------------------	
}
