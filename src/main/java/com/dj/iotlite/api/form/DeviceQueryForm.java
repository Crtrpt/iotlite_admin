package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class DeviceQueryForm extends PageForm {
    String productSn;
    String version;
    Long proxyId;
    Long groupId;
}
