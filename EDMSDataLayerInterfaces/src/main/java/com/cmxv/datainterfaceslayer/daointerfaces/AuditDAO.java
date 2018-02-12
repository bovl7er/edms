package com.cmxv.datainterfaceslayer.daointerfaces;

import com.cmxv.modellayer.DBentities.AuditDocumentView;
import com.cmxv.modellayer.DBentities.AuditUserView;
import java.util.Date;
import java.util.List;

public interface AuditDAO {
    
    public List<AuditDocumentView> getDocumentAuditTable(Integer startId, Integer endId);
    
    public List<AuditDocumentView> getDocumentAuditTableByFilter(Date startDate, Date endDate,Integer startId, Integer endId);
    
    public List<AuditUserView> getUserAuditTable(Integer startId, Integer endId);
    
    public List<AuditUserView> getUserAuditTableByFilter(Date startDate, Date endDate,Integer startId, Integer endId);
    
    public long getAuditDocCount();
    
    public long getFilterAuditDocCount(Date startDate,Date endDate);
    
    public long getAuditUserCount();
    
    public long getFilterAuditUserCount(Date startDate,Date endDate);
}
