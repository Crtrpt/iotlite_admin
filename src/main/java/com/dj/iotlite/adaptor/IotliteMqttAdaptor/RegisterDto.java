package com.dj.iotlite.adaptor.IotliteMqttAdaptor;

import lombok.Data;

@Data
public class RegisterDto {
    String productSn;
    String version;
    String hdVersion;
    String deviceSn;
}
