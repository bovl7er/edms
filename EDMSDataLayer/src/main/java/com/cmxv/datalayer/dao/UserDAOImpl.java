package com.cmxv.datalayer.dao;

import com.cmxv.datainterfaceslayer.daointerfaces.UserDAO;
import com.cmxv.general.MultiGetParams;
import com.cmxv.modellayer.DBentities.DocumentBase;
import com.cmxv.modellayer.DBentities.RoleBase;
import com.cmxv.modellayer.DBentities.RoleExt;
import com.cmxv.modellayer.DBentities.UserBase;
import com.cmxv.modellayer.DBentities.UserExt;
import com.cmxv.modellayer.DBentities.UserWithRights;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Long getCountOfUsers() {
    	Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserBase.class).setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        return  count;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<UserExt> getMultipleUserExt(MultiGetParams params) {
    	 Session session = sessionFactory.getCurrentSession();
         Criteria criteria = session.createCriteria(UserExt.class);
         if(params != null && params.getPageNum() != null && params.getRecsOnPage() != null) {
        	 criteria.setFirstResult((params.getPageNum() - 1) * params.getRecsOnPage());
        	 criteria.setMaxResults(params.getRecsOnPage());
         }
         List<UserExt> result = (List<UserExt>) criteria.list();
         for(UserExt user : result) {
        	 Hibernate.initialize(user.getRoles());
         }
         return (List<UserExt>) criteria.list();
    	
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Добавляем или обновляем пользователя по ид
     *
     * @param user объект пользователя
     * @return ид пользователя
     */
    @Override
    public int createOrUpdate(UserExt user) {
        Session session = sessionFactory.getCurrentSession();
        /*
         * Добавляем роли если есть что добавить.
         */
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            List<RoleBase> userRoles = new ArrayList<RoleBase>();
            for (RoleBase role : user.getRoles()) {
                userRoles.add((RoleBase) session.load(RoleBase.class, role.getRoleId()));
            }

            user.setRoles(userRoles);
        }
        if(user.getUserRemoveState()!='Y') {
        	user.setUserRemoveState('N');
        }

        if (user.getUserId() == null || user.getUserId() == 0) {
            user.setUserRemoveState('N');
            session.save(user);
        } else {
            session.update(user);
        }

        return user.getUserId();
    }


//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получаем пользователя по его ид
     *
     * @param id ид пользователя
     * @return пользователя
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserExt getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserExt.class).add(Restrictions.eq("userId", id));
        UserExt result = (UserExt) criteria.uniqueResult();
        Hibernate.initialize(result.getRoles());
        return result;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserBase getUserBaseById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserBase.class).add(Restrictions.eq("userId", id));
        UserBase result = (UserBase) criteria.uniqueResult();
        return result;
    }

    /**
     * Получаем пользователя по его логину и паролю
     *
     * @param login логин
     * @param pass пароль
     * @return пользователя
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public UserBase getUserByLoginAndPass(String login, String pass) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserBase.class).add(Restrictions.eq("userLogin", login)).add(Restrictions.eq("userPassword", pass));
        criteria.add(Restrictions.eq("userRemoveState", 'N'));
        return (UserBase) criteria.uniqueResult();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Удаляем пользователя по ид
     *
     * @param id ид пользователя
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserBase user = (UserBase) session.load(UserBase.class, id);
        session.delete(user);
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получаем всех пользователей
     *
     * @return список пользователей
     */
    @Override
    public ArrayList<UserBase> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserBase.class);
        return (ArrayList<UserBase>) criteria.list();
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получаем пользователя по логину
     *
     * @param login логин пользователя
     * @return пользователя
     */
    @Override
    public UserBase getUserByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserBase.class).add(Restrictions.eq("userLogin", login));
        UserBase result = (UserBase) criteria.uniqueResult();
        return result;
    }

	@Override
	public UserWithRights getUserWithRights(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserWithRights.class).add(Restrictions.eq("userLogin", username)).add(Restrictions.eq("userPassword", password));
        UserWithRights result = (UserWithRights) criteria.uniqueResult();
        if(result != null) {
        	Hibernate.initialize(result.getRoles());
        	if(result.getRoles() != null) {
        		for(RoleExt role : result.getRoles()) {
        			Hibernate.initialize(role.getRights());
        		}
        	}
        }
	
        //criteria.add(Restrictions.eq("userRemoveState", 'N'));
        return result;
	}
}
