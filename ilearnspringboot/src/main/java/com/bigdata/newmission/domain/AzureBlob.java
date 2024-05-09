package com.bigdata.newmission.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class AzureBlob {
    @Field
    private String docId;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
