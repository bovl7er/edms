package com.cmxv.modellayer.dto;

import java.util.Date;

public class AuditDocDTO {
   
    Date changingDate;
    String changingName;
    Integer documentId;
    String documentType;
    String authorName;
    String oldUserName;
    String newUserName;
    String oldStateName;
    String newStateName;

    public AuditDocDTO() {
    }
    
    

    public Date getChangingDate() {
        return changingDate;
    }

    public void setChangingDate(Date changingDate) {
        this.changingDate = changingDate;
    }

    public String getChangingName() {
        return changingName;
    }

    public void setChangingName(String changingName) {
        this.changingName = changingName;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getOldUserName() {
        return oldUserName;
    }

    public void setOldUserName(String oldUserName) {
        this.oldUserName = oldUserName;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getOldStateName() {
        return oldStateName;
    }

    public void setOldStateName(String oldStateName) {
        this.oldStateName = oldStateName;
    }

    public String getNewStateName() {
        return newStateName;
    }

    public void setNewStateName(String newStateName) {
        this.newStateName = newStateName;
    }

    @Override
    public String toString() {
        return "AuditDocDTO{" + "changingDate=" + changingDate + ", changingName=" + changingName + ", documentId=" + documentId + ", documentType=" + documentType + ", authorName=" + authorName + ", oldUserName=" + oldUserName + ", newUserName=" + newUserName + ", oldStateName=" + oldStateName + ", newStateName=" + newStateName + '}';
    }
    
    
}
