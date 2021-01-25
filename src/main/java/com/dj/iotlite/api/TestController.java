package com.dj.iotlite.api;


import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.service.UserService;
import com.dj.iotlite.spec.SpecV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class TestController extends BaseController {

    @Autowired
    UserService userService;

    @GetMapping("/groovy")
    public ResDto<Object> groovy() throws Exception {
        File f = new File("./spec/temperature.spec.json");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String specV1Json;
        String sp = "";

        while ((specV1Json = br.readLine()) != null) {
            sp = sp + specV1Json;
        }
        // System.out.println(specV1Json);
        SpecV1 specV1 = new SpecV1();
        specV1.fromJson(sp);

        specV1.action("turn off");
        return success();
    }

    @Autowired
    @Qualifier("clickHouseJdbcTemplate")
    JdbcTemplate postgresJdbcTemplate;

    @GetMapping("/clickhouse")
    public ResDto<Object> clickhouse() throws Exception {
        List<Map<String, Object>> maps = postgresJdbcTemplate.queryForList("select * from test1.userEvents ");

       System.out.println(maps);
        return success();
    }
}
