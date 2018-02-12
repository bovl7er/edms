package com.cmxv.weblayer.util;

import com.cmxv.bussinessinterfaceslayer.AuditService;
import com.cmxv.bussinessinterfaceslayer.util.DBNullException;
import com.cmxv.modellayer.dto.AuditDocDTO;
import com.cmxv.modellayer.business.SearchFilter;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LazyAuditDocDataModel extends LazyDataModel<AuditDocDTO> {

    private static final Logger log = Logger.getLogger(LazyAuditDocDataModel.class);

    SearchFilter filter = null;

    @Autowired
    AuditService auditService;
//--------------------------------------------------------------------------------------------------------------------

    public void setFilter(SearchFilter filter) {
        this.filter = filter;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение ид записи из таблицы аудита документов по объекту типа
     * AuditDocDTO
     *
     * @param auditDoc запись из таблицы аудита документов для поиска ее ид
     * @return ид
     */
    @Override
    public Object getRowKey(AuditDocDTO auditDoc) {
        return auditDoc.getDocumentId();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение таблицы аудита документов постранично
     *
     * @param first с какого номера начинать выгрузку таблицы из БД
     * @param pageSize количество записей,отображающихся на странице
     * @param sortField поле для сортировки
     * @param sortOrder класс для проведения сортировки
     * @param filters поля для фильтрации
     * @return таблицу аудита документов с учетом всех параметров
     */
    @Override
    public List<AuditDocDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        List<AuditDocDTO> data;
        int dataSize;
        try {
            if (filter == null) {
                //Получение количества записей таблицы аудита документов для получения количества страниц
                dataSize = (int) auditService.getAuditDocCount();
                this.setRowCount(dataSize);

                // Пагинация
                if (dataSize > pageSize) {
                    //Получение номера по которое делать выгрузку таблицы документов из БД
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    //Получение таблицы аудита документов
                    data = auditService.loadAuditDocTable(first, last);
                } else {
                    data = auditService.loadAuditDocTable(null, null);
                }
                return data;
            } else {
                //Получение количества записей отфильтрованной таблицы аудита документов для получения количества страниц
                dataSize = (int) auditService.getFilterAuditDocCount(filter);
                this.setRowCount(dataSize);

                //Получение отфильтрованной таблицы аудита документов
                if (dataSize > pageSize) {
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    data = auditService.loadFilterAuditDocTable(filter, first, last);
                } else {
                    data = auditService.loadFilterAuditDocTable(filter, null, null);
                }
                return data;

            }
        } catch (DBNullException error) {
            log.error("Ошибка получения таблицы аудита документов: " + error.getMessage(), error);
            return null;
        }

    }
//--------------------------------------------------------------------------------------------------------------------
}
