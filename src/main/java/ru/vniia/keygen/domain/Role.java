package ru.vniia.keygen.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    CANGEN; //роль что пользователь может генерировать пароли

    @Override
    public String getAuthority() {
        return name();
    }
}
