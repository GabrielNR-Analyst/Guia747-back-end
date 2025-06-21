package com.guia747.domain.users.exception;

import com.guia747.shared.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
