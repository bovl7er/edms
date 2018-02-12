package com.cmxv.weblayer.managedbeans;

import com.cmxv.bussinessinterfaceslayer.AuditService;
import com.cmxv.bussinessinterfaceslayer.DocumentService;
import com.cmxv.modellayer.business.SearchFilter;
import com.cmxv.tree.processors.impls.AuditNode;
import com.cmxv.weblayer.util.LazyAuditDocDataModel;
import com.cmxv.weblayer.util.LazyAuditUserDataModel;
import com.cmxv.weblayer.util.LazyDocumentDataModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
@ManagedBean
@RequestScoped
public class SearchController {

    String search;
    String startDate;
    String endDate;
    Integer docTypeId;

    private static final Logger log = Logger.getLogger(SearchController.class);

    ServletContext servletContext;

    private MainPageController mainPageController;
    FacesContext context;

    @Autowired
    LazyAuditDocDataModel laddm;

    @Autowired
    LazyAuditUserDataModel laudm;

    @Autowired
    DocumentService documentService;

    @Autowired
    AuditService auditService;

    @Autowired
    LazyDocumentDataModel lddm;

    @Autowired
    MainPageController controller;

    @Autowired
    AuditController auditController;

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение списка документов, отфильтрованного по дате,типу,фио
     * пользователя
     *
     */
    public void filter() {
        //Получение контекста сервлета
        getServletContext();
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date start = null;
        Date end = null;
        try {
            //Получение начальной даты для фильтрации
            if (startDate != null) {
                if (!startDate.equals("")) {
                    start = format.parse(startDate);
                }
            }
            //Получение конечной даты для фильтрации
            if (endDate != null) {
                if (!endDate.equals("") && endDate != null) {
                    end = format.parse(endDate);
                }
            }

        } catch (ParseException e) {
            log.error("Ошибка получения даты для фильтрации документов", e);
        }

        if (start != null && end != null) {
            if (end.getTime() < start.getTime()) {
                addMessage("Ошибка фильтрации.Конечная дата не может быть меньше начальной.");
                return;
            }
        }
        //Получение типа документов для фильтрации
        if (docTypeId == null) {
            docTypeId = (Integer) servletContext.getAttribute("documentTypeId");
        }

        SearchFilter searchFilter = new SearchFilter(search, start, end, docTypeId);
        lddm.setFilter(searchFilter);
        lddm.setFiltering(true);
        //Получение отфильтрованного списка документов из БД
        controller.setLazyModel(lddm);
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение таблицы документов или пользователей , отфильтрованной по дате
     *
     */
    public void filterAudit() {
        //Получение бина mainPageController из контекста приложения
        getMainPageController();
        //Получение контекста сервлета
        getServletContext();

        //Получение бина leftTreeController из контекста приложения
        context = FacesContext.getCurrentInstance();
        LeftTreeController leftTreeController = context.getApplication().evaluateExpressionGet(context, "#{leftTreeController}", LeftTreeController.class);

        //Получение названия подузла аудита для выбора фильтрации документов или пользователей
        String action = (leftTreeController.getSelectedNode() instanceof AuditNode) ? ((AuditNode) leftTreeController.getSelectedNode()).getAuditAction() : "";

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date start = null;
        Date end = null;
        try {
            //Получение начальной даты для фильтрации
            if (startDate != null) {
                if (!startDate.equals("")) {
                    start = format.parse(startDate);
                }
            }
            //Получение конечной даты для фильтрации
            if (endDate != null) {
                if (!endDate.equals("") && endDate != null) {
                    end = format.parse(endDate);
                }
            }
        } catch (ParseException e) {
            log.error("Ошибка получения даты для фильтрации таблицы аудита", e);
        }
        
        if (start != null && end != null) {
            if (end.getTime() < start.getTime()) {
                addMessage("Ошибка фильтрации.Конечная дата не может быть меньше начальной.");
                return;
            }
        }
        
        SearchFilter searchFilter = new SearchFilter(start, end);
        switch (action) {
            case "Документы":
                laddm.setFilter(searchFilter);
                //Получение отфильтрованной таблицы аудита документов из БД
                mainPageController.setLazyAuditDocModel(laddm);
                break;
            case "Пользователи":
                laudm.setFilter(searchFilter);
                //Получение отфильтрованной таблицы аудита пользователей из БД
                mainPageController.setLazyAuditUserModel(laudm);
                break;
        }
        //Отчистка фильтра
        cleanFilter();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Отчистка фильтра
     *
     */
    public void cleanFilter() {
        docTypeId = null;
        endDate = null;
        search = null;
        startDate = null;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение контекста сервлета
     */
    public void getServletContext() {
        servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
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
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
