package com.vcv.backend.enums;

public enum JobType {
    ACCIDENT (3, "ACC"),
    SERVICE  (2, "SRV"),
    REPAIR   (1, "REP");

    int w;
    String a;

    JobType(int w, String a) {
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
        switch (this) {
            case ACCIDENT:
                return "Accident";

            case SERVICE:
                return "Service";

            case REPAIR:
                return "Repair";

            default:
                return "";
        }
    }
}
