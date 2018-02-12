package com.cmxv.datalayer.dao;

import com.cmxv.datainterfaceslayer.daointerfaces.DocumentBaseDAO;
import com.cmxv.modellayer.DBentities.DocumentAttachmentBase;
import com.cmxv.modellayer.DBentities.DocumentBase;
import com.cmxv.modellayer.DBentities.DocumentTypeBase;
import com.cmxv.modellayer.DBentities.StateBase;
import com.cmxv.modellayer.DBentities.StatementDocumentBase;
import com.cmxv.modellayer.DBentities.UserExt;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DocumentBaseDAOImpl implements DocumentBaseDAO {

    private static final Logger log = Logger.getLogger(DocumentBaseDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Добавляем документ в БД
     *
     * @param docType ид типа документа
     * @param docState ид статуса документа
     * @param docAuthor ид автора документа
     * @param docUser ид пользователя,который работает с документом в данный
     * момент
     * @return ид добавленного пользователя
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public int addDocument(Integer docType, Integer docState, Integer docAuthor, Integer docUser) {
        Session session = sessionFactory.getCurrentSession();
        DocumentBase document = new DocumentBase();
        try {
            UserExt user = (UserExt) session.load(UserExt.class, docUser);
            UserExt author = (UserExt) session.load(UserExt.class, docAuthor);
            StateBase state = (StateBase) session.load(StateBase.class, docState);
            DocumentTypeBase documentType = (DocumentTypeBase) session.load(DocumentTypeBase.class, docType);
            document.setDocumentAuthor(author);
            document.setDocumentState(state);
            document.setDocumentType(documentType);
            document.setDocumentUser(user);
            author.getCreatedDocuments().add(document);
            user.getDocuments().add(document);
            documentType.getDocuments().add(document);
            session.save(document);
        } catch (Exception error) {
            log.error("Ошибка добавления пользователя в БД", error);
        }

        return document.getDocumentId();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Добавление содержимого заявления в БД
     *
     * @param docId ид документа
     * @param subject тема документа
     * @param author автор документа
     * @param date дата создания документа
     * @param task поставленный задачи документа
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public void addStatementDocumentContent(Integer docId, String subject, String author, Date date, String task) {
        Session session = sessionFactory.getCurrentSession();
        StatementDocumentBase statementDocument = new StatementDocumentBase();
        try {
            DocumentBase document = (DocumentBase) session.load(DocumentBase.class, docId);
            statementDocument.setDocument(document);
            statementDocument.setSubject(subject);
            statementDocument.setAuthor(author);
            statementDocument.setDate(date);
            statementDocument.setTask(task);
            document.setStatementContent(statementDocument);
            session.save(statementDocument);
        } catch (Exception error) {
            log.error("Ошибка добавления содержимого заявления в БД", error);
        }
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение списка документов определенного типа
     *
     * @param docType ид типа документа для отображения
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return список документов определенного типа
     */
    @Override
    public List<DocumentBase> findDocumentsByType(Integer docType, Integer startId, Integer endId) {
        Session session = sessionFactory.getCurrentSession();
        List<DocumentBase> documents = null;
        try {
            DocumentTypeBase documentType = (DocumentTypeBase) session.load(DocumentTypeBase.class, docType);
            StateBase state = (StateBase) session.load(StateBase.class, 4);
            Criteria criteria = session.createCriteria(DocumentBase.class)
                    .add(Restrictions.eq("documentType", documentType));
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            criteria.add(
                    Restrictions.not(
                            Restrictions.in("documentState", new StateBase[]{state})
                    )
            );
            //Сортировка по возрастанию
            criteria.addOrder(Order.asc("documentId"));
            documents = criteria.list();
        } catch (HibernateException error) {
            log.error("Ошибка получения списка документов определенного типа", error);
        }
        return documents;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение списка документов со статусом ПРИНЯТО
     *
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return список документов со статусом ПРИНЯТО
     */
    @Override
    public List<DocumentBase> findAcceptDocuments(Integer startId, Integer endId) {
        Session session = sessionFactory.getCurrentSession();
        StateBase state = (StateBase) session.load(StateBase.class, 4);
        List<DocumentBase> documents = null;
        try {
            Criteria criteria = session.createCriteria(DocumentBase.class)
                    .add(Restrictions.eq("documentState", state));
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            //Сортировка по возрастанию
            criteria.addOrder(Order.asc("documentId"));
            documents = criteria.list();
        } catch (HibernateException error) {
            log.error("Ошибка получение списка документов со статусом 'ПРИНЯТО'", error);
        }
        return documents;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение содержания документа по его ид
     *
     * @param docId ид документа
     * @return содержание документа
     */
    @Override
    @Transactional(readOnly = true)
    public StatementDocumentBase getDocumentContent(Integer docId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            DocumentBase document = (DocumentBase) session.load(DocumentBase.class, docId);
            Criteria criteria = session.createCriteria(StatementDocumentBase.class).add(Restrictions.eq("document", document));
            return (StatementDocumentBase) criteria.uniqueResult();
        } catch (HibernateException error) {
            log.error("Ошибка получения содержания документа", error);
            return null;
        }
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Добавление вложений к документу
     *
     * @param docId ид документа
     * @param attacments список вложений
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public void addDocumentAttachment(Integer docId, Map<String, byte[]> attacments) {
        Session session = sessionFactory.getCurrentSession();
        try {
            DocumentBase document = (DocumentBase) session.load(DocumentBase.class, docId);
            for (Entry<String, byte[]> attachment : attacments.entrySet()) {
                DocumentAttachmentBase docAttachment = new DocumentAttachmentBase();
                docAttachment.setDocument(document);
                docAttachment.setFile(attachment.getValue());
                docAttachment.setFileName(attachment.getKey());
                document.getDocumentAttachments().add(docAttachment);
                session.save(docAttachment);
            }
        } catch (Exception error) {
            log.error("Ошибка добавления вложений к документу", error);
        }
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение списка вложений к документу
     *
     * @param docId ид документа
     * @return список вложений к докуементу
     */
    @Override
    @Transactional(readOnly = true)
    public List<DocumentAttachmentBase> getDocumentAttachments(Integer docId) {
        List<DocumentAttachmentBase> attachments = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            DocumentBase document = (DocumentBase) session.load(DocumentBase.class, docId);
            Criteria criteria = session.createCriteria(DocumentAttachmentBase.class).add(Restrictions.eq("document", document));
            attachments = criteria.list();
        } catch (HibernateException error) {
            log.error("Ошибка получения списка вложений к документу", error);
        }
        return attachments;

    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Обновление статуса документа
     *
     * @param docId ид документа
     * @param newStateId ид нового статуса
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public void updateDocumentState(Integer docId, Integer newStateId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            StateBase state = (StateBase) session.load(StateBase.class, newStateId);
            DocumentBase document = (DocumentBase) session.load(DocumentBase.class, docId);
            document.setDocumentState(state);
            session.update(document);
        } catch (Exception error) {
            log.error("Ошибка обновления статуса документа", error);
        }
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Обновление содержания документа
     *
     * @param statementId ид заявления
     * @param subject тема документа
     * @param author автор документа
     * @param task поставленные задачи для документа
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public void updateStatementDocument(Integer statementId, String subject, String author, String task) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(StatementDocumentBase.class).add(Restrictions.eq("statementDocId", statementId));
            StatementDocumentBase statement = (StatementDocumentBase) criteria.uniqueResult();
            if (!subject.equals("")) {
                statement.setSubject(subject);
            }
            if (!author.equals("")) {
                statement.setAuthor(author);
            }
            if (!task.equals("")) {
                statement.setTask(task);
            }
            session.update(statement);
        } catch (HibernateException error) {
            log.error("Ошибка обновления содержания документа", error);
        }
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение содержания заявления по ид документа
     *
     * @param docId ид документа
     * @return ид содержания документа
     */
    @Override
    @Transactional(readOnly = true)
    public int getStatementByDocId(Integer docId) {
        Integer statementId = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            DocumentBase document = (DocumentBase) session.load(DocumentBase.class, docId);
            Criteria criteria = session.createCriteria(StatementDocumentBase.class).add(Restrictions.eq("document", document));
            StatementDocumentBase statement = (StatementDocumentBase) criteria.uniqueResult();
            statementId = statement.getStatementDocId();
        } catch (Exception error) {
            log.error("Ошибка получение содержания заявления по ид документа", error);
        }
        return statementId;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества документов
     *
     * @return количество документов
     */
    @Override
    @Transactional(readOnly = true)
    public long getDocumentCount() {
        Long count = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(DocumentBase.class).setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        } catch (HibernateException error) {
            log.error("Ошибка получения количества документов", error);
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение всех докуемнтов
     *
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return список всех документов
     */
    @Override
    public List<DocumentBase> getAllDocuments(Integer startId, Integer endId) {
        List<DocumentBase> documents = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(DocumentBase.class);
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            criteria.addOrder(Order.asc("documentId"));
            documents = (List<DocumentBase>) criteria.list();
        } catch (Exception error) {
            log.error("Ошибка получения всех докуемнтов", error);
        }
        return documents;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение отфильтрованного списка документов
     *
     * @param startDate начальная дата для фильтрации
     * @param endDate конечная дата для фильтрации
     * @param searchWord строка для фильтрации по фио
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param docTypeId тип документа для фильтрации
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return отфильтрованный спискок документов
     */
    @Override
    public List<DocumentBase> getFilterDocuments(Date startDate, Date endDate, String searchWord, Integer docTypeId, Integer startId, Integer endId) {
        List<DocumentBase> documents = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(DocumentBase.class);
            //фильтрация по фио
            if (searchWord != null) {
                criteria.createAlias("documentAuthor", "docAuth").add(Restrictions.like("docAuth.userFio", "%" + searchWord + "%"));
            }
            //фильтрация по типу
            if (docTypeId != null && docTypeId != 0) {
                if (docTypeId == 2) {
                    DocumentTypeBase type = (DocumentTypeBase) session.load(DocumentTypeBase.class, docTypeId);
                    StateBase state = (StateBase) session.load(StateBase.class, 4);
                    criteria.add(Restrictions.eq("documentType", type));
                    //фильтрация по дате
                    if (startDate != null) {
                        criteria.createAlias("statementContent", "content").add(Restrictions.between("content.date", startDate, endDate));
                    } else {
                        criteria.createAlias("statementContent", "content").add(Restrictions.le("content.date", endDate));
                    }
                    criteria.add(
                            Restrictions.not(
                                    Restrictions.in("documentState", new StateBase[]{state})
                            )
                    );
                }
                //фильтрация по типу
                if (docTypeId == 1) {
                    StateBase state = (StateBase) session.load(StateBase.class, 4);
                    criteria.add(Restrictions.eq("documentState", state));
                    //фильтрация по дате
                    if (startDate != null) {
                        criteria.createAlias("statementContent", "content").add(Restrictions.between("content.date", startDate, endDate));
                    } else {
                        criteria.createAlias("statementContent", "content").add(Restrictions.le("content.date", endDate));
                    }
                }
                //фильтрация по типу
            } else {
                //фильтрация по дате
                if (startDate != null) {
                    criteria.createAlias("statementContent", "content").add(Restrictions.between("content.date", startDate, endDate));
                } else {
                    criteria.createAlias("statementContent", "content").add(Restrictions.le("content.date", endDate));
                }
            }
            if (startId != null && endId != null) {
                criteria.setFirstResult(startId);
                criteria.setMaxResults(endId - startId);
            }
            criteria.addOrder(Order.asc("documentId"));
            documents = (List<DocumentBase>) criteria.list();
        } catch (HibernateException error) {
            log.error("Ошибка получения отфильтрованного списка документов", error);
        }

        return documents;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение документа по его ид
     *
     * @param docId ид документа
     * @return документ
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public DocumentBase getDocumentById(Integer docId) {
        DocumentBase document = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            document = (DocumentBase) session.load(DocumentBase.class, docId);
        } catch (Exception error) {
            log.error("Ошибка получения документа по его ид", error);
        }
        Hibernate.initialize(document.getDocumentAuthor());
        Hibernate.initialize(document.getDocumentState());
        Hibernate.initialize(document.getDocumentType());

        return document;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Удаление документа по ид
     *
     * @param docId ид документа
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public void deleteDocument(Integer docId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            DocumentBase document = (DocumentBase) session.load(DocumentBase.class, docId);
            Hibernate.initialize(document.getDocumentState());
            Hibernate.initialize(document.getDocumentAuthor());
            Hibernate.initialize(document.getDocumentUser());
            Hibernate.initialize(document.getDocumentType());
            session.delete(document);
        } catch (HibernateException error) {
            log.error("Ошибка удаления документа по ид", error);
        }

    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества заявлений
     *
     * @return количество заявлений
     */
    @Override
    @Transactional(readOnly = true)
    public long getStatementDocumentCount() {
        Long count = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            DocumentTypeBase type = (DocumentTypeBase) session.load(DocumentTypeBase.class, 2);
            StateBase state = (StateBase) session.load(StateBase.class, 4);
            Criteria criteria = session.createCriteria(DocumentBase.class).add(Restrictions.eq("documentType", type));
            criteria.add(
                    Restrictions.not(
                            Restrictions.in("documentState", new StateBase[]{state})
                    )
            );
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        } catch (HibernateException error) {
            log.error("Ошибка получения количества заявлений", error);
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества принятых документов
     *
     * @return количество принятых документов
     */
    @Override
    @Transactional(readOnly = true)
    public long getAcceptedDocumentCount() {
        Long count = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            StateBase state = (StateBase) session.load(StateBase.class, 4);
            Criteria criteria = session.createCriteria(DocumentBase.class).add(Restrictions.eq("documentState", state));
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        } catch (HibernateException error) {
            log.error("Ошибка получения количества принятых документов", error);
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества отфильтрованных документов
     *
     * @param startDate начальная дата для фильтрации
     * @param endDate конечная дата для фильтрации
     * @param searchWord строка для фильтрации по фио
     * @param docTypeId тип документа для фильтрации
     * @return количество отфильтрованных документов
     */
    @Override
    @Transactional(readOnly = true)
    public long getFilteredDocumentCount(Date startDate, Date endDate, String searchWord, Integer docTypeId) {
        Long count = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            Criteria criteria = session.createCriteria(DocumentBase.class);
            //фильтрация по имени
            if (searchWord != null) {
                criteria.createAlias("documentAuthor", "docAuth").add(Restrictions.like("docAuth.userFio", "%" + searchWord + "%"));
            }
            //фильтрация по типу
            if (docTypeId != null && docTypeId != 0) {
                //фильтрация по типу
                if (docTypeId == 2) {
                    DocumentTypeBase type = (DocumentTypeBase) session.load(DocumentTypeBase.class, docTypeId);
                    StateBase state = (StateBase) session.load(StateBase.class, 4);
                    criteria.add(Restrictions.eq("documentType", type));
                    ////фильтрация по дате
                    if (startDate != null) {
                        criteria.createAlias("statementContent", "content").add(Restrictions.between("content.date", startDate, endDate));
                    } else {
                        criteria.createAlias("statementContent", "content").add(Restrictions.le("content.date", endDate));
                    }
                    criteria.add(
                            Restrictions.not(                
                                    Restrictions.in("documentState", new StateBase[]{state})
                            )
                    );
                }
                //фильтрация по типу
                if (docTypeId == 1) {
                    StateBase state = (StateBase) session.load(StateBase.class, 4);
                    criteria.add(Restrictions.eq("documentState", state));
                    ////фильтрация по дате
                    if (startDate != null) {
                        criteria.createAlias("statementContent", "content").add(Restrictions.between("content.date", startDate, endDate));
                    } else {
                        criteria.createAlias("statementContent", "content").add(Restrictions.le("content.date", endDate));
                    }
                }
                //фильтрация по типу
            } else {
                ////фильтрация по дате
                if (startDate != null) {
                    criteria.createAlias("statementContent", "content").add(Restrictions.between("content.date", startDate, endDate));
                } else {
                    criteria.createAlias("statementContent", "content").add(Restrictions.le("content.date", endDate));
                }
            }
            criteria.setProjection(Projections.rowCount());
            count = (Long) criteria.uniqueResult();
        } catch (HibernateException error) {
            log.error("Ошибка получения количества отфильтрованных документов", error);
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------
}
