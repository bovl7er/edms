package com.cmxv.bussinessinterfaceslayer;

import com.cmxv.modellayer.DBentities.DocumentAttachmentBase;
import com.cmxv.modellayer.business.Document;
import com.cmxv.modellayer.business.SearchFilter;
import com.cmxv.modellayer.dto.DocumentDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DocumentService {
 
    public void createNewDocument(Document document);
    
    public int addNewDocument(Integer docType,Integer docState, Integer docAuthor,Integer docUser);
    
    public void addStatementContent(Integer docId,String subject,String author,Date date,String task);
    
    public List<DocumentDTO> getSpecialTypeDocuments(Integer docType,Integer startId,Integer endId);
    
    public List<DocumentDTO> getAcceptedDocuments(Integer startId,Integer endId);
    
    public Document getDocumentContent(Integer docId,Integer docTypeId);
    
    public void addDocumentAttachment(Integer docId,Map<String,byte[]> attachments );
    
    public List<DocumentAttachmentBase> getDocAttachments(Integer docId);
  
    public void changeDocumentState(Integer docId,Integer newStateId);
    
    public void updateDocument(Document document);
    
    public int findStatementByDocId(Integer docId);
    
    public List<DocumentDTO> getDocumentsWithFilter(SearchFilter searchFilter,Integer startId,Integer endId);
    
    public long getDocumentsCount();
    
    public long getStatementDocumentsCount();
    
    public long getAcceptedDocumentsCount();
    
    public long getDocumentsWithFilterCount(SearchFilter searchFilter);
    
    public List<DocumentDTO> getAllDocuments(Integer startId,Integer endId);
    
    public DocumentDTO getDocumentById(Integer docId);
    
    public void deleteDocumentById(Integer docId);

}
