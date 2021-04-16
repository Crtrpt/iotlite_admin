package com.dj.iotlite.api;


import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController extends BaseController {

    @Autowired
    ImageService imageService;

    /**
     * upload image
     * @param dataFile
     * @return
     */
    @RequestMapping("/upload")
    public ResDto upload(@RequestPart("file") @NotNull @NotBlank MultipartFile dataFile){
        return success(imageService.upload(dataFile));
    }
}
