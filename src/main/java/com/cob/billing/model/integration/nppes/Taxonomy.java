package com.cob.billing.model.integration.nppes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Taxonomy {
    public String code;
    public String taxonomy_group;
    public String desc;
    public String state;
    public String license;
    public boolean primary;
}
