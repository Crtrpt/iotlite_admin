package com.dj.iotlite.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class RequestUtils {
    static StringBuilder formatParams(HttpRequest request) {
        StringBuilder responseData = new StringBuilder();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        if (!params.isEmpty()) {
            for (Map.Entry<String, List<String>> p : params.entrySet()) {
                String key = p.getKey();
                List<String> vals = p.getValue();
                for (String val : vals) {

                    log.info(key+": "+val+" ");
                }


            }
            System.out.println("");
        }
        return responseData;
    }

    static StringBuilder formatBody(HttpContent httpContent) {
        StringBuilder responseData = new StringBuilder();
        ByteBuf content = httpContent.content();
        if (content.isReadable()) {
            responseData.append(content.toString(CharsetUtil.UTF_8)
                    .toUpperCase());
            responseData.append("\r\n");
        }
        return responseData;
    }

    static StringBuilder evaluateDecoderResult(HttpObject o) {
        StringBuilder responseData = new StringBuilder();
        DecoderResult result = o.decoderResult();

        if (!result.isSuccess()) {
            responseData.append("..Decoder Failure: ");
            responseData.append(result.cause());
            responseData.append("\r\n");
        }

        return responseData;
    }

    static StringBuilder prepareLastResponse(HttpRequest request, LastHttpContent trailer) {
        StringBuilder responseData = new StringBuilder();
        responseData.append("ok\r\n");
        return responseData;
    }
}
