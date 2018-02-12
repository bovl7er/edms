package com.cmxv.weblayer.managedbeans;

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.springframework.stereotype.Controller;

@Controller
@RequestScoped
public class EditDocumentActionController {

    String editActionName;

    public String getEditAction() {
        return editActionName;
    }

    public void setEditAction(String editAction) {
        this.editActionName = editAction;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Изменение параметра editActionName в статус РЕДАКТИРОВАНИЕ или ПРОСМОТР
     * @return 
     */
    public boolean handleActionChange() {
        //Получение аттрибута editAction из контекста приложения
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String action = externalContext.getRequestParameterMap().get("editAction");
        setEditAction(action);
        return true;

    }

//--------------------------------------------------------------------------------------------------------------------
}
