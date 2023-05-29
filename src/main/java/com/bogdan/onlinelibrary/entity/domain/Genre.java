package com.bogdan.onlinelibrary.entity.domain;

public enum Genre {
    SOFTWARE_ENGINEERING("Softversko inženjerstvo"),
    CYBER_SECURITY("Sajber bezbednost"),
    BLOCKCHAIN("Blokčejn"),
    VIRTUALIZATION("Virtualizacija");

    private final String value;
    Genre(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
