package com.dj.iotlite.adaptor.IotlitHttpAdaptor;

import lombok.Data;




@Data
class BzException  extends RuntimeException {
    private int code=-1;

    public BzException(String message,int code) {
        super(message);
        this.code = code;
    }
}
