package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.ProductForm;
import com.dj.iotlite.api.form.ProductQueryForm;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.service.DeviceService;
import org.springframework.beans.BeanUtils;
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
    public ResDto<Page<ProductListDto>> list(ProductQueryForm query) {

        Page<ProductListDto> ret = new Page<ProductListDto>();
        org.springframework.data.domain.Page<Product> res = deviceService.getProductList(query);
        res.forEach(s -> {
            ProductListDto t = new ProductListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

    @GetMapping("/all")
    public ResDto<List<OptionDto>> all() {
        List<OptionDto> ret = deviceService.getAllProduct();
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(deviceService.removeProduct(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody ProductForm productForm) {
        return success(deviceService.saveProduct(productForm));
    }

    @GetMapping("/info")
    public ResDto<ProductDto> query(@RequestParam("id") Long id) {
        return success(deviceService.queryProduct(id));
    }
}
