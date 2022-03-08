package ru.platon.bot2.entities;

import java.util.stream.Stream;

public enum Status {
    CUSTOMER("Клиент"),
    CURATOR("Куратор"),
    TEACHER("Учитель"),
    ADMINISTRATOR("Администратор");

    private final String rusName;

    Status(String rusName) {
        this.rusName = rusName;
    }

    public String getRusName() {
        return rusName;
    }

    public static Status getLinkType(String type) {
        return Stream.of(Status.values())
                .filter(status -> String.valueOf(status).equals(type))
                .findFirst()
                .orElse(null);
    }
}
