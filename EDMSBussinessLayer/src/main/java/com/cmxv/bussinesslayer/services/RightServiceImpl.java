package com.cmxv.bussinesslayer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmxv.bussinessinterfaceslayer.RightService;
import com.cmxv.datainterfaceslayer.daointerfaces.RightDao;
import com.cmxv.modellayer.DBentities.Right;

@Repository
public class RightServiceImpl implements RightService {

    @Autowired
    RightDao rightDao;
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение всех правил
     *
     * @return список правил
     */
    @Override
    public List<Right> getAllRights() {
        return rightDao.getAllRights();
    }
//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение правила по ид
     *
     * @param id ид правила
     * @return соответствующее правило
     */
    @Override
    public Right getRightById(int id) {
        return rightDao.getRightById(id);
    }
//--------------------------------------------------------------------------------------------------------------------
}
