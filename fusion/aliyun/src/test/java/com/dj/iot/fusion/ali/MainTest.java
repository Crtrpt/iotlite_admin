package com.dj.iot.fusion.ali;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.QueryDeviceRequest;
import com.aliyuncs.iot.model.v20180120.QueryDeviceResponse;
import com.aliyuncs.iot.model.v20180120.QueryProductListRequest;
import com.aliyuncs.iot.model.v20180120.QueryProductListResponse;
import com.dj.iot.fusion.ali.client.IotClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class MainTest {

    @Test
    public void main() {
        var client = IotClient.getClient();

        QueryProductListResponse response = null;
        QueryProductListRequest request = new QueryProductListRequest();
        request.setPageSize(10);
        request.setCurrentPage(1);
        ;
        try {
            response = client.getAcsResponse(request);

            if (response.getSuccess() != null && response.getSuccess()) {
                response.getData().getList().stream().forEach(p -> {

                    QueryDeviceResponse deviceResponse = null;
                    QueryDeviceRequest deviceRequest = new QueryDeviceRequest();
                    deviceRequest.setProductKey(p.getProductKey());
                    deviceRequest.setCurrentPage(1);
                    deviceRequest.setPageSize(10);

                    try {
                        deviceResponse = client.getAcsResponse(deviceRequest);

                        if (deviceResponse.getSuccess() != null && deviceResponse.getSuccess()) {
                            deviceResponse.getData().stream().forEach(d -> {
                                System.out.println(d.getDeviceName());
                                System.out.println(p.getProductName());
                                System.out.println(d.getProductKey());
                            });
                        } else {
                            log.info("产品下设备列表查询失败");
                        }
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                System.out.println("查询产品列表失败" + response.getErrorMessage());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}