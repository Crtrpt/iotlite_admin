package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.*;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.service.AuthService;
import com.dj.iotlite.service.DeviceService;
import com.dj.iotlite.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class ProductController extends BaseController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    AuthService authService;


    @Autowired
    ImageService imageService;

    /**
     * upload image
     * @param dataFile
     * @return
     */
    @RequestMapping("/image")
    public ResDto upload(@RequestPart("file") @NotNull @NotBlank MultipartFile dataFile,@RequestPart("productSn") String productSn){
        return success(deviceService.updateProductImage(dataFile,productSn));
    }

    @GetMapping("/list")
    public ResDto<Page<ProductListDto>> list(ProductQueryForm query) {
        Page<ProductListDto> ret = new Page<ProductListDto>();
        org.springframework.data.domain.Page<Product> res = deviceService.getProductList(query,authService.getUserInfo());
        res.forEach(s -> {
            ProductListDto t = new ProductListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        ret.setTotal(res.getTotalElements());
        return success(ret);
    }

    @GetMapping("/deviceList")
    public ResDto<Page<DeviceListDto>> deviceList(DeviceQueryForm query) {

        Page<DeviceListDto> ret = new Page<DeviceListDto>();
        org.springframework.data.domain.Page<Device> res = deviceService.getDeviceList(query,authService.getUserInfo());
        res.forEach(s -> {
            DeviceListDto t = new DeviceListDto();
            BeanUtils.copyProperties(s, t);
            deviceService.getProduct(s.getProductSn(), t.getProduct());
            ret.getList().add(t);
        });
        ret.setTotal(res.getTotalElements());
        return success(ret);
    }

    @GetMapping("/mapDeviceList")
    public ResDto mapDeviceList(MapDeviceQueryForm query) {
        return success( deviceService.getMapDeviceList(query,authService.getUserInfo()));
    }


    @GetMapping("/all")
    public ResDto<List<OptionDto>> all() {
        List<OptionDto> ret = deviceService.getAllProduct(authService.getUserInfo());
        return success(ret);
    }

    @GetMapping("/allAdapter")
    public ResDto<List<AdapterOptionDto>> allAdapter() {
        List<AdapterOptionDto> ret = deviceService.getAllAdapterOptionDto();
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestBody ProductRemoveForm form) {
        return success(deviceService.removeProduct(form,authService.getUserInfo()));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody ProductForm productForm) {
        return success(deviceService.saveProduct(productForm,authService.getUserInfo()));
    }

    @GetMapping("/info")
    public ResDto<ProductDto> query(@RequestParam("sn") String sn) {
        return success(deviceService.queryProduct(sn,authService.getUserInfo()));
    }

    @PostMapping("/saveModel")
    public ResDto<Boolean> saveModel(@RequestBody ProductSaveModelForm form) {
        return success(deviceService.saveModel(form,authService.getUserInfo()));
    }

    @PostMapping("/saveAccess")
    public ResDto<Boolean> saveAccess(@RequestBody ProductSaveAccessForm form) {
        return success(deviceService.saveAccess(form,authService.getUserInfo()));
    }

    @PostMapping("/refreshProductKey")
    public ResDto<Boolean> refreshProductKey(@RequestBody ProductRefreshProductKeyForm form) {
        return success(deviceService.refreshProductKey(form,authService.getUserInfo()));
    }

    @PostMapping("/changeTags")
    public ResDto<Boolean> changeTags(@RequestBody ChangeTagsForm form) {
        return success(deviceService.changeTags(form,authService.getUserInfo()));
    }

    @PostMapping("/saveBase")
    public ResDto<Boolean> saveBase(@RequestBody ProductSaveBaseForm form) {
        return success(deviceService.saveProductBase(form,authService.getUserInfo()));
    }

}
