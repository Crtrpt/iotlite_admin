package com.dj.iotlite.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IndexController extends BaseController {

    @RequestMapping("/")
    public Object Index() {
        return success("success");
    }

}
