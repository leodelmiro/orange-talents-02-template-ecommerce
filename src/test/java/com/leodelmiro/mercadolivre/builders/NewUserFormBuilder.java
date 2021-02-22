package com.leodelmiro.mercadolivre.builders;

import com.leodelmiro.mercadolivre.newuser.NewUserForm;

public class NewUserFormBuilder {

    private String email;
    private String password;

    public NewUserFormBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public NewUserFormBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public NewUserForm build() {
        return new NewUserForm(email, password);
    }
}
