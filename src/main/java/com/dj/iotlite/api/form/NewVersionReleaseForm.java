package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class NewVersionReleaseForm {
    String productSn;
    String version;
    String verDescription;
    String minHdVersion;
}
