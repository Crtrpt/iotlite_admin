package com.dj.iotlite.api.form;

import com.dj.iotlite.entity.organization.Organization;
import lombok.Data;

@Data
public class OrganizationQueryForm extends PageForm {
    Long   OrganizationId;
}
