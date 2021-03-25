package com.dj.iotlite.adaptor.IotlitHttpAdaptor;

import com.dj.iotlite.adaptor.MessageScheduling;
import com.google.gson.Gson;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import io.undertow.util.StatusCodes;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
class Ret{
    int code;
    String msg;
    Object data;
    String toJson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }
}

@Slf4j
public class MessageCallback implements HttpHandler, MessageScheduling {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange)  {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        try {
            if(!Authenticator.check(httpServerExchange.getRequestURI(),httpServerExchange.getRequestHeaders().get("Authorization"))){
                //TODO 处理设备授权
                return;
            }

            if (httpServerExchange.getRequestMethod().equals(Methods.POST)) {
                httpServerExchange.getRequestReceiver().receiveFullBytes((exchange1, bytes) -> {
                    try {
                        dispatch(exchange1.getRequestURI(), bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                httpServerExchange.getResponseSender().send(Ret.builder().msg("success").code(0).build().toJson());

            } else {
                httpServerExchange.setStatusCode(StatusCodes.BAD_REQUEST);
                throw new BzException("please use post method",-1);
            }
        }catch (BzException e){
            httpServerExchange.setStatusCode(StatusCodes.BAD_GATEWAY);
            httpServerExchange.getResponseSender().send(Ret.builder().msg(e.getMessage()).code(e.getCode()).build().toJson());
            e.printStackTrace();
        }

    }
}
