package com.dj.iotlite.service;

import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.NewVersionReleaseForm;
import com.dj.iotlite.entity.product.ProductVersion;
import org.springframework.data.domain.Page;

public interface VersionService {

    Boolean newVersion(NewVersionReleaseForm form);

    Page<ProductVersion> getVersionList(DeviceQueryForm query);
}
