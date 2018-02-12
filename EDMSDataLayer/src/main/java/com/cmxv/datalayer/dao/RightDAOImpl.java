package com.cmxv.datalayer.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cmxv.datainterfaceslayer.daointerfaces.RightDao;
import com.cmxv.modellayer.DBentities.Right;

@Repository
public class RightDAOImpl implements RightDao {

    @Autowired
    private SessionFactory sessionFactory;
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение всех правил
     *
     * @return список правил
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public List<Right> getAllRights() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criterea = session.createCriteria(Right.class);
        //criterea.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criterea.list();
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение правила по ид
     *
     * @param id ид правила
     * @return соответствующее правило
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public Right getRightById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criterea = session.createCriteria(Right.class).add(Restrictions.eq("rightId", id));

        Right right = (Right) criterea.uniqueResult();
        return right;
    }
//--------------------------------------------------------------------------------------------------------------------
}
