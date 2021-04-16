package com.dj.iotlite.service;



import com.dj.iotlite.utils.FileUtils;
import com.dj.iotlite.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${app.upload}")
    String path;

    @Value("${app.downloadDomain}")
    String downloadDomain;

    @Override
    public Object upload(@NotNull @NotBlank MultipartFile file) {
        HashMap<String, Object> ret = new HashMap<>();
        try {
            String fileName = UUID.getUUID()+".png";
            InputStream is = file.getInputStream();
            String dateDir = FileUtils.makeDateDir(path);
            String filePath = path + dateDir + fileName;
            Files.copy(is, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            ret.put("url", downloadDomain + dateDir + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
