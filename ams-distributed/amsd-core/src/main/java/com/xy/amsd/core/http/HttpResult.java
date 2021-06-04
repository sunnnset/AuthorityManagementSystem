package com.xy.amsd.core.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一封装了HTTP Response的格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult {

    private int status = 200;
    private String msg;
    private Object data;

    public static HttpResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static HttpResult error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static HttpResult error(int code, String msg) {
        return new HttpResult(code, msg);
    }

    public static HttpResult ok(int code, String msg) {
        return new HttpResult(code, msg);
    }

    public static HttpResult ok(String msg) {
        return new HttpResult(HttpStatus.SC_OK, msg);
    }

    public static HttpResult ok(Object data) {
        return new HttpResult(data);
    }

    public static HttpResult ok() {
        return new HttpResult();
    }


    public HttpResult(Object data) {
        this.data = data;
    }

    public HttpResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
