package com.kaishengit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AjaxResult {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String status;
    private String state;
    private String message;
    private Object data;

    public AjaxResult(){}
    public AjaxResult(String status,String message) {
        this.status = status;
        this.message = message;
    }
    public AjaxResult(String state, Object data) {
        this.state = state;
        this.data = data;
    }

    public AjaxResult(Object data) {
        this.status = SUCCESS;
        this.data = data;
    }

}
