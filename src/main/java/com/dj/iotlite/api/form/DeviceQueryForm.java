package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class DeviceQueryForm extends PageForm {
    String productSn;
    Long proxyId;
    Long groupId;
}
