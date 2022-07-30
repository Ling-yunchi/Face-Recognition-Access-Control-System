package com.hitwh.face.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangrong
 * @date 2022/4/19 8:52
 */
@NoArgsConstructor
@Data
public class BaseResult<T> {
    private boolean success;
    private String message;
    private T data;

    public void construct(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
