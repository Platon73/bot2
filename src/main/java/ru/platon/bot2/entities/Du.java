package ru.platon.bot2.entities;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Du {
    ODIN("1"),
    VTOROY("2");

    private final String rusName;

    Du(String rusName) {
        this.rusName = rusName;
    }

    public String getRusName() {
        return rusName;
    }

    public static Du getLinkType(String type) {
        return Stream.of(Du.values())
                .filter(currency -> String.valueOf(currency).equals(type))
                .findFirst()
                .orElse(null);
    }
}
