package com.cob.billing.model.integration.nppes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Result {
    public String created_epoch;
    public String enumeration_type;
    public String last_updated_epoch;
    public String number;
    public ArrayList<Address> addresses;
    public ArrayList<Object> practiceLocations;
    public Basic basic;
    public ArrayList<Taxonomy> taxonomies;
    public ArrayList<Object> identifiers;
    public ArrayList<Object> endpoints;
    public ArrayList<Object> other_names;
}
