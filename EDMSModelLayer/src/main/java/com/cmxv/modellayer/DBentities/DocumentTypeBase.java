package com.cmxv.modellayer.DBentities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENT_TYPES")
public class DocumentTypeBase implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_type_id_seq")
    @SequenceGenerator(name = "doc_type_id_seq", sequenceName = "DOCTYPE_SEQ", allocationSize = 1)
    @Column(name = "DOCTYPE_ID")
    private Integer docTypeId;
    
    @Column(name = "DOCTYPE_NAME")
    private String docTypeName;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType", cascade = CascadeType.ALL)
    private List<DocumentBase> documents;

    public DocumentTypeBase() {
    }

    public DocumentTypeBase(Integer docTypeId, String docTypeName) {
        this.docTypeId = docTypeId;
        this.docTypeName = docTypeName;
    }

    public Integer getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }


    public List<DocumentBase> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentBase> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "DocumentTypeBase{" + "docTypeId=" + docTypeId + ", docTypeName=" + docTypeName + ", documents=" + documents + '}';
    }
    
    
}
