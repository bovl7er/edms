package com.cmxv.weblayer.managedbeans;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
@ManagedBean
@RequestScoped
public class FileDownloadView {

    private static final Logger log = Logger.getLogger(FileDownloadView.class);

    Map<String, byte[]> attachments = new HashMap<>();
    List<String> attachmentNames = new ArrayList<>();
    List<byte[]> attachmentContent = new ArrayList<>();
//--------------------------------------------------------------------------------------------------------------------

    public List<String> getAttachmentNames() {
        return attachmentNames;
    }
//--------------------------------------------------------------------------------------------------------------------

    public void setAttachments(Map<String, byte[]> attachments) {
        this.attachments = attachments;
    }
//--------------------------------------------------------------------------------------------------------------------

    public Map<String, byte[]> getAttachments() {
        return attachments;
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Загрузка вложения документа
     */
    public void download() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        String fileName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("attachmentName");
        byte[] fileContent = getAttachments().get(fileName);
        File downloadFile = new File(fileName);
        try {
            FileUtils.writeByteArrayToFile(downloadFile, fileContent);
            String contentType = ec.getMimeType(fileName);
            int contentLength = (int) downloadFile.length();
            ec.responseReset();
            ec.setResponseContentType(contentType);
            ec.setResponseContentLength(contentLength);
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            OutputStream output = ec.getResponseOutputStream();

            Files.copy(downloadFile.toPath(), output);

            fc.responseComplete();
        } catch (IOException e) {
            log.error("Ошибка при загрузке вложения к документу", e);
        }

    }

//--------------------------------------------------------------------------------------------------------------------
}
