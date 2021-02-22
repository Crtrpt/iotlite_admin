# IOTLITE
Simple to use IoT platform

## required
- JDK >=  11 
- clickhouse >= 21.1.2.15
- mysql >= 8.0
- mqtt >= 3.1
- redis >=5.0

## run
java -jar iotlite.jar

## 设置设备属性

设置一个或者多个设备属性
```
Tenant/ProductSn/DeviceSn/Property
```
- Psn001 产品 Psn001
- Dsn001 设备 Dsn001
topic
```
default/Psn001/Dsn001/property
```
payload
```
{
    power:"3000",
}
```

---
- redis 设备最新数据
- mysql 设备元数据
- mqtt 设备通讯
---