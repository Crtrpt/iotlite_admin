package com.dj.iotlite.api.form;

import lombok.Data;

import java.util.HashMap;

@Data
public class HookSaveForm {
    String key;
    HashMap<String,String> data;
}
