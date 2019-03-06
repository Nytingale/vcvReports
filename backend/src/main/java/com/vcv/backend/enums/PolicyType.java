package com.vcv.backend.enums;

public enum PolicyType {
    COMPREHENSIVE (2, "COM"),
    THIRD_PARTY   (1, "3PY");

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
            case COMPREHENSIVE:
                return "Comprehensive";

            case THIRD_PARTY:
                return "Third Party";

            default:
                return "";
        }
    }
}
