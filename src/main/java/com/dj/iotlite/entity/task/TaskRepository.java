package com.dj.iotlite.entity.task;


import com.dj.iotlite.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends
        CrudRepository<Task, Long>,
        JpaSpecificationExecutor<Task>,
        JpaRepository<Task, Long> {

}
