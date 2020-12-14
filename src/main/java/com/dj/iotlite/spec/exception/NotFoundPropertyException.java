package com.dj.iotlite.spec.exception;


import com.dj.iotlite.exception.IExceptionEnum;
import lombok.Data;

import java.util.HashMap;

@Data
public class NotFoundPropertyException extends RuntimeException {

    private Integer code;

    private Object data = new HashMap<String,String>(1);

    public NotFoundPropertyException() {
        super("");
        this.code = 1;
    }

    public NotFoundPropertyException(String message) {
        super(message);
        this.code = 1;
    }

    public NotFoundPropertyException(IExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public NotFoundPropertyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public NotFoundPropertyException(IExceptionEnum exceptionEnum, Object data) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.data = data;
    }

    public NotFoundPropertyException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
}
