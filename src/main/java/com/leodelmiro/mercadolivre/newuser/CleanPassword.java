package com.leodelmiro.mercadolivre.newuser;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Representa uma senha limpa no sistema
 *
 */
public class CleanPassword {

    private String password;

    public CleanPassword(@NotBlank @Size(min = 6) String password) {
        Assert.hasLength(password, "Senha não pode ser vazia");
        Assert.state(password.length() >= 6, "Senha deve ter pelo menos 6 caracteres");

        this.password = password;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
