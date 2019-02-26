package com.vcv.backend.enums;

public enum ClaimType {
    PERSONAL_INJURY (4, "INJ"),
    TOTAL_LOSS      (3, "TTL"),
    LIABILITY       (2, "LIA"),
    ACCIDENT        (1, "ACC");

    int w;
    String a;

    ClaimType(int w, String a) {
        this.w = w;
        this.a = a;
    }

    public int weight() {
        return w;
    }
    public String acronym() {
        return a;
    }

    @Override
    public String toString() {
        switch(this) {
            case PERSONAL_INJURY:
                return "Personal Injury";
            case TOTAL_LOSS:
                return "Total Loss";
            case LIABILITY:
                return "Liability";
            case ACCIDENT:
                return "Accident";
            default:
                return "";
        }
    }
}
