package com.cmxv.modellayer.DBentities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "AUDIT_USER_VIEW")
public class AuditUserView implements java.io.Serializable {
    
    @Id
    @Column(name = "AUDIT_USER_ID")
    private Integer auditUserId;
    
    @Column(name = "SYS_USER")
    private String systemUser;
    
    @Column(name = "CHANGE_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date changeDate;
    
    @Column(name = "WORK_TYPE")
    private String workType;
    
    @Column(name = "OLD_USER_FIO")
    private String oldUserFio;
    
    @Column(name = "NEW_USER_FIO")
    private String newUserFio;

    @Column(name = "OLD_USER_LOGIN")
    private String oldUserLogin;
    
    @Column(name = "NEW_USER_LOGIN")
    private String newUserLogin;
    
    @Column(name = "OLD_USER_PASSWORD")
    private String oldUserPassword;
    
    @Column(name = "NEW_USER_PASSWORD")
    private String newUserPassword;
    
    @Column(name = "OLD_USER_REMOVE_STATE")
    private String oldUserRemoveState;
    
    @Column(name = "NEW_USER_REMOVE_STATE")
    private String newUserRemoveState;

    public AuditUserView() {
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
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

    public String getOldUserLogin() {
        return oldUserLogin;
    }

    public void setOldUserLogin(String oldUserLogin) {
        this.oldUserLogin = oldUserLogin;
    }

    public String getNewUserLogin() {
        return newUserLogin;
    }

    public void setNewUserLogin(String newUserLogin) {
        this.newUserLogin = newUserLogin;
    }

    public String getOldUserPassword() {
        return oldUserPassword;
    }

    public void setOldUserPassword(String oldUserPassword) {
        this.oldUserPassword = oldUserPassword;
    }

    public String getNewUserPassword() {
        return newUserPassword;
    }

    public void setNewUserPassword(String newUserPassword) {
        this.newUserPassword = newUserPassword;
    }

    public String getOldUserRemoveState() {
        return oldUserRemoveState;
    }

    public void setOldUserRemoveState(String oldUserRemoveState) {
        this.oldUserRemoveState = oldUserRemoveState;
    }

    public String getNewUserRemoveState() {
        return newUserRemoveState;
    }

    public void setNewUserRemoveState(String newUserRemoveState) {
        this.newUserRemoveState = newUserRemoveState;
    }

    @Override
    public String toString() {
        return "AuditUserView{" + "auditUserId=" + auditUserId + ", systemUser=" + systemUser + ", changeDate=" + changeDate + ", workType=" + workType + ", oldUserFio=" + oldUserFio + ", newUserFio=" + newUserFio + ", oldUserLogin=" + oldUserLogin + ", newUserLogin=" + newUserLogin + ", oldUserPassword=" + oldUserPassword + ", newUserPassword=" + newUserPassword + ", oldUserRemoveState=" + oldUserRemoveState + ", newUserRemoveState=" + newUserRemoveState + '}';
    }
    
    
}
