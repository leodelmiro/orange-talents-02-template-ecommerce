package com.leodelmiro.mercadolivre.builders;

import com.leodelmiro.mercadolivre.common.security.LoginInputDTO;
import com.leodelmiro.mercadolivre.newuser.NewUserForm;

public class LoginInputDtoBuilder {

    private String email;
    private String password;

    public LoginInputDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public LoginInputDtoBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginInputDTO build() {
        return new LoginInputDTO(email, password);
    }
}
