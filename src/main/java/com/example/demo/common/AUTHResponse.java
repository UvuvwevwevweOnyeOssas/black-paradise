package com.example.demo.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AUTHResponse implements Serializable {
    public String msg;
    public Object data;
    public Integer statusCode;
    private long timestamp;

    public static AUTHResponse fail(String errorMsg) {
        return AUTHResponse.builder()
                .statusCode(Constant.FAILURE_CODE)
                .msg(errorMsg)
                .data("*****")
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }

    public static AUTHResponse success(String msg, Object data) {
        return AUTHResponse.builder()
                .statusCode(Constant.SUCCESS_CODE)
                .msg(msg)
                .data(data)
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }

    public static AUTHResponse fail(String errorMsg,Object data) {
        return AUTHResponse.builder()
                .statusCode(Constant.FAILURE_CODE)
                .msg(errorMsg)
                .data(data.toString())
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }
}
