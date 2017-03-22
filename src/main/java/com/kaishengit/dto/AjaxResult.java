package com.kaishengit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by bayllech on 2017/3/22.
 */
@Data
public class AjaxResult {

    private String state;
    private String message;
    private Object data;

    public AjaxResult() {
    }

    public AjaxResult(String message) {
        this.message = message;
    }

    public AjaxResult(String state, Object data) {
        this.state = state;
        this.data = data;
    }
}
