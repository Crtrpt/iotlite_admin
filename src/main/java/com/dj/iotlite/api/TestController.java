package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.dto.UserListDto;
import com.dj.iotlite.api.form.UserForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.service.UserService;
import com.dj.iotlite.spec.SpecV1;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController extends BaseController {

    @Autowired
    UserService userService;

    @GetMapping("/groovy")
    public ResDto<Object> groovy() throws Exception {
        File f=new File("./spec/temperature.spec.json");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String specV1Json;
        String sp="";

        while ((specV1Json = br.readLine()) != null){
            sp=sp+specV1Json;
        }
        //            System.out.println(specV1Json);
        SpecV1 specV1=new SpecV1();
        specV1.fromJson(sp);
        String[] roots = new String[] { "." };

        specV1.action("turn off");

        GroovyScriptEngine gse = new GroovyScriptEngine(roots);
        Binding binding = new Binding();
        binding.setVariable("input", "world");
        gse.run("test.groovy", binding);
        System.out.println(binding.getVariable("output"));
        return success("success");
    }
}
