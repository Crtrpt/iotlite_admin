package com.dj.iotlite.entity.alarm;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Long>, JpaSpecificationExecutor<Alarm>, JpaRepository<Alarm, Long> {

}
