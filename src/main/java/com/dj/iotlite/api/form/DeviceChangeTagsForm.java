package com.dj.iotlite.api.form;

import lombok.Data;

import java.util.List;

@Data
public class DeviceChangeTagsForm {
    String productSn;
    String sn;
    List<String> tags;
}
