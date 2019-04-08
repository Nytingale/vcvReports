package com.vcv.backend.enums;

import java.io.Serializable;

public enum PolicyType implements Serializable {
    Comprehensive (2, "COM"),
    ThirdParty    (1, "3PY");

    int w;
    String a;

    PolicyType(int w, String a) {
        this.w = w;
        this.a = a;
    }

    int weight() {
        return w;
    }
    String acronym() {
        return a;
    }

    @Override
    public String toString() {
        switch(this) {
            case Comprehensive:
                return "Comprehensive";

            case ThirdParty:
                return "Third Party";

            default:
                return "";
        }
    }
}
