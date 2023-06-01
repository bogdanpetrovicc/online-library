package com.bogdan.onlinelibrary.entity.domain;

public enum MemberType {
    PREMIUM("Premium"),
    STANDARD("Standard");

    private final String value;

    MemberType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
