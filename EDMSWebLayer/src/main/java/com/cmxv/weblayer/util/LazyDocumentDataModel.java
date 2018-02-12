package com.cmxv.weblayer.util;

import com.cmxv.bussinessinterfaceslayer.DocumentService;
import com.cmxv.modellayer.business.SearchFilter;
import com.cmxv.modellayer.dto.DocumentDTO;
import com.cmxv.weblayer.managedbeans.DocumentsController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LazyDocumentDataModel extends LazyDataModel<DocumentDTO> {

    Integer docType = null;
    SearchFilter filter = null;
    Boolean filtering = false;

    FacesContext context;

    @Autowired
    DocumentService documentService;

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public void setFilter(SearchFilter filter) {
        this.filter = filter;
    }

    public void setFiltering(Boolean filtering) {
        this.filtering = filtering;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение ид документа по объекту типа DocumentDTO
     *
     * @param document документ для поиска его ид
     * @return ид
     */
    @Override
    public Object getRowKey(DocumentDTO document) {
        return document.getDocId();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение списка документов постранично
     *
     * @param first с какого номера начинать выгрузку списка документов из БД
     * @param pageSize количество документов,отображающихся на странице
     * @param sortField поле для сортировки
     * @param sortOrder класс для проведения сортировки
     * @param filters поля для фильтрации
     * @return список докуентов с учетом всех параметров
     */
    @Override
    public List<DocumentDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<DocumentDTO> data = new ArrayList<>();
        int dataSize;

        if (!filtering) {
            if (docType == DocumentsController.DocumentTypes.ALL_DOCUMENTS.ordinal()) {
                //Получение количества документов данного типа для получения количества страниц
                dataSize = (int) documentService.getDocumentsCount();
                this.setRowCount(dataSize);

                // Пагинация
                if (dataSize > pageSize) {
                    //Получение номера по которое делать выгрузку списка документов из БД
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    //Получение списка всех документов
                    data = documentService.getAllDocuments(first, last);
                } else {
                    data = documentService.getAllDocuments(null, null);
                }
                return data;
            } else if (docType == DocumentsController.DocumentTypes.STATEMENT.ordinal()) {
                //Получение количества документов данного типа для получения количества страниц
                dataSize = (int) documentService.getStatementDocumentsCount();
                this.setRowCount(dataSize);

                // Пагинация
                if (dataSize > pageSize) {
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    //Получение списка документов определенного типа
                    data = documentService.getSpecialTypeDocuments(docType, first, last);
                } else {
                    data = documentService.getSpecialTypeDocuments(docType, null, null);
                }
                return data;
            } else if (docType == DocumentsController.DocumentTypes.ARCHIVE.ordinal()) {
                //Получение количества документов данного типа для получения количества страниц
                dataSize = (int) documentService.getAcceptedDocumentsCount();
                this.setRowCount(dataSize);

                // Пагинация
                if (dataSize > pageSize) {
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    //Получение списка документов со статусом ПРИНЯТ
                    data = documentService.getAcceptedDocuments(first, last);
                } else {
                    data = documentService.getAcceptedDocuments(null, null);
                }
                return data;
            }
        } else {
            if (filter != null) {
                //Получение количества документов данного типа для получения количества страниц
                dataSize = (int) documentService.getDocumentsWithFilterCount(filter);
                this.setRowCount(dataSize);

                // Пагинация
                if (dataSize > pageSize) {
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    //Получение отфильтрованного списка документов
                    data = documentService.getDocumentsWithFilter(filter, first, last);
                } else {
                    data = documentService.getDocumentsWithFilter(filter, null, null);
                }
                return data;
            }
        }
        return data;
    }
//--------------------------------------------------------------------------------------------------------------------
}
