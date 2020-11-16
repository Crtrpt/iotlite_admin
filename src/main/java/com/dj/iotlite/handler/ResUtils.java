package com.dj.iotlite.handler;

import com.dj.iotlite.api.dto.ResDto;

public class ResUtils {
    public static ResDto fail(Integer code, String message, Object data) {
        ResDto res = new ResDto();
        res.setCode(code);
        res.setMsg(message);
        res.setData(data);
        return res;
    }
}
