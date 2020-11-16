package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.OrganizationListDto;
import com.dj.iotlite.api.dto.ProductDto;
import com.dj.iotlite.api.dto.ProductListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController extends BaseController {

    @Autowired
    DeviceService deviceService;

    @GetMapping("/list")
    public ResDto<List<ProductListDto>> list() {
        return success(deviceService.getProductList());
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(deviceService.removeProduct(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save() {
        return success(deviceService.saveProduct());
    }

    @GetMapping("/query")
    public ResDto<ProductDto> query(@RequestParam("uuid") String uuid) {
        return success(deviceService.queryProduct(uuid));
    }
}
