package com.cmxv.bussinesslayer.services;

import com.cmxv.bussinessinterfaceslayer.DocumentWebService;
import com.cmxv.datainterfaceslayer.daointerfaces.DocumentBaseDAO;
import com.cmxv.modellayer.DBentities.DocumentBase;
import com.cmxv.modellayer.dto.DocumentDTO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path(value = "documentService")
@Component
public class DocumentWebServiceImpl implements DocumentWebService{
    
    @Autowired
    private DocumentBaseDAO documentBaseDAO;

    @Override
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{docId}")
    public DocumentDTO get(@PathParam("docId") String docId) {
        System.out.println("---" + this.getClass().getCanonicalName() + " вызван метод getCustomer()");
        DocumentDTO documentDTO = new DocumentDTO();
        DocumentBase document = documentBaseDAO.getDocumentById(Integer.parseInt(docId));
        //Инициализация параметров
        documentDTO.setAuthorId(document.getDocumentAuthor().getUserId());
        documentDTO.setStateId(document.getDocumentState().getStateId());
        documentDTO.setTypeId(document.getDocumentType().getDocTypeId());

        return documentDTO;
    }
    
}
