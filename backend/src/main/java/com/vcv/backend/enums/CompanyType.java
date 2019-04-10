package com.vcv.backend.enums;

import java.io.Serializable;

public enum CompanyType implements Serializable {
    VCV_Staff  (3, "VCV"),
    Dealership (2, "DEA"),
    Insurance  (2, "INS"),
    Garage     (2, "GAR"),
    Mechanic   (1, "MEC"),
    Casual     (0, "CAS");

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
            case VCV_Staff:
                return "VCV Staff";
            case Dealership:
                return "Dealership";
            case Insurance:
                return "Insurance";
            case Garage:
                return "Garage";
            case Mechanic:
                return "Mechanic";
            case Casual:
                return "Casual";
            default:
                return "";
        }
    }
}
