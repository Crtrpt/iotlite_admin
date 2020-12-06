package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.GatewayDto;
import com.dj.iotlite.api.dto.GatewayListDto;
import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.entity.product.Gateway;
import com.dj.iotlite.service.GatewayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GatewayController extends BaseController {

    @Autowired
    GatewayService gatewayService;

    @GetMapping("/list")
    public ResDto<Page<GatewayListDto>> list(DeviceQueryForm query) {
        Page<GatewayListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Gateway> res = gatewayService.getGatewayList(query);
        res.forEach(s -> {
            GatewayListDto t = new GatewayListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(gatewayService.removeGateway(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save() {
        return success(gatewayService.saveGateway());
    }

    @GetMapping("/query")
    public ResDto<GatewayDto> query(@RequestParam("uuid") String uuid) {
        return success(gatewayService.queryGateway(uuid));
    }
}
