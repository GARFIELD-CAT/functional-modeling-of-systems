package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import lombok.Getter;

@Getter
public enum UserRoles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }
}