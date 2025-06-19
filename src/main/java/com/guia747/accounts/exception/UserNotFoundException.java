package com.guia747.accounts.exception;

import com.guia747.common.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
