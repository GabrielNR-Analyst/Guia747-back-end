package com.guia747.domain.exception;

public class UserAccountNotFoundException extends RuntimeException {

    public UserAccountNotFoundException() {
        super("Usuário não encontrado");
    }
}
