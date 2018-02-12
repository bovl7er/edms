package com.cmxv.weblayer.managedbeans;

import com.cmxv.modellayer.dto.AuditDocDTO;
import com.cmxv.modellayer.dto.AuditUserDTO;
import com.cmxv.modellayer.dto.DocumentDTO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import org.primefaces.model.LazyDataModel;

@Component
@ManagedBean
@ViewScoped
public class MainPageController {

    private boolean showDocType;

    Map<Integer, Boolean> checkerDocuments = new HashMap<>();
    private LazyDataModel<DocumentDTO> lazyModel;
    private LazyDataModel<AuditDocDTO> lazyAuditDocModel;
    private LazyDataModel<AuditUserDTO> lazyAuditUserModel;

    public String getRightTemplate() {
        return rightTemplate;
    }

    public void setRightTemplate(String rightTemplate) {
        this.rightTemplate = rightTemplate;
    }

    private String rightTemplate = "";

    public LazyDataModel<DocumentDTO> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<DocumentDTO> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public LazyDataModel<AuditDocDTO> getLazyAuditDocModel() {
        return lazyAuditDocModel;
    }

    public void setLazyAuditDocModel(LazyDataModel<AuditDocDTO> lazyAuditDocModel) {
        this.lazyAuditDocModel = lazyAuditDocModel;
    }

    public LazyDataModel<AuditUserDTO> getLazyAuditUserModel() {
        return lazyAuditUserModel;
    }

    public void setLazyAuditUserModel(LazyDataModel<AuditUserDTO> lazyAuditUserModel) {
        this.lazyAuditUserModel = lazyAuditUserModel;
    }

    public Map<Integer, Boolean> getCheckerDocuments() {
        return checkerDocuments;
    }

    public void setCheckerDocuments(Map<Integer, Boolean> checkerDocuments) {
        this.checkerDocuments = checkerDocuments;
    }

    public boolean isShownType() {
        return showDocType;
    }

    public void setShowDocType(boolean showDocType) {
        this.showDocType = showDocType;
    }

}
