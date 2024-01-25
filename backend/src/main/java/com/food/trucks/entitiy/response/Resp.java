package com.food.trucks.entitiy.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class Resp<T> implements Serializable {

    private String code;
    private String message;
    private Integer status;
    private T data;

    /**
     * 用于追踪此次请求的日志信息的ID.
     */
//    private String traceId;

    public Resp(String code, String message, Integer status, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public Resp(String code, Integer status, T data) {
        this(code, null,status, data);
    }

    public Resp(String code, String message, Integer status) {
        this(code, message,status, null);
    }

    public static <T> Resp<T> ok(T r) {
        return new Resp<>(String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase(),HttpStatus.OK.value(), r);
    }

    public static <T> Resp<T> success() {
        return Resp.ok(null);
    }

    public static <R> Resp<R> fail(String code, String message) {
        return new Resp<>(code, message,HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

//    public static <R> Result<R> fail(BusinessAndServerErrorMessage aDefault) {
//        return Result.fail(aDefault.getCode().code(), aDefault.getMessage(null));
//    }

}
