package com.cmxv.modellayer.business;

import java.util.Date;
import java.util.Map;

public class StatementDocument extends Document{

    public String subject;
    public String author;
    public String task;
    public Date date;

    public StatementDocument() {
    }

    public StatementDocument(String subject, String author, String task, Date date,Map<String,byte[]> attachments) {
        this.subject = subject;
        this.author = author;
        this.task = task;
        this.date = date;
        this.attachments = attachments;
    }
    
    public StatementDocument(Integer docId,Integer docTypeId,String subject, String author, String task, Date date,Map<String,byte[]> attachments) {
        this.docId = docId;
        this.docTypeId = docTypeId;
        this.subject = subject;
        this.author = author;
        this.task = task;
        this.date = date;
        this.attachments = attachments;
    }
    
    

    public StatementDocument(Integer stateId, Integer authorId, Integer userId, String subject, String author, String task, Date date,Map<String,byte[]> attachments) {
        this.stateId = stateId;
        this.authorId = authorId;
        this.userId = userId;
        this.subject = subject;
        this.author = author;
        this.task = task;
        this.date = date;
        this.attachments = attachments;
        this.docTypeId = 2;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StatementDocument{" + "subject=" + subject + ", author=" + author + ", task=" + task + ", date=" + date + ", attachments size="+ attachments.size()+'}';
    }



    
    
}
