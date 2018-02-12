package com.cmxv.bussinessinterfaceslayer;

import com.cmxv.bussinessinterfaceslayer.util.DBNullException;
import com.cmxv.modellayer.dto.AuditDocDTO;
import com.cmxv.modellayer.dto.AuditUserDTO;
import com.cmxv.modellayer.business.SearchFilter;
import java.util.List;

public interface AuditService {
    
    public List<AuditDocDTO> loadAuditDocTable(Integer startId, Integer endId) throws DBNullException;
    
    public List<AuditDocDTO> loadFilterAuditDocTable(SearchFilter searchFilter,Integer startId, Integer endId) throws DBNullException;
    
    public List<AuditUserDTO> loadAuditUserTable(Integer startId, Integer endId) throws DBNullException;
    
    public List<AuditUserDTO> loadFilterAuditUserTable(SearchFilter searchFilter,Integer startId, Integer endId) throws DBNullException;
    
    public long getAuditDocCount() throws DBNullException;
    
    public long getFilterAuditDocCount(SearchFilter searchFilter) throws DBNullException;
    
    public long getAuditUserCount() throws DBNullException;
    
    public long getFilterAuditUserCount(SearchFilter searchFilter) throws DBNullException;
}
