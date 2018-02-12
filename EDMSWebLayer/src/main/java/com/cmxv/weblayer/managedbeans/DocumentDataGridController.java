package com.cmxv.weblayer.managedbeans;

import com.sun.faces.component.visit.FullVisitContext;
import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
@ManagedBean
@RequestScoped
public class DocumentDataGridController implements Serializable {

    private DocumentsController controller;
    private MainPageController pageController;

    Boolean checker;

    public Boolean getChecker() {
        return checker;
    }

    public void setChecker(Boolean checker) {
        this.checker = checker;

    }
//--------------------------------------------------------------------------------------------------------------------
    
    /**
     * Добавление документа,отмеченного к групповым действиям в общий список групповых документов
     * 
     * @param docId Идентификтор добавляемого документа
     */
    public void setDocumentId(Integer docId) {
        getControllers();
        //Получение компонентов для изменения их стиля в зависимости от элементов списка групповых документов
        HtmlPanelGroup reviewComponent = (HtmlPanelGroup) findComponent("reviewAllDocButton");
        HtmlPanelGroup acceptComponent = (HtmlPanelGroup) findComponent("acceptAllDocButton");
        HtmlPanelGroup refuseComponent = (HtmlPanelGroup) findComponent("refuseAllDocButton");
        HtmlPanelGroup deleteComponent = (HtmlPanelGroup) findComponent("deleteAllDocButton");
        controller.docId = docId;
        //Добавление документа,отмеченного к групповым действиям в общий список групповых документов
        if (pageController.checkerDocuments != null) {
            pageController.checkerDocuments.put(controller.getDocId(), checker);
        }
        //Изменение видимости документа в зависимоти от того какого статуса документы находятся в списке
        if (!pageController.checkerDocuments.isEmpty()) {
            if (controller.isAvailableForSentAll(pageController.checkerDocuments)) {
                reviewComponent.setStyle("visibility: visible");
                deleteComponent.setStyle("visibility: visible");
            } else {
                reviewComponent.setStyle("visibility: hidden");
                deleteComponent.setStyle("visibility: hidden");
            }
            if (controller.isAvailableForAgreeOrCancelAll(pageController.checkerDocuments)) {
                acceptComponent.setStyle("visibility: visible");
                refuseComponent.setStyle("visibility: visible");
            } else {
                acceptComponent.setStyle("visibility: hidden");
                refuseComponent.setStyle("visibility: hidden");
            }
        }
    }
    
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Обновление видимости кнопок смены статуса документов
     * 
     */
    public void refreshVisability() {
        HtmlPanelGroup reviewComponent = (HtmlPanelGroup) findComponent("reviewAllDocButton");
        HtmlPanelGroup acceptComponent = (HtmlPanelGroup) findComponent("acceptAllDocButton");
        HtmlPanelGroup refuseComponent = (HtmlPanelGroup) findComponent("refuseAllDocButton");
        HtmlPanelGroup deleteComponent = (HtmlPanelGroup) findComponent("deleteAllDocButton");

        reviewComponent.setStyle("visibility: visible");
        deleteComponent.setStyle("visibility: visible");

        acceptComponent.setStyle("visibility: visible");
        refuseComponent.setStyle("visibility: visible");
    }
    
//--------------------------------------------------------------------------------------------------------------------
    
    /**
     * Получение managed beans(контроллеры) из контекста приложения
     * 
     */
    public void getControllers() {
        FacesContext context = FacesContext.getCurrentInstance();
        controller = context.getApplication().evaluateExpressionGet(context, "#{documentsController}", DocumentsController.class);
        pageController = context.getApplication().evaluateExpressionGet(context, "#{mainPageController}", MainPageController.class);
    }

//--------------------------------------------------------------------------------------------------------------------
    
    /**
     * Получение managed beans(контроллеры) из контекста приложения
     * 
     * @param id идентификатор компонента в контексте приложения
     * @return компонент,найденный по своему идентификатору
     */
    public UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        //Получение корневого элемента
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });

        return found[0];

    }
    
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Очищение списка групповых документов при смене страницы при пагинации
     * 
     */
    public void changeCheckState() {
        getControllers();
        for (Map.Entry<Integer, Boolean> checkerDoc : pageController.checkerDocuments.entrySet()) {
            pageController.checkerDocuments.put(checkerDoc.getKey(), false);
        }
        checker = false;
    }
}

//--------------------------------------------------------------------------------------------------------------------