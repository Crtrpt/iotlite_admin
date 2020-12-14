package com.dj.iotlite.spec.exception;


import com.dj.iotlite.exception.IExceptionEnum;
import lombok.Data;

import java.util.HashMap;

@Data
public class NotFoundActionException extends RuntimeException {

    private Integer code;

    private Object data = new HashMap<String,String>(1);

    public NotFoundActionException() {
        super("");
        this.code = 1;
    }

    public NotFoundActionException(String message) {
        super(message);
        this.code = 1;
    }

    public NotFoundActionException(IExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public NotFoundActionException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public NotFoundActionException(IExceptionEnum exceptionEnum, Object data) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.data = data;
    }

    public NotFoundActionException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
}
