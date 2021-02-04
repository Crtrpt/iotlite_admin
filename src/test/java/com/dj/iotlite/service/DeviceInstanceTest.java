package com.dj.iotlite.service;

import com.dj.iotlite.IotliteApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IotliteApplication.class)
@Transactional
class DeviceInstanceTest {

    @Autowired
    DeviceInstance deviceInstance;

    @Test
    void setPropertys() {
        deviceInstance.setProperty("3","1","power","20","测试");
    }
}