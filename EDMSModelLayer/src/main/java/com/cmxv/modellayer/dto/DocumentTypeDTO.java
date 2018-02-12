package com.cmxv.modellayer.dto;

public class DocumentTypeDTO {

    private int docTypeId;
    private String docTypeName;

    public DocumentTypeDTO(int docTypeId, String docTypeName) {
        this.docTypeId = docTypeId;
        this.docTypeName = docTypeName;
    }

    public DocumentTypeDTO() {
    }
    
    

    public int getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(int docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    @Override
    public String toString() {
        return "DocumentTypeDTO{" + "docTypeId=" + docTypeId + ", docTypeName=" + docTypeName + '}';
    }
    
    
}
