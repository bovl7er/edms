package com.cmxv.modellayer.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentDTO {
    
    String userName;
    int userId;
    String authorName;
    int authorId;
    int stateId;
    int typeId;
    String typeName;
    int docId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "DocumentDTO{" + "userName=" + userName + ", userId=" + userId + ", authorName=" + authorName + ", authorId=" + authorId + ", stateId=" + stateId + ", typeId=" + typeId + ", typeName=" + typeName + ", docId=" + docId + '}';
    }

    

    

    

    
    
}
