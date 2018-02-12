package com.cmxv.modellayer.DBentities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENT_ATTACHMENT")
public class DocumentAttachmentBase implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_attach_id_seq")
    @SequenceGenerator(name = "doc_attach_id_seq", sequenceName = "DOC_ATTACH_SEQ", allocationSize = 1)
    @Column(name = "DOC_ATTACH_ID")
    private Integer attachmentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_ID")
    private DocumentBase document;
    
    @Column(name = "DOC_ATTACH_FILE")
    private byte[] file;
    
    @Column(name = "DOC_ATTACH_FILE_NAME")
    private String fileName;

    public DocumentAttachmentBase() {
    }

    public DocumentAttachmentBase(Integer attachmentId, DocumentBase document, byte[] file, String fileName) {
        this.attachmentId = attachmentId;
        this.document = document;
        this.file = file;
        this.fileName = fileName;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public DocumentBase getDocument() {
        return document;
    }

    public void setDocument(DocumentBase document) {
        this.document = document;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "DocumentAttachmentBase{" + "attachmentId=" + attachmentId + ", document=" + document + ", file=" + file + ", fileName=" + fileName + '}';
    }
    
    
}
