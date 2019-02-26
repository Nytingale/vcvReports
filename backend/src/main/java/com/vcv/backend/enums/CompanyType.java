package com.vcv.backend.enums;

public enum CompanyType {
    VCV_REPORTS (5, "VCV"),
    DEALERSHIP  (4, "DEA"),
    INSURANCE   (3, "INS"),
    GARAGE      (2, "GAR"),
    MECHANIC    (1, "MEC"),
    CASUAL      (0, "CAS");

    int l;
    String a;

    CompanyType(int l, String a) {
        this.l = l;
        this.a = a;
    }

    public int level() {
        return l;
    }
    public String acronym() {
        return a;
    }

    @Override
    public String toString() {
        switch(this) {
            case VCV_REPORTS:
                return "Staff";
            case DEALERSHIP:
                return "Dealership";
            case INSURANCE:
                return "Insurance";
            case GARAGE:
                return "Garage";
            case MECHANIC:
                return "Mechanic";
            case CASUAL:
                return "Casual";
            default:
                return "";
        }
    }
}
