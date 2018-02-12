package com.cmxv.bussinesslayer.services;

import com.cmxv.bussinessinterfaceslayer.DocumentService;
import com.cmxv.datainterfaceslayer.daointerfaces.DocumentBaseDAO;
import com.cmxv.modellayer.DBentities.DocumentAttachmentBase;
import com.cmxv.modellayer.DBentities.DocumentBase;
import com.cmxv.modellayer.DBentities.StatementDocumentBase;
import com.cmxv.modellayer.business.Document;
import com.cmxv.modellayer.business.SearchFilter;
import com.cmxv.modellayer.business.StatementDocument;
import com.cmxv.modellayer.dto.DocumentDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentBaseDAO documentBaseDAO;
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Создание нового документа
     *
     * @param document документ DTO для добавления в БД
     */
    @Override
    public void createNewDocument(Document document) {
        int docType = document.getDocTypeId();
        //Создаем новый документ
        int docId = addNewDocument(document.getDocTypeId(), document.getStateId(), document.getAuthorId(), document.getUserId());
        //Добавляем содержимое документа в зависимости от типа документа
        if (docType == 2) {
            StatementDocument statement = (StatementDocument) document;
            addStatementContent(docId, statement.getSubject(), statement.getAuthor(), statement.getDate(), statement.getTask());
        }
        //Добавляем вложения к документу
        addDocumentAttachment(docId, document.getAttachments());
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Добавление нового документа в БД
     *
     * @param docType ид типа документа
     * @param docState ид статуса документа
     * @param docAuthor ид автора документа
     * @param docUser ид пользователя,работающего с документом
     * @return ид созданного документа
     */
    @Override
    public int addNewDocument(Integer docType, Integer docState, Integer docAuthor, Integer docUser) {

        int docId = documentBaseDAO.addDocument(docType, docState, docAuthor, docUser);
        return docId;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Добавление содержания нового заявления в БД
     *
     * @param docId ид документа
     * @param subject тема документа
     * @param author автор
     * @param date дата создания
     * @param task поставленные задачи заявления
     */
    @Override
    public void addStatementContent(Integer docId, String subject, String author, Date date, String task) {
        documentBaseDAO.addStatementDocumentContent(docId, subject, author, date, task);
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public List<DocumentDTO> getSpecialTypeDocuments(Integer docType, Integer startId, Integer endId) {
        List<DocumentDTO> resultDocs = new ArrayList<>();
        List<DocumentBase> documents = documentBaseDAO.findDocumentsByType(docType, startId, endId);
        //Инициализация параметров
        for (DocumentBase document : documents) {
            DocumentDTO ddto = new DocumentDTO();
            ddto.setDocId(document.getDocumentId());
            Hibernate.initialize(document.getDocumentState());
            ddto.setStateId(document.getDocumentState().getStateId());
            Hibernate.initialize(document.getDocumentUser());
            ddto.setUserName(document.getDocumentUser().getUserFio());
            ddto.setUserId(document.getDocumentUser().getUserId());
            Hibernate.initialize(document.getDocumentAuthor());
            ddto.setAuthorName(document.getDocumentAuthor().getUserFio());
            ddto.setAuthorId(document.getDocumentAuthor().getUserId());
            Hibernate.initialize(document.getDocumentType());
            ddto.setTypeName(document.getDocumentType().getDocTypeName());
            ddto.setTypeId(document.getDocumentType().getDocTypeId());
            resultDocs.add(ddto);
        }
        return resultDocs;
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public List<DocumentDTO> getAcceptedDocuments(Integer startId, Integer endId) {
        List<DocumentDTO> resultDocs = new ArrayList<>();
        List<DocumentBase> documents = documentBaseDAO.findAcceptDocuments(startId, endId);
        //Инициализация параметров
        for (DocumentBase document : documents) {
            DocumentDTO ddto = new DocumentDTO();
            ddto.setDocId(document.getDocumentId());
            Hibernate.initialize(document.getDocumentState());
            ddto.setStateId(document.getDocumentState().getStateId());
            Hibernate.initialize(document.getDocumentUser());
            ddto.setUserName(document.getDocumentUser().getUserFio());
            ddto.setUserId(document.getDocumentUser().getUserId());
            Hibernate.initialize(document.getDocumentAuthor());
            ddto.setAuthorName(document.getDocumentAuthor().getUserFio());
            ddto.setAuthorId(document.getDocumentAuthor().getUserId());
            Hibernate.initialize(document.getDocumentType());
            ddto.setTypeName(document.getDocumentType().getDocTypeName());
            ddto.setTypeId(document.getDocumentType().getDocTypeId());
            resultDocs.add(ddto);
        }
        return resultDocs;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение содержания документа по его ид
     *
     * @param docId ид документа
     * @param docTypeId ид типа докуента
     * @return содержание документа
     */
    @Override
    public Document getDocumentContent(Integer docId, Integer docTypeId) {
        Document docContent = null;
        //Получение вложений к документу
        List<DocumentAttachmentBase> attachmentsBase = getDocAttachments(docId);
        Map<String, byte[]> attachments = new HashMap<>();
        for (DocumentAttachmentBase attachment : attachmentsBase) {
            attachments.put(attachment.getFileName(), attachment.getFile());
        }
        //Получение контента документа по типу документа
        if (docTypeId == 2) {
            StatementDocumentBase document = documentBaseDAO.getDocumentContent(docId);
            StatementDocument doc = new StatementDocument(document.getSubject(), document.getAuthor(), document.getTask(), document.getDate(), attachments);
            docContent = (StatementDocument) doc;
        }
        return docContent;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Добавление вложений к документу
     *
     * @param docId ид документа
     * @param attachments
     */
    @Override
    public void addDocumentAttachment(Integer docId, Map<String, byte[]> attachments) {
        documentBaseDAO.addDocumentAttachment(docId, attachments);
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение списка вложений к документу
     *
     * @param docId ид документа
     * @return список вложений к докуементу
     */
    @Override
    public List<DocumentAttachmentBase> getDocAttachments(Integer docId) {
        List<DocumentAttachmentBase> attachments;
        attachments = documentBaseDAO.getDocumentAttachments(docId);

        return attachments;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Изменение статуса документа
     *
     * @param docId ид документа
     * @param newStateId ид нового статуса
     */
    @Override
    public void changeDocumentState(Integer docId, Integer newStateId) {
        documentBaseDAO.updateDocumentState(docId, newStateId);
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Обновление содержания документа
     *
     * @param document докуемнт DTO для обновления
     */
    @Override
    public void updateDocument(Document document) {
        int docType = document.getDocTypeId();
        //Обновление содержания в зависимости от типа документа
        if (docType == 2) {
            StatementDocument statement = (StatementDocument) document;
            int statementId = findStatementByDocId(statement.getDocId());
            documentBaseDAO.updateStatementDocument(statementId, statement.getSubject(), statement.getAuthor(), statement.getTask());
        }
        //Добавление вложений к документу
        if (!document.getAttachments().isEmpty()) {
            documentBaseDAO.addDocumentAttachment(document.getDocId(), document.getAttachments());
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
    public int findStatementByDocId(Integer docId) {
        return documentBaseDAO.getStatementByDocId(docId);
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение отфильтрованного списка документов
     *
     * @param searchFilter фильтр с параметрами
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return отфильтрованный спискок документов
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public List<DocumentDTO> getDocumentsWithFilter(SearchFilter searchFilter, Integer startId, Integer endId) {
        List<DocumentDTO> resultDocs = new ArrayList<>();
        //Получение параметров для фильтрации
        Date startDate = searchFilter.getStartDate();
        Date endDate;
        String searchWord = searchFilter.getSearchString();
        Integer docTypeId = searchFilter.getDocTypeId();
        if (searchFilter.getEndDate() == null) {
            endDate = new Date();
        } else {
            endDate = searchFilter.getEndDate();
        }
        List<DocumentBase> documents = documentBaseDAO.getFilterDocuments(startDate, endDate, searchWord, docTypeId, startId, endId);
        //Инициализация параметров
        for (DocumentBase document : documents) {
            DocumentDTO ddto = new DocumentDTO();
            ddto.setDocId(document.getDocumentId());
            Hibernate.initialize(document.getDocumentState());
            ddto.setStateId(document.getDocumentState().getStateId());
            Hibernate.initialize(document.getDocumentUser());
            ddto.setUserName(document.getDocumentUser().getUserFio());
            ddto.setUserId(document.getDocumentUser().getUserId());
            Hibernate.initialize(document.getDocumentAuthor());
            ddto.setAuthorName(document.getDocumentAuthor().getUserFio());
            ddto.setAuthorId(document.getDocumentAuthor().getUserId());
            Hibernate.initialize(document.getDocumentType());
            ddto.setTypeName(document.getDocumentType().getDocTypeName());
            ddto.setTypeId(document.getDocumentType().getDocTypeId());
            resultDocs.add(ddto);
        }
        return resultDocs;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества документов
     *
     * @return количество документов
     */
    @Override
    public long getDocumentsCount() {
        return documentBaseDAO.getDocumentCount();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества заявлений
     *
     * @return количество заявлений
     */
    @Override
    public long getStatementDocumentsCount() {
        return documentBaseDAO.getStatementDocumentCount();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества принятых документов
     *
     * @return количество принятых документов
     */
    @Override
    public long getAcceptedDocumentsCount() {
        return documentBaseDAO.getAcceptedDocumentCount();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение количества отфильтрованных документов
     *
     * @param searchFilter фильтр с параметрами
     * @return количество отфильтрованных документов
     */
    @Override
    public long getDocumentsWithFilterCount(SearchFilter searchFilter) {
        //Получение параметров для фильтрации
        Date startDate = searchFilter.getStartDate();
        Date endDate;
        String searchWord = searchFilter.getSearchString();
        Integer docTypeId = searchFilter.getDocTypeId();
        if (searchFilter.getEndDate() == null) {
            endDate = new Date();
        } else {
            endDate = searchFilter.getEndDate();
        }
        return documentBaseDAO.getFilteredDocumentCount(startDate, endDate, searchWord, docTypeId);
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public List<DocumentDTO> getAllDocuments(Integer startId, Integer endId) {
        List<DocumentDTO> resultDocs = new ArrayList<>();
        List<DocumentBase> documents = documentBaseDAO.getAllDocuments(startId, endId);
        //Инициализация параметров
        for (DocumentBase document : documents) {
            DocumentDTO ddto = new DocumentDTO();
            ddto.setDocId(document.getDocumentId());
            Hibernate.initialize(document.getDocumentState());
            ddto.setStateId(document.getDocumentState().getStateId());
            Hibernate.initialize(document.getDocumentUser());
            ddto.setUserName(document.getDocumentUser().getUserFio());
            ddto.setUserId(document.getDocumentUser().getUserId());
            Hibernate.initialize(document.getDocumentAuthor());
            ddto.setAuthorName(document.getDocumentAuthor().getUserFio());
            ddto.setAuthorId(document.getDocumentAuthor().getUserId());
            Hibernate.initialize(document.getDocumentType());
            ddto.setTypeName(document.getDocumentType().getDocTypeName());
            ddto.setTypeId(document.getDocumentType().getDocTypeId());
            resultDocs.add(ddto);
        }
        return resultDocs;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение документа по его ид
     *
     * @param docId ид документа
     * @return документ
     */
    @Override
    public DocumentDTO getDocumentById(Integer docId) {
        DocumentDTO documentDTO = new DocumentDTO();
        DocumentBase document = documentBaseDAO.getDocumentById(docId);
        //Инициализация параметров
        documentDTO.setAuthorId(document.getDocumentAuthor().getUserId());
        documentDTO.setStateId(document.getDocumentState().getStateId());
        documentDTO.setTypeId(document.getDocumentType().getDocTypeId());

        return documentDTO;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Удаление документа по ид
     *
     * @param docId ид документа
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
        ConstraintViolationException.class})
    public void deleteDocumentById(Integer docId) {
        DocumentDTO ddto = getDocumentById(docId);
        if (ddto.getTypeId() == 2) {
            documentBaseDAO.deleteDocument(docId);
        }
    }
//--------------------------------------------------------------------------------------------------------------------
}
