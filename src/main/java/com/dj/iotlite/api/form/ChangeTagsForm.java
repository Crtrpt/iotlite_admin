package com.dj.iotlite.api.form;

import lombok.Data;

import java.util.List;

@Data
public class ChangeTagsForm {
    String productSn;
    List<String> tags;
}
