package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.OptionDto;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.GetAllVersionForm;
import com.dj.iotlite.api.form.NewVersionReleaseForm;
import com.dj.iotlite.api.form.VersionRemoveForm;
import com.dj.iotlite.entity.product.ProductVersion;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VersionService {

    Boolean newVersion(NewVersionReleaseForm form);

    Page<ProductVersion> getVersionList(DeviceQueryForm query);

    List<OptionDto> getAll(GetAllVersionForm query);

    Object queryProductVersion(String sn, String version);

    Object removeProductVersion(VersionRemoveForm form);
}
