package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.ProductDto;
import com.dj.iotlite.api.dto.ProductListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.service.TaskService;
import com.dj.iotlite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController  extends BaseController{
    @Autowired
    TaskService taskService;

    @GetMapping("/list")
    public ResDto<List<ProductListDto>> list() {
        return success(taskService.getTaskList());
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(taskService.removeTask(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save() {
        return success(taskService.saveTask());
    }

    @GetMapping("/query")
    public ResDto<ProductDto> query(@RequestParam("uuid") String uuid) {
        return success(taskService.queryTask(uuid));
    }
}
