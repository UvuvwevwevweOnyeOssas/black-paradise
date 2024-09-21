package com.example.demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    HORNY_GUY_READ("hornyGuy:read"),
    HORNY_GUY_UPDATE("hornyGuy:update"),
    HORNY_GUY_CREATE("hornyGuy:create"),
    HORNY_GUY_DELETE("hornyGuy:delete"),

    DATE_GIRL_READ("dateGirl:read"),
    DATE_GIRL_UPDATE("dateGirl:update"),
    DATE_GIRL_CREATE("dateGirl:create"),
    DATE_GIRL_DELETE("dateGirl:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete");

    @Getter
    private final String permission;
}
