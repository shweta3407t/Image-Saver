package com.example.imageSaver.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PaginatedResponseDTO <UploadImageResponseDTO>{

    private List<UploadImageResponseDTO> content;
    private int currentPage;
//    private int pageSize;
    private long totalElements;//number of element come from db on search
    private  long itemLimit;//item in each page
    private int totalPages;
    private boolean hasPrevious;
    private boolean hasNext;

    // Constructor

    public PaginatedResponseDTO(){}

    public PaginatedResponseDTO(List<UploadImageResponseDTO> content, int currentPage, long totalElements, long itemLimit, int totalPages, boolean hasPrevious, boolean hasNext) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.itemLimit = itemLimit;
        this.totalPages = totalPages;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
    }

    public List<UploadImageResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<UploadImageResponseDTO> content) {
        this.content = content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public long getItemLimit() {
        return itemLimit;
    }

    public void setItemLimit(long itemLimit) {
        this.itemLimit = itemLimit;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
