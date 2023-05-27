package com.bogdan.onlinelibrary.entity.domain;

public enum Genre {
    SCI_FI("Naučna fantastika"),
    HORROR("Horor"),
    DRAMA("Drama"),
    ACTION("Akcija"),
    COMEDY("Komedija"),
    ROMANCE("Romansa");

    private final String value;
    Genre(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
