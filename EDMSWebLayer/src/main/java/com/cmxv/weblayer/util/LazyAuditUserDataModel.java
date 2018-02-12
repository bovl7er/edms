package com.cmxv.weblayer.util;

import com.cmxv.bussinessinterfaceslayer.AuditService;
import com.cmxv.bussinessinterfaceslayer.util.DBNullException;
import com.cmxv.modellayer.dto.AuditUserDTO;
import com.cmxv.modellayer.business.SearchFilter;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LazyAuditUserDataModel extends LazyDataModel<AuditUserDTO> {
    
    private static final Logger log = Logger.getLogger(LazyAuditUserDataModel.class);

    SearchFilter filter = null;

    @Autowired
    AuditService auditService;
//--------------------------------------------------------------------------------------------------------------------

    public void setFilter(SearchFilter filter) {
        this.filter = filter;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение таблицы аудита пользователей постранично
     *
     * @param first с какого номера начинать выгрузку таблицы из БД
     * @param pageSize количество записей,отображающихся на странице
     * @param sortField поле для сортировки
     * @param sortOrder класс для проведения сортировки
     * @param filters поля для фильтрации
     * @return таблицу аудита пользователей с учетом всех параметров
     */
    @Override
    public List<AuditUserDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        List<AuditUserDTO> data;
        int dataSize;
        try {
            if (filter == null) {
                //Получение количества записей таблицы аудита пользователей для получения количества страниц
                dataSize = (int) auditService.getAuditUserCount();
                this.setRowCount(dataSize);

                // Пагинация
                if (dataSize > pageSize) {
                    //Получение номера по которое делать выгрузку таблицы аудита пользователей из БД
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    //Получение таблицы аудита пользователей
                    data = auditService.loadAuditUserTable(first, last);
                } else {
                    data = auditService.loadAuditUserTable(null, null);
                }
                return data;
            } else {
                //Получение количества записей отфильтрованной таблицы аудита пользователей для получения количества страниц
                dataSize = (int) auditService.getFilterAuditUserCount(filter);
                this.setRowCount(dataSize);

                //Получение отфильтрованной таблицы аудита пользователей
                if (dataSize > pageSize) {
                    int last = ((first + pageSize) > dataSize) ? first + (dataSize % pageSize) : first + pageSize;
                    data = auditService.loadFilterAuditUserTable(filter, first, last);
                } else {
                    data = auditService.loadFilterAuditUserTable(filter, null, null);
                }
                return data;
            }
        } catch (DBNullException error) {
            log.error("Ошибка получения таблицы аудита пользователей: " + error.getMessage(), error);
            return null;
        }

    }
//--------------------------------------------------------------------------------------------------------------------

}
