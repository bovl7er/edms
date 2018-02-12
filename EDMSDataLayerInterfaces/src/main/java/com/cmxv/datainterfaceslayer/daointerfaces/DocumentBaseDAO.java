package com.cmxv.datainterfaceslayer.daointerfaces;

import com.cmxv.modellayer.DBentities.DocumentAttachmentBase;
import com.cmxv.modellayer.DBentities.DocumentBase;
import com.cmxv.modellayer.DBentities.StatementDocumentBase;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DocumentBaseDAO {

    public int addDocument(Integer docType,Integer docState,Integer docAuthor,Integer docUser);
    
    public void addStatementDocumentContent(Integer docId,String subject, String author, Date date, String task);
    
    public List<DocumentBase> findDocumentsByType(Integer docType,Integer startId,Integer endId);
    
    public List<DocumentBase> findAcceptDocuments(Integer startId,Integer endId);
    
    public StatementDocumentBase getDocumentContent(Integer docId);
    
    public void addDocumentAttachment(Integer docId, Map<String,byte[]> attachments);
    
    public List<DocumentAttachmentBase> getDocumentAttachments(Integer docId);
    
    public void updateDocumentState(Integer docId,Integer newStateId);
    
    public void updateStatementDocument(Integer statementId,String subject, String author, String task);

    public int getStatementByDocId(Integer docId);
    
    public long getDocumentCount();
    
    public long getStatementDocumentCount();
    
    public long getAcceptedDocumentCount();
    
    public long getFilteredDocumentCount(Date startDate, Date endDate, String searchWord, Integer docTypeId);
    
    public List<DocumentBase> getAllDocuments(Integer startId,Integer endId);
    
    public List<DocumentBase> getFilterDocuments(Date startDate,Date endDate,String searchWord,Integer docTypeId,Integer startId,Integer endId);
    
    public DocumentBase getDocumentById(Integer docId);
    
    public void deleteDocument(Integer docId);
    
    
}
