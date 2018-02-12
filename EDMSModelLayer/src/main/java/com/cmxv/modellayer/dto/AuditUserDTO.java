package com.cmxv.modellayer.dto;

import java.util.Date;

public class AuditUserDTO {
    
    Date changingDate;
    String changingName;
    String oldFio;
    String newFio;
    String oldLogin;
    String newLogin;
    String oldPass;
    String newPass;
    String oldRemoveState;
    String newRemoveState;

    public AuditUserDTO() {
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

    public String getOldFio() {
        return oldFio;
    }

    public void setOldFio(String oldFio) {
        this.oldFio = oldFio;
    }

    public String getNewFio() {
        return newFio;
    }

    public void setNewFio(String newFio) {
        this.newFio = newFio;
    }

    public String getOldLogin() {
        return oldLogin;
    }

    public void setOldLogin(String oldLogin) {
        this.oldLogin = oldLogin;
    }

    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getOldRemoveState() {
        return oldRemoveState;
    }

    public void setOldRemoveState(String oldRemoveState) {
        this.oldRemoveState = oldRemoveState;
    }

    public String getNewRemoveState() {
        return newRemoveState;
    }

    public void setNewRemoveState(String newRemoveState) {
        this.newRemoveState = newRemoveState;
    }

    @Override
    public String toString() {
        return "AuditUserDTO{" + "changingDate=" + changingDate + ", changingName=" + changingName + ", oldFio=" + oldFio + ", newFio=" + newFio + ", oldLogin=" + oldLogin + ", newLogin=" + newLogin + ", oldPass=" + oldPass + ", newPass=" + newPass + ", oldRemoveState=" + oldRemoveState + ", newRemoveState=" + newRemoveState + '}';
    }
    
    
}
