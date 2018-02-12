package com.cmxv.modellayer.DBentities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "STATEMENT_DOCUMENT")
public class StatementDocumentBase implements java.io.Serializable  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_doc_id_seq")
    @SequenceGenerator(name = "state_doc_id_seq", sequenceName = "STATE_DOC_SEQ", allocationSize = 1)
    @Column(name = "STAT_DOC_ID")
    private Integer statementDocId;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DOC_ID")
    private DocumentBase document;
    
    @Column(name = "STAT_DOC_SUBJECT")
    private String subject;
    
    @Column(name = "STAT_DOC_AUTHOR")
    private String author;
    
    @Column(name = "STAT_DOC_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @Column(name = "STAT_DOC_TASK")
    private String task;

    public StatementDocumentBase() {
    }

    public StatementDocumentBase(Integer statementDocId, DocumentBase document, String subject, String author, Date date, String task) {
        this.statementDocId = statementDocId;
        this.document = document;
        this.subject = subject;
        this.author = author;
        this.date = date;
        this.task = task;
    }

    public Integer getStatementDocId() {
        return statementDocId;
    }

    public void setStatementDocId(Integer statementDocId) {
        this.statementDocId = statementDocId;
    }

    public DocumentBase getDocument() {
        return document;
    }

    public void setDocument(DocumentBase document) {
        this.document = document;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "StatementDocumentBase{" + "statementDocId=" + statementDocId + ", document=" + document + ", subject=" + subject + ", author=" + author + ", date=" + date + ", task=" + task + '}';
    }

    
    
    
}
