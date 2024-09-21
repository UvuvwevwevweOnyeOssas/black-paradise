package com.example.demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    HORNY_GUY(
            Set.of(
                    Permission.HORNY_GUY_READ,
                    Permission.HORNY_GUY_CREATE,
                    Permission.HORNY_GUY_DELETE,
                    Permission.HORNY_GUY_UPDATE
            )
    ),
    DATE_GIRL(
            Set.of(
                    Permission.DATE_GIRL_READ,
                    Permission.DATE_GIRL_CREATE,
                    Permission.DATE_GIRL_DELETE,
                    Permission.DATE_GIRL_UPDATE
            )
    ),
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_CREATE,
                    Permission.HORNY_GUY_READ,
                    Permission.HORNY_GUY_CREATE,
                    Permission.HORNY_GUY_DELETE,
                    Permission.HORNY_GUY_UPDATE,
                    Permission.DATE_GIRL_READ,
                    Permission.DATE_GIRL_CREATE,
                    Permission.DATE_GIRL_DELETE,
                    Permission.DATE_GIRL_UPDATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}