package com.zys.elm.models;

import lombok.Data;

/**
 * Created by Mr on 2018/3/17.
 */
@Data
public class Response<T> {
    private boolean success=false;
    private String code;
    private String msg;
    private T result;

    public Response(T result) {
        this.result = result;
        success=true;
    }

    public Response(String code) {
        this.code = code;
        success=false;
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
        success=false;
    }
}
