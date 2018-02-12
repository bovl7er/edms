package com.cmxv.weblayer.managedbeans;

import com.cmxv.bussinessinterfaceslayer.DocumentService;
import com.cmxv.modellayer.business.StatementDocument;
import com.cmxv.modellayer.dto.DocumentDTO;
import com.cmxv.tree.processors.impls.DocumentsNode;
import com.cmxv.weblayer.sessionUtil.SessionBean;
import com.cmxv.weblayer.util.LazyDocumentDataModel;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
@ManagedBean
@RequestScoped
public class DocumentsController {

    private static final Logger log = Logger.getLogger(DocumentsController.class);
    
    private final String BASE_URI = "http://localhost:8081/WebLayer";

    List<Part> files = new ArrayList<>();
    Part file;

    String subject;
    String author;
    String task;
    String date;

    Integer docId;
    Integer docTypeId;
    String state;

    @Autowired
    DocumentService documentService;

    @Autowired
    EditDocumentActionController editDocumentActionController;

    @Autowired
    LazyDocumentDataModel lddm;
    
    @Autowired
    AuthController authController;

    private FileDownloadView downloadView;
    private MainPageController mainPageController;
    private DocumentDataGridController dataGridController;
    FacesContext context;
    String nodeTitle;
    ServletContext servletContext;

    enum States {

        STATE1, STATE2, CREATED, REVIEWED, AGREED, CANCELED;
    }

    public static enum DocumentTypes {

