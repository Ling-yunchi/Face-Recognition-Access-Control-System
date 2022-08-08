package com.hitwh.face.model;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/8/8 10:19
 */
public class BasePageResult<T> {
    private Boolean success;
    private String message;
    private List<T> data;
    private Integer totalPages;
    private Integer currentPage;
    private Long totalElement;

    public BasePageResult() {
    }

    public BasePageResult(Boolean success, String message, Page<T> page) {
        this.success = success;
        this.message = message;
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.totalElement = page.getTotalElements();
    }

    public void construct(Boolean success, String message, Page<T> page) {
        this.success = success;
        this.message = message;
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.totalElement = page.getTotalElements();
    }

    public void construct(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}