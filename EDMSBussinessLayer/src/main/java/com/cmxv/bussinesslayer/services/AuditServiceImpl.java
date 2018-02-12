package com.cmxv.bussinesslayer.services;

import com.cmxv.bussinessinterfaceslayer.AuditService;
import com.cmxv.bussinessinterfaceslayer.util.DBNullException;
import com.cmxv.datainterfaceslayer.daointerfaces.AuditDAO;
import com.cmxv.modellayer.DBentities.AuditDocumentView;
import com.cmxv.modellayer.DBentities.AuditUserView;
import com.cmxv.modellayer.dto.AuditDocDTO;
import com.cmxv.modellayer.dto.AuditUserDTO;
import com.cmxv.modellayer.business.SearchFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private static final Logger log = Logger.getLogger(AuditServiceImpl.class);

    @Autowired
    private AuditDAO auditDAO;

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Загрузка таблицы аудита документов
     *
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return таблицу аудита документов
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public List<AuditDocDTO> loadAuditDocTable(Integer startId, Integer endId) throws DBNullException {
        List<AuditDocDTO> auditDocDTOs = new ArrayList<>();
        try {
            //Загрузка таблицы аудита документов
            List<AuditDocumentView> auditDocumentViews = auditDAO.getDocumentAuditTable(startId, endId);
            if (auditDocumentViews == null) {
                throw new DBNullException("Список аудита документов пуст");
            }
            //Заполнение AuditDocDTO и передача объекта для отображения
            for (AuditDocumentView view : auditDocumentViews) {
                AuditDocDTO auditDocDTO = new AuditDocDTO();
                auditDocDTO.setChangingDate(view.getChangeDate());
                switch (view.getWorkType()) {
                    case "UPDATE":
                        if (!view.getOldStateName().equals(view.getNewStateName())) {
                            auditDocDTO.setChangingName("Изменен статус");
                        } else if (!view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditDocDTO.setChangingName("Изменен пользователь");
                        } else if (!view.getOldStateName().equals(view.getNewStateName()) && !view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditDocDTO.setChangingName("Изменен пользователь и статус");
                        } else {
                            auditDocDTO.setChangingName("Изменен");
                        }
                        break;
                    case "INSERT":
                        auditDocDTO.setChangingName("Создан");
                        break;
                    case "DELETE":
                        auditDocDTO.setChangingName("Удален");
                        break;
                }
                if (view.getOldDocId() == null) {
                    auditDocDTO.setDocumentId(view.getNewDocId());
                } else {
                    auditDocDTO.setDocumentId(view.getOldDocId());
                }
                if (view.getOldDocTypeName() == null) {
                    auditDocDTO.setDocumentType(view.getNewDocTypeName());
                } else {
                    auditDocDTO.setDocumentType(view.getOldDocTypeName());
                }
                if (view.getOldAuthorFio() == null) {
                    auditDocDTO.setAuthorName(view.getNewAuthorFio());
                } else {
                    auditDocDTO.setAuthorName(view.getOldAuthorFio());
                }
                auditDocDTO.setNewStateName(view.getNewStateName());
                auditDocDTO.setOldStateName(view.getOldStateName());
                auditDocDTO.setOldUserName(view.getOldUserFio());
                auditDocDTO.setNewUserName(view.getNewUserFio());

                auditDocDTOs.add(auditDocDTO);
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }

        return auditDocDTOs;
    }
//--------------------------------------------------------------------------------------------------------------------
     /**
     * Загрузка отфильтрованной таблицы аудита документов
     *
     * @param searchFilter объект с полями для фильтрации
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return отфильтрованную таблицу аудита документов
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public List<AuditDocDTO> loadFilterAuditDocTable(SearchFilter searchFilter, Integer startId, Integer endId) throws DBNullException {
        List<AuditDocDTO> auditDocDTOs = new ArrayList<>();
        Date startDate = searchFilter.getStartDate();
        Date endDate;

        //Получение начальной и конечной даты для фильтрации
        if (searchFilter.getEndDate() == null) {
            endDate = new Date();
        } else {
            endDate = searchFilter.getEndDate();
        }
        try {
            //Загрузка отфильтрованной таблицы аудита документов
            List<AuditDocumentView> auditDocumentViews = auditDAO.getDocumentAuditTableByFilter(startDate, endDate, startId, endId);
            if (auditDocumentViews == null) {
                throw new DBNullException("Отфильтрованный список аудита документов пуст");
            }
            //Заполнение AuditDocDTO и передача объекта для отображения
            for (AuditDocumentView view : auditDocumentViews) {
                AuditDocDTO auditDocDTO = new AuditDocDTO();
                auditDocDTO.setChangingDate(view.getChangeDate());
                switch (view.getWorkType()) {
                    case "UPDATE":
                        if (!view.getOldStateName().equals(view.getNewStateName())) {
                            auditDocDTO.setChangingName("Изменен статус");
                        } else if (!view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditDocDTO.setChangingName("Изменен пользователь");
                        } else if (!view.getOldStateName().equals(view.getNewStateName()) && !view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditDocDTO.setChangingName("Изменен пользователь и статус");
                        } else {
                            auditDocDTO.setChangingName("Изменен");
                        }
                        break;
                    case "INSERT":
                        auditDocDTO.setChangingName("Создан");
                        break;
                    case "DELETE":
                        auditDocDTO.setChangingName("Удален");
                        break;
                }
                if (view.getOldDocId() == null) {
                    auditDocDTO.setDocumentId(view.getNewDocId());
                } else {
                    auditDocDTO.setDocumentId(view.getOldDocId());
                }
                if (view.getOldDocTypeName() == null) {
                    auditDocDTO.setDocumentType(view.getNewDocTypeName());
                } else {
                    auditDocDTO.setDocumentType(view.getOldDocTypeName());
                }
                if (view.getOldAuthorFio() == null) {
                    auditDocDTO.setAuthorName(view.getNewAuthorFio());
                } else {
                    auditDocDTO.setAuthorName(view.getOldAuthorFio());
                }
                auditDocDTO.setNewStateName(view.getNewStateName());
                auditDocDTO.setOldStateName(view.getOldStateName());
                auditDocDTO.setOldUserName(view.getOldUserFio());
                auditDocDTO.setNewUserName(view.getNewUserFio());

                auditDocDTOs.add(auditDocDTO);
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }

        return auditDocDTOs;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Загрузка таблицы аудита пользователей
     *
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return таблицу аудита пользователей
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public List<AuditUserDTO> loadAuditUserTable(Integer startId, Integer endId) throws DBNullException {
        List<AuditUserDTO> auditUserDTOs = new ArrayList<>();
        try {
            //Загрузка таблицы аудита пользователей
            List<AuditUserView> auditDocumentViews = auditDAO.getUserAuditTable(startId, endId);
            if (auditDocumentViews == null) {
                throw new DBNullException("Список аудита пользователей пуст");
            }
            //Заполнение AuditUserDTO и передача объекта для отображения
            for (AuditUserView view : auditDocumentViews) {
                AuditUserDTO auditUserDTO = new AuditUserDTO();
                auditUserDTO.setChangingDate(view.getChangeDate());
                switch (view.getWorkType()) {
                    case "UPDATE":
                        if (!view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditUserDTO.setChangingName("Изменен ФИО");
                        } else if (!view.getOldUserLogin().equals(view.getNewUserLogin())) {
                            auditUserDTO.setChangingName("Изменен логин");
                        } else if (!view.getOldUserPassword().equals(view.getNewUserPassword()) && !view.getNewUserLogin().equals(view.getOldUserLogin())) {
                            auditUserDTO.setChangingName("Изменен логин и пароль");
                        } else if (!view.getOldUserPassword().equals(view.getNewUserPassword())) {
                            auditUserDTO.setChangingName("Изменен пароль");
                        } else if (!view.getOldUserRemoveState().equals(view.getNewUserRemoveState())) {
                            auditUserDTO.setChangingName("Изменен статус");
                        } else if (!view.getOldUserPassword().equals(view.getNewUserPassword()) && !view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditUserDTO.setChangingName("Изменен ФИО и пароль");
                        } else if (!view.getOldUserLogin().equals(view.getNewUserLogin()) && !view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditUserDTO.setChangingName("Изменен ФИО и логин");
                        } else {
                            auditUserDTO.setChangingName("Изменен");
                        }
                        break;
                    case "INSERT":
                        auditUserDTO.setChangingName("Создан");
                        break;
                    case "DELETE":
                        auditUserDTO.setChangingName("Удален");
                        break;
                }

                auditUserDTO.setNewFio(view.getNewUserFio());
                auditUserDTO.setOldFio(view.getOldUserFio());
                auditUserDTO.setOldLogin(view.getOldUserLogin());
                auditUserDTO.setNewLogin(view.getNewUserLogin());
                if (view.getNewUserRemoveState() != null) {
                    if (view.getNewUserRemoveState().equals("N")) {
                        auditUserDTO.setNewRemoveState("Не удален");
                    } else {
                        auditUserDTO.setNewRemoveState("Удален");
                    }
                } else {
                    auditUserDTO.setNewRemoveState(null);
                }
                if (view.getOldUserRemoveState() != null) {
                    if (view.getOldUserRemoveState().equals("N")) {
                        auditUserDTO.setOldRemoveState("Не удален");
                    } else {
                        auditUserDTO.setOldRemoveState("Удален");
                    }
                } else {
                    auditUserDTO.setOldRemoveState(null);
                }
                auditUserDTO.setOldPass(view.getOldUserPassword());
                auditUserDTO.setNewPass(view.getNewUserPassword());

                auditUserDTOs.add(auditUserDTO);
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }
        return auditUserDTOs;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Загрузка отфильтрованной таблицы аудита пользоватлей
     *
     * @param searchFilter объект с полями для фильтрации
     * @param startId начальный номер с которого делать выгрузку из БД
     * @param endId конечный номер с которого делать выгрузку из БД
     * @return отфильтрованную таблицу аудита пользователей
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public List<AuditUserDTO> loadFilterAuditUserTable(SearchFilter searchFilter, Integer startId, Integer endId) throws DBNullException {
        List<AuditUserDTO> auditUserDTOs = new ArrayList<>();
        Date startDate = searchFilter.getStartDate();
        Date endDate;
        //Получение начальной и конечной даты для фильтрации
        if (searchFilter.getEndDate() == null) {
            endDate = new Date();
        } else {
            endDate = searchFilter.getEndDate();
        }
        try {
            //Загрузка отфильтрованной таблицы аудита пользователей
            List<AuditUserView> auditDocumentViews = auditDAO.getUserAuditTableByFilter(startDate, endDate, startId, endId);
            if (auditDocumentViews == null) {
                throw new DBNullException("Отфильтрованный список аудита пользователей пуст");
            }
            //Заполнение AuditUserDTO и передача объекта для отображения
            for (AuditUserView view : auditDocumentViews) {
                AuditUserDTO auditUserDTO = new AuditUserDTO();
                auditUserDTO.setChangingDate(view.getChangeDate());
                switch (view.getWorkType()) {
                    case "UPDATE":
                        if (!view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditUserDTO.setChangingName("Изменен ФИО");
                        } else if (!view.getOldUserLogin().equals(view.getNewUserLogin())) {
                            auditUserDTO.setChangingName("Изменен логин");
                        } else if (!view.getOldUserPassword().equals(view.getNewUserPassword()) && !view.getNewUserLogin().equals(view.getOldUserLogin())) {
                            auditUserDTO.setChangingName("Изменен логин и пароль");
                        } else if (!view.getOldUserPassword().equals(view.getNewUserPassword())) {
                            auditUserDTO.setChangingName("Изменен пароль");
                        } else if (!view.getOldUserRemoveState().equals(view.getNewUserRemoveState())) {
                            auditUserDTO.setChangingName("Изменен статус");
                        } else if (!view.getOldUserPassword().equals(view.getNewUserPassword()) && !view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditUserDTO.setChangingName("Изменен ФИО и пароль");
                        } else if (!view.getOldUserLogin().equals(view.getNewUserLogin()) && !view.getOldUserFio().equals(view.getNewUserFio())) {
                            auditUserDTO.setChangingName("Изменен ФИО и логин");
                        } else {
                            auditUserDTO.setChangingName("Изменен");
                        }
                        break;
                    case "INSERT":
                        auditUserDTO.setChangingName("Создан");
                        break;
                    case "DELETE":
                        auditUserDTO.setChangingName("Удален");
                        break;
                }

                auditUserDTO.setNewFio(view.getNewUserFio());
                auditUserDTO.setOldFio(view.getOldUserFio());
                auditUserDTO.setOldLogin(view.getOldUserLogin());
                auditUserDTO.setNewLogin(view.getNewUserLogin());
                auditUserDTO.setNewRemoveState(view.getNewUserRemoveState());
                auditUserDTO.setOldRemoveState(view.getOldUserRemoveState());
                auditUserDTO.setOldPass(view.getOldUserPassword());
                auditUserDTO.setNewPass(view.getNewUserPassword());

                auditUserDTOs.add(auditUserDTO);
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }

        return auditUserDTOs;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение количества записей в таблице аудита документов
     *
     * @return количество записей
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public long getAuditDocCount() throws DBNullException {
        Long count = null;
        try {
            //Получение количества записей
            count = auditDAO.getAuditDocCount();
            if (count == null) {
                throw new DBNullException("Количество записей списка аудитов документа равно 0");
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение количества записей в отфильтрованной таблице аудита документов
     *
     * @param searchFilter объект с полями для фильтрации
     * @return количество записей
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public long getFilterAuditDocCount(SearchFilter searchFilter) throws DBNullException {
        Date startDate = searchFilter.getStartDate();
        Date endDate;
        Long count = null;
        //Получение начальной и конечной даты для фильтрации
        if (searchFilter.getEndDate() == null) {
            endDate = new Date();
        } else {
            endDate = searchFilter.getEndDate();
        }
        try {
            //Получение количества записей
            count = auditDAO.getFilterAuditDocCount(startDate, endDate);
            if (count == null) {
                throw new DBNullException("Количество записей отфильтрованного списка аудитов документа равно 0");
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение количества записей в таблице аудита пользователей
     *
     * @return количество записей
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public long getAuditUserCount() throws DBNullException {
        Long count = null;
        try {
            //Получение количества записей
            count = auditDAO.getAuditUserCount();
            if (count == null) {
                throw new DBNullException("Количество записей списка аудитов пользователей равно 0");
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение количества записей в отфильтрованной таблице аудита пользвателей
     *
     * @param searchFilter объект с полями для фильтрации
     * @return количество записей
     * @throws com.cmxv.bussinessinterfaceslayer.util.DBNullException исключение при нулевом результате методов ДАО
     */
    @Override
    public long getFilterAuditUserCount(SearchFilter searchFilter) throws DBNullException {
        Date startDate = searchFilter.getStartDate();
        Date endDate;
        Long count = null;
        //Получение начальной и конечной даты для фильтрации
        if (searchFilter.getEndDate() == null) {
            endDate = new Date();
        } else {
            endDate = searchFilter.getEndDate();
        }

        try {
            //Получение количества записей
            count = auditDAO.getFilterAuditUserCount(startDate, endDate);
            if (count == null) {
                throw new DBNullException("Количество записей отфильтрованного списка аудитов пользователей равно 0");
            }
        } catch (DBNullException e) {
            log.error(e.getMessage());
        }
        return count;
    }
//--------------------------------------------------------------------------------------------------------------------
}