        ALL_DOCUMENTS, ARCHIVE, STATEMENT;
    }
    
    

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
        if (file != null) {
            files.add(this.file);
        }
    }

    public Integer getDocId() {
        return docId;
    }

    public String getDate() {
        return date;
    }


    public String getState() {
        return state;
    }

    public Integer getDocTypeId() {
        return docTypeId;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение название статуса документа через его идентификатор
     *
     * @param stateId идентификатор статуса
     * @return название статуса
     */
    public boolean checkState(int stateId) {
        if (stateId == States.CREATED.ordinal()) {
            state = "Создан";
        }
        if (stateId == States.REVIEWED.ordinal()) {
            state = "Отправлен на рецензирование";
        }
        if (stateId == States.AGREED.ordinal()) {
            state = "Принят";
        }
        if (stateId == States.CANCELED.ordinal()) {
            state = "Отправлен на доработку";
        }
        return true;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Создание документа и добавление его в БД
     */
    public void createDocument() {
        getFileDownloadView();
        Integer userId = SessionBean.getUserId();
        //Добавление вложения и загрузка его в БД
        upload();
        //Создание документа,типа заявление
        StatementDocument statementDocument = new StatementDocument(States.CREATED.ordinal(), userId, userId, subject, author, task, new Date(), downloadView.getAttachments());
        documentService.createNewDocument(statementDocument);
        //Обновление списка документов
        refreshDocList();
        //Очистка контента
        clearContent();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Открытие документа в окне диалога для просмотра
     *
     * @param docId идентификатор документа,предзначенного для просмотра
     */
    public void openDocument(int docId) {
        getFileDownloadView();
        //Предварительная очистка контента
        clearContent();
        //Вызов контекста сервлета,для получения и записи аттрибутов
        getServletContext();
        
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(BASE_URI);
        //Получение документа из БД
        DocumentDTO ddto = service.path("rest").path("documentService").path(String.valueOf(docId)).accept(MediaType.APPLICATION_XML).get(DocumentDTO.class);
        servletContext.setAttribute("docTypeId", ddto.getTypeId());
        this.docTypeId = ddto.getTypeId();
        servletContext.setAttribute("authorId", ddto.getAuthorId());
        servletContext.setAttribute("docId", docId);
        this.docId = docId;
        servletContext.setAttribute("stateId", ddto.getStateId());
        //Установка параметра editActionName в статус ПРОСМОТР,для  верного отображения окна диалога просмотра документа
        editDocumentActionController.editActionName = "Просмотр";
        //Открытие документа типа ЗАЯВЛЕНИЕ
        if (docTypeId == DocumentTypes.STATEMENT.ordinal()) {
            StatementDocument doc = (StatementDocument) documentService.getDocumentContent(docId, docTypeId);
            setSubject(doc.getSubject());
            setAuthor(doc.getAuthor());
            setTask(doc.getTask());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            date = sdf.format(doc.getDate());
            downloadView.setAttachments(doc.getAttachments());
            for (Entry<String, byte[]> attachment : downloadView.getAttachments().entrySet()) {
                downloadView.attachmentNames.add(attachment.getKey());
                downloadView.attachmentContent.add(attachment.getValue());
            }

        }

    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Установка параметра editActionName в статус РЕДАКТИРОВАНИЕ,для верного
     * отображения окна диалога просмотра документа с возможностью
     * редактирования полей и добавление новых вложений
     */
    public void editDocument() {
        //Установка параметра editActionName в статус РЕДАКТИРОВАНИЕ
        editDocumentActionController.handleActionChange();
        //Очистка контента
        clearContent();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Загрузка определенного списка документов,в зависимости от типа,заданного
     * при нажатии на соответствующий узел дерева
     *
     * @param title название узла
     */
    public void viewDocumentsByType(String title) {
        //Вызов контекста сервлета,для получения и записи аттрибутов
        getServletContext();
        //Получение бина mainPageController из контекста приложения
        getMainPageController();
        //Загрузка докумнтов всех типов при нажатии на узел "Документы"
        if (title.equals("Документы")) {
            servletContext.setAttribute("documentTypeId", DocumentTypes.STATEMENT.ordinal());
            lddm.setDocType(DocumentTypes.ALL_DOCUMENTS.ordinal());
            lddm.setFiltering(false);
            //Ленивая загрузка документов при пагинации
            mainPageController.setLazyModel(lddm);
        }
        //Загрузка докумнтов типа ЗАЯВЛЕНИЕ при нажатии на узел "Заявления"
        if (title.equals("Заявления")) {
            servletContext.setAttribute("documentTypeId", DocumentTypes.STATEMENT.ordinal());
            lddm.setDocType(DocumentTypes.STATEMENT.ordinal());
            lddm.setFiltering(false);
            mainPageController.setLazyModel(lddm);
        }
        //Загрузка архива документов при нажатии на узел "Архив"
        if (title.equals("Архив")) {
            servletContext.setAttribute("documentTypeId", DocumentTypes.ARCHIVE.ordinal());
            lddm.setDocType(DocumentTypes.ARCHIVE.ordinal());
            lddm.setFiltering(false);
            mainPageController.setLazyModel(lddm);
        }

    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Добавление вложения к документу и запись в список вложений
     */
    public void upload() {
        getFileDownloadView();
        byte[] bytes;
        String name;
        //Добавление вложения в список аттачментов
        try {
            for (Part fileItem : files) {
                bytes = IOUtils.toByteArray(fileItem.getInputStream());
                name = fileItem.getSubmittedFileName();
                downloadView.getAttachments().put(name, bytes);
            }
        } catch (IOException error) {
            log.error("Ошибка при добавлении вложения к документу", error);
        }

    }

    public boolean showForSent() {
    	return  (authController.checkRights("Отправить") || authController.checkRights("Удалить"));
    }
    
    public boolean showForAgreeOrCancel() {
    	return  (authController.checkRights("Принять") || authController.checkRights("На доработку"));
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Проверка на отображение кнопок для удаления и отпраки на рецензирование
     * документа
     *
     * @return true для отображения кнопок или false для скрытия
     */
    public boolean isAvailableForSent() {
        getServletContext();
        return (authController.checkRights("Отправить") || authController.checkRights("Удалить")) && (servletContext.getAttribute("authorId").equals(SessionBean.getUserId()) && !servletContext.getAttribute("stateId").equals(States.AGREED.ordinal()) && !servletContext.getAttribute("stateId").equals(States.REVIEWED.ordinal()));
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Проверка на отображение кнопок для принятия и отправки на доработку
     * документа
     *
     * @return true для отображения кнопок или false для скрытия
     */
    public boolean isAvailableForAgreeOrCancel() {
        getServletContext();
        return  (authController.checkRights("Принять") || authController.checkRights("На доработку")) && (!servletContext.getAttribute("authorId").equals(SessionBean.getUserId()) && servletContext.getAttribute("stateId").equals(States.REVIEWED.ordinal()));
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Проверка на отображение кнопок для принятия и отправки на доработку
     * группы документов
     *
     * @param checkedDocuments список документов назначенных на групповые
     * действия
     * @return true для отображения кнопок или false для скрытия
     */
    public boolean isAvailableForSentAll(Map<Integer, Boolean> checkedDocuments) {
        getServletContext();
    	if(!(authController.checkRights("Отправить") || authController.checkRights("Удалить"))) {
    		return false;
    	}
        //Цикл проверки документов из списка на то можно ли применить к ним кнопки для удаления и отправки на рецензирование
        for (Entry<Integer, Boolean> checkerDocument : checkedDocuments.entrySet()) {
            if (checkerDocument.getValue()) {
                DocumentDTO ddto = documentService.getDocumentById(checkerDocument.getKey());
                servletContext.setAttribute("authorId", ddto.getAuthorId());
                servletContext.setAttribute("stateId", ddto.getStateId());
                //Проверка на отображение кнопок для удаления и отпраки на рецензирование этого документа
                if (!isAvailableForSent()) {
                    return false;
                }
            }
        }

        return true;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Проверка на отображение кнопок для принятия и отправки на доработку
     * группы документов
     *
     * @param checkedDocuments список документов назначенных на групповые
     * действия
     * @return true для отображения кнопок или false для скрытия
     */
    public boolean isAvailableForAgreeOrCancelAll(Map<Integer, Boolean> checkedDocuments) {
        getServletContext();
    	if(!(authController.checkRights("Принять") || authController.checkRights("На доработку"))) {
    		return false;
    	}
        //Цикл проверки документов из списка на то можно ли применить к ним кнопки для принятия и отправки на доработку
        for (Entry<Integer, Boolean> checkerDocument : checkedDocuments.entrySet()) {
            if (checkerDocument.getValue()) {
                DocumentDTO ddto = documentService.getDocumentById(checkerDocument.getKey());
                servletContext.setAttribute("authorId", ddto.getAuthorId());
                servletContext.setAttribute("stateId", ddto.getStateId());
                //Проверка на отображение кнопок для принятия и отправки на доработку этого документа
                if (!isAvailableForAgreeOrCancel()) {
                    return false;
                }
            }
        }

        return true;
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Отчистка контента
     */
    public void clearContent() {
        getFileDownloadView();
        getMainPageController();
        //Очистка списка документов для групповых действий
        for (Entry<Integer, Boolean> checkerDoc : mainPageController.checkerDocuments.entrySet()) {
            mainPageController.checkerDocuments.put(checkerDoc.getKey(), false);
        }
        //Очистка контента для заявления
        subject = null;
        date = null;
        author = null;
        task = null;
        //Очистка списка файлов и аттачменты
        files.clear();
        downloadView.attachments.clear();
        downloadView.attachmentNames.clear();
        downloadView.attachmentContent.clear();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Отправка документа на рецензирование
     */
    public void sentDocToReview() {
        getServletContext();
        //Смена статуса документа на статус REVIEWED
        documentService.changeDocumentState((Integer) servletContext.getAttribute("docId"), States.REVIEWED.ordinal());
        //Обновление списка документов
        refreshDocList();

    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Изменение статуса документа на ПРИНЯТ
     */
    public void acceptDocument() {
        getServletContext();
        //Смена статуса документа на статус AGREED
        documentService.changeDocumentState((Integer) servletContext.getAttribute("docId"), States.AGREED.ordinal());
        //Обновление списка документов
        refreshDocList();

    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Отправка документа на доработку
     */
    public void cancelDocument() {
        getServletContext();
        //Смена статуса документа на статус CANCELED
        documentService.changeDocumentState((Integer) servletContext.getAttribute("docId"), States.CANCELED.ordinal());
        //Обновление списка документов
        refreshDocList();

    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Удаление документа
     */
    public void deleteDocument() {
        getServletContext();
        //Удаление документа из  БД
        documentService.deleteDocumentById((Integer) servletContext.getAttribute("docId"));
        //Обновление списка документов
        refreshDocList();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Редактирование документа и отправка его на рецензирование
     */
    public void updateDocument() {
        getFileDownloadView();
        getServletContext();
        //Добавление вложения и загрузка его в БД
        upload();
        //Создание документа,типа заявление
        StatementDocument statementDocument = new StatementDocument((Integer) servletContext.getAttribute("docId"), (Integer) servletContext.getAttribute("docTypeId"), subject, author, task, new Date(), downloadView.getAttachments());
        documentService.updateDocument(statementDocument);
        //Отправка документа на рецензирование
        sentDocToReview();
        //Обновление списка документов
        refreshDocList();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Отправка группы документов на рецензирование
     */
    public void sentAllDocsToReview() {
        getMainPageController();
        getDocumentDataGridController();
        //Смена статуса группы документов на REVIEWED
        for (Entry<Integer, Boolean> checkerDocument : mainPageController.checkerDocuments.entrySet()) {
            if (checkerDocument.getValue()) {
                documentService.changeDocumentState(checkerDocument.getKey(), States.REVIEWED.ordinal());
            }
        }
        //Обновление списка документов
        refreshDocList();
        //Обновление отображения кнопок групповых действий
        dataGridController.refreshVisability();
        //Очистка контента
        clearContent();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Изменение статуса группы документов на ПРИНЯТО
     */
    public void acceptAllDocument() {
        getMainPageController();
        getDocumentDataGridController();
        //Смена статуса группы документов на AGREED
        for (Entry<Integer, Boolean> checkerDocument : mainPageController.checkerDocuments.entrySet()) {
            if (checkerDocument.getValue()) {
                documentService.changeDocumentState(checkerDocument.getKey(), States.AGREED.ordinal());
            }
        }
        //Обновление отображения кнопок групповых действий
        dataGridController.refreshVisability();
        //Обновление списка документов
        refreshDocList();
        //Очистка контента
        clearContent();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Отправка группы документов на доработку
     */
    public void cancelAllDocument() {
        getMainPageController();
        getDocumentDataGridController();
        //Смена статуса группы документов на CANCELED
        for (Entry<Integer, Boolean> checkerDocument : mainPageController.checkerDocuments.entrySet()) {
            if (checkerDocument.getValue()) {
                documentService.changeDocumentState(checkerDocument.getKey(), States.CANCELED.ordinal());
            }
        }
        //Обновление отображения кнопок групповых действий
        dataGridController.refreshVisability();
        //Обновление списка документов
        refreshDocList();
        //Очистка контента
        clearContent();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Удаление группы документов
     */
    public void deleteAllDocument() {
        getMainPageController();
        getDocumentDataGridController();
        //Удаление группы документов из БД
        for (Entry<Integer, Boolean> checkerDocument : mainPageController.checkerDocuments.entrySet()) {
            if (checkerDocument.getValue()) {
                documentService.deleteDocumentById(checkerDocument.getKey());
            }
        }
        //Обновление отображения кнопок групповых действий
        dataGridController.refreshVisability();
        //Обновление списка документов
        refreshDocList();
        //Очистка контента
        clearContent();
    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Обновление списка документов
     */
    public void refreshDocList() {
        getServletContext();
        context = FacesContext.getCurrentInstance();
        mainPageController = context.getApplication().evaluateExpressionGet(context, "#{mainPageController}", MainPageController.class);
        //Получение списка документов в зависимости от выбранного узла ((DocumentsNode) leftTreeController.getSelectedNode()).getType()
        LeftTreeController leftTreeController = context.getApplication().evaluateExpressionGet(context, "#{leftTreeController}", LeftTreeController.class);
        if (leftTreeController.getSelectedNode() instanceof DocumentsNode) {
            viewDocumentsByType(((DocumentsNode) leftTreeController.getSelectedNode()).getTitle());
        }

    }

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение бина dataGridController из контекста приложения
     */
    public void getDocumentDataGridController() {
        context = FacesContext.getCurrentInstance();
        dataGridController = context.getApplication().evaluateExpressionGet(context, "#{documentDataGridController}", DocumentDataGridController.class);

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
    public void getFileDownloadView() {
        context = FacesContext.getCurrentInstance();
        downloadView = context.getApplication().evaluateExpressionGet(context, "#{fileDownloadView}", FileDownloadView.class);

    }

//--------------------------------------------------------------------------------------------------------------------
}
