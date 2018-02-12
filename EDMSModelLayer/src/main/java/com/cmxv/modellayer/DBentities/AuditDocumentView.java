package com.cmxv.modellayer.DBentities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "AUDIT_DOCUMENT_VIEW")
public class AuditDocumentView implements java.io.Serializable {

    @Id
    @Column(name = "AUDIT_DOC_ID")
    private Integer auditDocId;
    
    @Column(name = "SYS_USER")
    private String systemUser;
    
    @Column(name = "CHANGE_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date changeDate;
    
    @Column(name = "WORK_TYPE")
    private String workType;
    
    @Column(name = "OLD_DOC_ID")
    private Integer oldDocId;
    
    @Column(name = "NEW_DOC_ID")
    private Integer newDocId;
    
    @Column(name = "OLD_DOCTYPE_NAME")
    private String oldDocTypeName;
    
    @Column(name = "NEW_DOCTYPE_NAME")
    private String newDocTypeName;
    
    @Column(name = "OLD_USER_FIO")
    private String oldUserFio;
    
    @Column(name = "NEW_USER_FIO")
    private String newUserFio;
    
    @Column(name = "OLD_AUTHOR_FIO")
    private String oldAuthorFio;
    
    @Column(name = "NEW_AUTHOR_FIO")
    private String newAuthorFio;
    
    @Column(name = "OLD_STATE_NAME")
    private String oldStateName;
    
    @Column(name = "NEW_STATE_NAME")
    private String newStateName;

    public AuditDocumentView() {
    }

    public AuditDocumentView(Integer auditDocId, String systemUser, Date changeDate, String workType, Integer oldDocId, Integer newDocId, String oldDocTypeName, String newDocTypeName, String oldUserFio, String newUserFio, String oldAuthorFio, String newAuthorFio, String oldStateName, String newStateName) {
        this.auditDocId = auditDocId;
        this.systemUser = systemUser;
        this.changeDate = changeDate;
        this.workType = workType;
        this.oldDocId = oldDocId;
        this.newDocId = newDocId;
        this.oldDocTypeName = oldDocTypeName;
        this.newDocTypeName = newDocTypeName;
        this.oldUserFio = oldUserFio;
        this.newUserFio = newUserFio;
        this.oldAuthorFio = oldAuthorFio;
        this.newAuthorFio = newAuthorFio;
        this.oldStateName = oldStateName;
        this.newStateName = newStateName;
    }
    
    

    public Integer getAuditDocId() {
        return auditDocId;
    }

    public void setAuditDocId(Integer auditDocId) {
        this.auditDocId = auditDocId;
    }

    public String getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(String systemUser) {
        this.systemUser = systemUser;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Integer getOldDocId() {
        return oldDocId;
    }

    public void setOldDocId(Integer oldDocId) {
        this.oldDocId = oldDocId;
    }

    public Integer getNewDocId() {
        return newDocId;
    }

    public void setNewDocId(Integer newDocId) {
        this.newDocId = newDocId;
    }

    public String getOldDocTypeName() {
        return oldDocTypeName;
    }

    public void setOldDocTypeName(String oldDocTypeName) {
        this.oldDocTypeName = oldDocTypeName;
    }

    public String getNewDocTypeName() {
        return newDocTypeName;
    }

    public void setNewDocTypeName(String newDocTypeName) {
        this.newDocTypeName = newDocTypeName;
    }

    public String getOldUserFio() {
        return oldUserFio;
    }

    public void setOldUserFio(String oldUserFio) {
        this.oldUserFio = oldUserFio;
    }

    public String getNewUserFio() {
        return newUserFio;
    }

    public void setNewUserFio(String newUserFio) {
        this.newUserFio = newUserFio;
    }

    public String getOldAuthorFio() {
        return oldAuthorFio;
    }

    public void setOldAuthorFio(String oldAuthorFio) {
        this.oldAuthorFio = oldAuthorFio;
    }

    public String getNewAuthorFio() {
        return newAuthorFio;
    }

    public void setNewAuthorFio(String newAuthorFio) {
        this.newAuthorFio = newAuthorFio;
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
        return "AuditDocumentView{" + "auditDocId=" + auditDocId + ", systemUser=" + systemUser + ", changeDate=" + changeDate + ", workType=" + workType + ", oldDocId=" + oldDocId + ", newDocId=" + newDocId + ", oldDocTypeName=" + oldDocTypeName + ", newDocTypeName=" + newDocTypeName + ", oldUserFio=" + oldUserFio + ", newUserFio=" + newUserFio + ", oldAuthorFio=" + oldAuthorFio + ", newAuthorFio=" + newAuthorFio + ", oldStateName=" + oldStateName + ", newStateName=" + newStateName + '}';
    }
    
    
}
