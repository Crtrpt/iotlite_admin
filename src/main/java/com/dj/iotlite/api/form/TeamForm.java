package com.dj.iotlite.api.form;

import com.dj.iotlite.utils.PasswordUtils;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class TeamForm {
    String name;
    String description;
}
