package com.dj.iotlite.service;

import com.dj.iotlite.api.form.AlarmQueryForm;
import com.dj.iotlite.entity.alarm.Alarm;
import com.dj.iotlite.entity.alarm.AlarmRepository;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.task.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AlarmRepository alarmRepository;

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

    public Page<Alarm> getAlarmList(AlarmQueryForm query) {
        Specification<Alarm> specification = (Specification<Alarm>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        //uuid
                        criteriaBuilder.like(root.get("uuid").as(String.class), "%" + query.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("content").as(String.class), "%" + query.getWords() + "%")
                ));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return alarmRepository.findAll(specification, query.getPage());
    }
}
