package com.cmxv.modellayer.DBentities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENTS")
public class DocumentBase implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_id_seq")
    @SequenceGenerator(name = "document_id_seq", sequenceName = "DOC_SEQ", allocationSize = 1)
    @Column(name = "DOC_ID")
    private Integer documentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "DOCTYPE_ID") 
    private DocumentTypeBase documentType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "STATE_ID") 
    private StateBase documentState;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_AUTHOR_ID")
    private UserExt documentAuthor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserExt documentUser;
    
    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL)
    private StatementDocumentBase statementContent;
    
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentAttachmentBase> documentAttachments;

    public DocumentBase() {
    }

    public DocumentBase(Integer documentId, DocumentTypeBase documentType, StateBase documentState, UserExt documentAuthor, UserExt documentUser) {
        this.documentId = documentId;
        this.documentType = documentType;
        this.documentState = documentState;
        this.documentAuthor = documentAuthor;
        this.documentUser = documentUser;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public DocumentTypeBase getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeBase documentType) {
        this.documentType = documentType;
    }

    public StateBase getDocumentState() {
        return documentState;
    }

    public void setDocumentState(StateBase documentState) {
        this.documentState = documentState;
    }

    public UserExt getDocumentAuthor() {
        return documentAuthor;
    }

    public void setDocumentAuthor(UserExt author) {
        this.documentAuthor = author;
    }

    public UserExt getDocumentUser() {
        return documentUser;
    }

    public void setDocumentUser(UserExt user) {
        this.documentUser = user;
    }

    public StatementDocumentBase getStatementContent() {
        return statementContent;
    }

    public void setStatementContent(StatementDocumentBase statementContent) {
        this.statementContent = statementContent;
    }

    public List<DocumentAttachmentBase> getDocumentAttachments() {
        return documentAttachments;
    }

    public void setDocumentAttachments(List<DocumentAttachmentBase> documentAttachments) {
        this.documentAttachments = documentAttachments;
    }

    @Override
    public String toString() {
        return "DocumentBase{" + "documentId=" + documentId + ", documentType=" + documentType + ", documentState=" + documentState + ", documentAuthor=" + documentAuthor + ", documentUser=" + documentUser + ", statementContent=" + statementContent + ", documentAttachments=" + documentAttachments + '}';
    }

    
    
    
    
    
}
