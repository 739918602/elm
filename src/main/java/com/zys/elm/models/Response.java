package com.zys.elm.models;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Mr on 2018/3/17.
 */
@Slf4j
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

    @Override
    public String toString() {
        String res =  JSONObject.toJSONString(this);
        log.info(res);
        return res;
    }
}
