package com.cmxv.modellayer.business;

import java.util.Map;

public abstract class Document {

    public Integer docId;
    public Integer docTypeId;
    public Integer stateId;
    public Integer authorId;
    public Integer userId;
    public Map<String,byte[]> attachments;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    
    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    public Map<String,byte[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String,byte[]> attachments) {
        this.attachments = attachments;
    }

    
}
