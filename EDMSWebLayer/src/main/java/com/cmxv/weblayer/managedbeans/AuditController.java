package com.cmxv.weblayer.managedbeans;

import com.cmxv.bussinessinterfaceslayer.AuditService;
import com.cmxv.weblayer.util.LazyAuditDocDataModel;
import com.cmxv.weblayer.util.LazyAuditUserDataModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
@ManagedBean
@RequestScoped
public class AuditController {

    @Autowired
    LazyAuditDocDataModel laddm;

    @Autowired
    LazyAuditUserDataModel laudm;

    @Autowired
    AuditService auditService;

    private MainPageController mainPageController;
    FacesContext context;

    String auditAction;

//--------------------------------------------------------------------------------------------------------------------
    
    public String getAuditAction() {
        return auditAction;
    }
    
//--------------------------------------------------------------------------------------------------------------------
    
    public void setAuditAction(String auditAction) {
        this.auditAction = auditAction;
    }
    
//--------------------------------------------------------------------------------------------------------------------
    
    /**
     * Открытие таблицы аудита документов
     */
    public void openDocumentAudit() {
        getMainPageController();
        mainPageController.setLazyAuditDocModel(laddm);
    }

//--------------------------------------------------------------------------------------------------------------------
    
    /**
     * Открытие таблицы аудита пользователей
     */
    public void openUserAudit() {
        getMainPageController();
        mainPageController.setLazyAuditUserModel(laudm);

    }
    
//--------------------------------------------------------------------------------------------------------------------
    
    /**
     * Получение бина mainPageController из контекста приложения
     */
    public void getMainPageController() {
        context = FacesContext.getCurrentInstance();
        mainPageController = context.getApplication().evaluateExpressionGet(context, "#{mainPageController}", MainPageController.class);
    }

//--------------------------------------------------------------------------------------------------------------------
}
