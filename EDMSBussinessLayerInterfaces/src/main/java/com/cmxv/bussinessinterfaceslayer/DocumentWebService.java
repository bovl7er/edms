package com.cmxv.bussinessinterfaceslayer;

import com.cmxv.modellayer.dto.DocumentDTO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "documentService")
public interface DocumentWebService {
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{docId}")
    public DocumentDTO get(@PathParam("docId") String docId);
    
}
