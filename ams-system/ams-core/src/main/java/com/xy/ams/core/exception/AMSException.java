package com.xy.ams.core.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AMSException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public AMSException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AMSException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public AMSException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public AMSException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
