package com.cmxv.tree.processors.impls;

import com.cmxv.tree.processors.interfaces.ProcessableNode;
import com.cmxv.weblayer.managedbeans.AuditController;
import com.cmxv.weblayer.managedbeans.MainPageController;
import com.cmxv.weblayer.managedbeans.SearchController;
import com.cmxv.weblayer.util.LazyAuditDocDataModel;
import com.cmxv.weblayer.util.LazyAuditUserDataModel;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class AuditNode extends DefaultTreeNode implements ProcessableNode {

    private static final long serialVersionUID = 7690435102407692073L;

    private SearchController searchController;

    private MainPageController mainPageComtroller;

    private AuditController auditController;

    private LazyAuditDocDataModel model;

    private LazyAuditUserDataModel userModel;

    private String title;
    private String templateName;
    private String auditAction;
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public String getTitle() {
        return this.title;
    }
//--------------------------------------------------------------------------------------------------------------------

    public String getAuditAction() {
        return auditAction;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setAuditAction(String auditAction) {
        this.auditAction = auditAction;
    }

//--------------------------------------------------------------------------------------------------------------------
    public AuditNode(String title, String templateName, String audithAction, TreeNode parent) {
        this(title, templateName, audithAction);
        this.setParent(parent);
        parent.getChildren().add(this);

    }

//--------------------------------------------------------------------------------------------------------------------
    public AuditNode(String title, String templateName, String audithAction) {
        super(title);

        FacesContext context = FacesContext.getCurrentInstance();
        searchController = context.getApplication().evaluateExpressionGet(context, "#{searchController}", SearchController.class);
        mainPageComtroller = context.getApplication().evaluateExpressionGet(context, "#{mainPageController}", MainPageController.class);
        auditController = context.getApplication().evaluateExpressionGet(context, "#{auditController}", AuditController.class);

        this.templateName = templateName;
        this.title = title;
        this.auditAction = audithAction;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Процесс,выполняющийся при выборе узла дерева
     */
    @Override
    public void process() {
        //Получение бинов из контекста приложения
        getModels();
        //Отчистить фильтр
        searchController.cleanFilter();
        //Получение таблицы аудита,в зависимости от выбранного подузла Аудита
        switch (getAuditAction()) {
             //Получение таблицы аудита документов при выборе АУДИТ ДОКУМЕНТОВ
            case "Документы":
                model.setFilter(null);
                auditController.openDocumentAudit();
                break;
            //Получение таблицы аудита пользователей при выборе АУДИТ ПОЛЬЗОВАТЕЛЕЙ
            case "Пользователи":
                userModel.setFilter(null);
                auditController.openUserAudit();
                break;
        }
        //Загрузка шаблона страницы
        mainPageComtroller.setRightTemplate(templateName);
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение бинов lazyAuditDocDataModel и lazyAuditUserDataModel из контекста приложения
     */
    public void getModels() {
        FacesContext context = FacesContext.getCurrentInstance();
        model = context.getApplication().evaluateExpressionGet(context, "#{lazyAuditDocDataModel}", LazyAuditDocDataModel.class);
        userModel = context.getApplication().evaluateExpressionGet(context, "#{lazyAuditUserDataModel}", LazyAuditUserDataModel.class);
    }
//--------------------------------------------------------------------------------------------------------------------

}
