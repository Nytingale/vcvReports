package com.vcv.backend.enums;

import java.io.Serializable;

public enum JobType implements Serializable {
    Accident (3, "ACC"),
    Service  (2, "SRV"),
    Repair   (1, "REP");

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
            case Accident:
                return "Accident";

            case Service:
                return "Service";

            case Repair:
                return "Repair";

            default:
                return "";
        }
    }
}
