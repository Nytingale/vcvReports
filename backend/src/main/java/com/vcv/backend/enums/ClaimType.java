package com.vcv.backend.enums;

import java.io.Serializable;

public enum ClaimType implements Serializable {
    PeronsalInjury (4, "INJ"),
    TotalLoss      (3, "TTL"),
    Liability      (2, "LIA"),
    Accident       (1, "ACC");

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
            case PeronsalInjury:
                return "Personal Injury";

            case TotalLoss:
                return "Total Loss";

            case Liability:
                return "Liability";

            case Accident:
                return "Accident";

            default:
                return "";
        }
    }
}
