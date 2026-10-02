package com.example.imageSaver.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PaginatedResponseDTO <UploadImageResponseDTO>{

    private List<UploadImageResponseDTO> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;

    // Constructor

    public PaginatedResponseDTO(){}
    public PaginatedResponseDTO(Page<UploadImageResponseDTO> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.isLast = page.isLast();
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
