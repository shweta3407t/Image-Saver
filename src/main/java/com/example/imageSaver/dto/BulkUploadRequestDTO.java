package com.example.imageSaver.dto;

import com.example.imageSaver.models.ImageMetaData;

public class BulkUploadRequestDTO {

    private  UploadImageRequestDTO[] bulkRequest;

    public BulkUploadRequestDTO(UploadImageRequestDTO[] bulkRequest) {
        this.bulkRequest = bulkRequest;
    }

    public UploadImageRequestDTO[] getBulkRequest() {
        return bulkRequest;
    }

    public void setBulkRequest(UploadImageRequestDTO[] bulkRequest) {
        this.bulkRequest = bulkRequest;
    }
}
