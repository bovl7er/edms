package com.cmxv.tree.processors.impls;

import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import com.cmxv.tree.processors.interfaces.ProcessableNode;
import com.cmxv.weblayer.managedbeans.DocumentsController;
import com.cmxv.weblayer.managedbeans.MainPageController;
import com.cmxv.weblayer.managedbeans.SearchController;

public class DocumentsNode extends DefaultTreeNode implements ProcessableNode {

    private static final long serialVersionUID = -2401266591500737934L;

    private DocumentsController controller;

    private SearchController searchController;

    private MainPageController mainPageComtroller;

    private String templateName;
    private String docsType;
    private String title;

//--------------------------------------------------------------------------------------------------------------------
    public DocumentsNode(String title, String templateName, String docsType) {
        super(title);
        FacesContext context = FacesContext.getCurrentInstance();
        controller = context.getApplication().evaluateExpressionGet(context, "#{documentsController}", DocumentsController.class);
        searchController = context.getApplication().evaluateExpressionGet(context, "#{searchController}", SearchController.class);
        mainPageComtroller = context.getApplication().evaluateExpressionGet(context, "#{mainPageController}", MainPageController.class);
        this.templateName = templateName;
        this.docsType = docsType;
        this.title = title;
    }
//--------------------------------------------------------------------------------------------------------------------

    public DocumentsNode(String title, String templateName, String docsType, TreeNode parent) {
        this(title, templateName, docsType);
        this.setParent(parent);
        parent.getChildren().add(this);

    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Процесс,выполняющийся при выборе узла дерева
     */
    @Override
    public void process() {
        //Отчистить фильтр
        searchController.cleanFilter();
        //Отчистить контент
        controller.clearContent();
        //Получение списка документов,в зависимости от выбранного узла дерева
        controller.viewDocumentsByType(docsType);
        //Настройка параметра отображения поля фильтра ТИП
        if (this.getParent().getClass().equals(DocumentsNode.class)) {
            mainPageComtroller.setShowDocType(false);
        } else {
            mainPageComtroller.setShowDocType(true);
        }
        //Загрузка шаблона страницы 
        mainPageComtroller.setRightTemplate(templateName);

    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public String getTitle() {
        return this.title;
    }
//--------------------------------------------------------------------------------------------------------------------
}
