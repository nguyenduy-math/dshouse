package com.bigdata.newmission.domain;

import lombok.Data;

import java.io.InputStream;

@Data
public class FileDTO {
    private InputStream inputStream;
    private String contentType;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
