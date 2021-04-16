package com.dj.iotlite.service;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface ImageService {
    Object upload(@NotNull @NotBlank MultipartFile dataFile);
}
