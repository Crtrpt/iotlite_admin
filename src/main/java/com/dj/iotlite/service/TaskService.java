package com.dj.iotlite.service;

import com.dj.iotlite.entity.product.ProductRepository;
import com.dj.iotlite.entity.task.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public Object getTaskList() {
        return null;
    }

    public Object removeTask(String uuid) {
        return null;
    }

    public Object saveTask() {
        return null;
    }

    public Object queryTask(String uuid) {
        return null;
    }

    public Object getAlarmList() {
        return null;
    }
}
