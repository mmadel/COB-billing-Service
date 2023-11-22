package com.cob.billing.enums;

public enum PlaceOfService {

    TELEHEALTH("Telehealth(02)"),
    SCHOOL("School(03)"),
    HOMELESSSHELTER("Homeless Shelter(04)"),
    PRISONCORRECTIONALFACILITY("Prison/Correctional Facility(09)"),
    OFFICE("Office(11)"),
    HOME("Home(12)"),
    ASSISTEDLIVINGFACILITY("Assisted Living Facility(13)"),
    GROUPHOME("Group Home(14)"),
    MOBILEUNIT("Mobile Unit(15)"),
    TEMPORARYLODGING("Temporary Lodging(16)"),
    OFFCAMPUSOUTPATIENTHOSPITAL("Off Campus-Outpatient Hospital(19)"),
    HOSPITAL("Hospital(21)"),
    ONCAMPUSOUTPATIENTHOSPITAL("On Campus-Outpatient Hospital(22)"),
    MILITARYTREATMENTFACILITY("Military Treatment Facility(26)"),
    SKILLEDNURSINGFACILITY("Skilled Nursing Facility(31)"),
    NURSINGFACILITY("Nursing Facility(32)"),
    HOSPICE("Hospice(34)"),
    INDEPENDENTCLINIC("Independent Clinic(49)"),
    COMMUNITYMENTALHEALTHCENTER("Community Mental Health Center(53)"),
    OUTPATIENTREHABILITATION("Outpatient Rehabilitation(62)");

    private final String value;

    PlaceOfService(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
