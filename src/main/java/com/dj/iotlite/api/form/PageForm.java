package com.dj.iotlite.api.form;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageForm {
    String words;

    int pageSize = 0;
    int pageNum = 10;

    String sortBy = "id";
    boolean sortDesc = true;


    public Pageable getPage() {
        return PageRequest.of(this.pageNum - 1, 10, Sort.by(new Sort.Order(this.sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy)));
    }
}
