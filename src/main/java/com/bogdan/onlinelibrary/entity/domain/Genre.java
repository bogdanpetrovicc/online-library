package com.bogdan.onlinelibrary.entity.domain;

public enum Genre {
    SOFTWARE_ENGINEERING("Software engineering"),
    CYBER_SECURITY("Cyber security"),
    BLOCKCHAIN("Blockchain"),
    VIRTUALIZATION("Virtualization");

    private final String value;
    Genre(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
