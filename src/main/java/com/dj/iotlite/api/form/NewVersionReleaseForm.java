package com.dj.iotlite.api.form;

import lombok.Data;

import java.sql.Date;

@Data
public class NewVersionReleaseForm {
    String productSn;
    String version;
    String verDescription;
    String minHdVersion;
    Date startAt;
    Date endAt;
}
