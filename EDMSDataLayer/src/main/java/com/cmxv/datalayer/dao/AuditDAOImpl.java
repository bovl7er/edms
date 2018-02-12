package com.cmxv.datalayer.dao;

import com.cmxv.datainterfaceslayer.daointerfaces.AuditDAO;
import com.cmxv.modellayer.DBentities.AuditDocumentView;
import com.cmxv.modellayer.DBentities.AuditUserView;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AuditDAOImpl implements AuditDAO {

    private static final Logger log = Logger.getLogger(AuditDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение таблицы аудита документов
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return таблицу аудита документов
     */
    @Override
    @Transactional(readOnly = true)
    public List<AuditDocumentView> getDocumentAuditTable(Integer startId, Integer endId) {
        Session session = sessionFactory.getCurrentSession();
        List<AuditDocumentView> auditTable = null;
        try {
            Criteria criteria = session.createCriteria(AuditDocumentView.class);
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            //Сортировка по возрастанию
            criteria.addOrder(Order.asc("auditDocId"));
            auditTable = (List<AuditDocumentView>) criteria.list();
            log.info("Получили список аудита документов из " + auditTable.size() + " записей");
        } catch (HibernateException e) {
            log.error("Ошибка получения таблицы аудита документов", e);
        }
        return auditTable;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение отфильтрованный таблицы аудита документов
     * @param startDate начальная дата для фильтрации
     * @param endDate конечная дата для фильтрации
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return отфильтрованную таблицу аудита документов
     */
    @Override
    @Transactional(readOnly = true)
    public List<AuditDocumentView> getDocumentAuditTableByFilter(Date startDate, Date endDate, Integer startId, Integer endId) {
        Session session = sessionFactory.getCurrentSession();
        List<AuditDocumentView> auditTable = null;
        try {
            Criteria criteria = session.createCriteria(AuditDocumentView.class);
            //фильтрация по дате
            if (startDate != null) {
                criteria.add(Restrictions.between("changeDate", startDate, endDate));
            } else {
                criteria.add(Restrictions.le("changeDate", endDate));
            }
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            //Сортировка по возрастанию
            criteria.addOrder(Order.asc("auditDocId"));
            auditTable = (List<AuditDocumentView>) criteria.list();
            log.info("Получили отфильтрованный список аудита документов из " + auditTable.size() + " записей");
        } catch (HibernateException e) {
            log.error("Ошибка получения отфильтрованный таблицы аудита документов", e);
        }
        return auditTable;
    }

//--------------------------------------------------------------------------------------------------------------------    
    /**
     * Получение таблицы аудита пользователей
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return таблицу аудита пользователей
     */
    @Override
    @Transactional(readOnly = true)
    public List<AuditUserView> getUserAuditTable(Integer startId, Integer endId) {
        Session session = sessionFactory.getCurrentSession();
        List<AuditUserView> auditTable = null;
        try {
            Criteria criteria = session.createCriteria(AuditUserView.class);
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            //Сортировка по возрастанию
            criteria.addOrder(Order.asc("auditUserId"));
            auditTable = (List<AuditUserView>) criteria.list();
            log.info("Получили список аудита пользователей из " + auditTable.size() + " записей");
        } catch (HibernateException e) {
            log.error("Ошибка получения таблицы аудита пользователей", e);
        }
        return auditTable;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение отфильтрованный таблицы аудита пользователей
     * @param startDate начальная дата для фильтрации
     * @param endDate конечная дата для фильтрации
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return отфильтрованную таблицу аудита пользователей
     */
    @Override
    @Transactional(readOnly = true)
    public List<AuditUserView> getUserAuditTableByFilter(Date startDate, Date endDate, Integer startId, Integer endId) {
        Session session = sessionFactory.getCurrentSession();
        List<AuditUserView> auditTable = null;
        try {
            Criteria criteria = session.createCriteria(AuditUserView.class);
            //фильтрация по дате
            if (startDate != null) {
                criteria.add(Restrictions.between("changeDate", startDate, endDate));
            } else {
                criteria.add(Restrictions.le("changeDate", endDate));
            }
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            //Сортировка по возрастанию
            criteria.addOrder(Order.asc("auditUserId"));
            auditTable = (List<AuditUserView>) criteria.list();
            log.info("Получили отфильтрованный список аудита пользователей из " + auditTable.size() + " записей");
        } catch (HibernateException e) {
            log.error("Ошибка получения отфильтрованный таблицы аудита пользователей", e);
        }
        return auditTable;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение количества записей в таблице аудита документов
     * @return количество записей в таблице аудита документов
     */
    @Override
    @Transactional(readOnly = true)
    public long getAuditDocCount() {
        Long count = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(AuditDocumentView.class);
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
            log.info("Получили количество записей в списке аудита документов: " + count);
        } catch (HibernateException e) {
            log.error("Ошибка получения количества записей в таблице аудита документов", e);
        }
        return count;
    }

//-------------------------------------------------------------------------------------------------------------------- 
    /**
     * Получение количества записей в отфильтрованной таблице аудита документов
     * @param startDate начальная дата для фильтрации
     * @param endDate конечная дата для фильтрации
     * @return количество записей в отфильтрованной таблице аудита документов
     */
    @Override
    @Transactional(readOnly = true)
    public long getFilterAuditDocCount(Date startDate, Date endDate) {
        Long count = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(AuditDocumentView.class);
            if (startDate != null) {
                criteria.add(Restrictions.between("changeDate", startDate, endDate));
            } else {
                criteria.add(Restrictions.le("changeDate", endDate));
            }
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
            log.info("Получили количество записей в отфильтрованном списке аудита документов: " + count);
        } catch (HibernateException e) {
            log.error("Ошибка получения количества записей в отфильтрованной таблице аудита документов", e);
        }
        return count;
    }

//--------------------------------------------------------------------------------------------------------------------    
    /**
     * Получение количества записей в таблице аудита пользователей
     * @return количество записей в таблице аудита пользователей
     */
    @Override
    @Transactional(readOnly = true)
    public long getAuditUserCount() {
        Long count = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(AuditUserView.class);
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
            log.info("Получили количество записей в списке аудита пользователей: " + count);
        } catch (HibernateException e) {
            log.error("Ошибка получения количества записей в таблице аудита пользователей", e);
        }
        return count;
    }

//--------------------------------------------------------------------------------------------------------------------   
    /**
     * Получение количества записей в отфильтрованной таблице аудита пользователей
     * @param startDate начальная дата для фильтрации
     * @param endDate конечная дата для фильтрации
     * @return количество записей в отфильтрованной таблице аудита пользователей
     */
    @Override
    @Transactional(readOnly = true)
    public long getFilterAuditUserCount(Date startDate, Date endDate) {
        Long count = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(AuditUserView.class);
            if (startDate != null) {
                criteria.add(Restrictions.between("changeDate", startDate, endDate));
            } else {
                criteria.add(Restrictions.le("changeDate", endDate));
            }
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
            log.info("Получили количество записей в отфильтрованном списке аудита пользователей: " + count);
        } catch (HibernateException e) {
            log.error("Ошибка получения количества записей в отфильтрованной таблице аудита пользователей", e);
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------
}
