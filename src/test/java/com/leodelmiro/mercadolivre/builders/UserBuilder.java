package com.leodelmiro.mercadolivre.builders;

import com.leodelmiro.mercadolivre.newuser.CleanPassword;
import com.leodelmiro.mercadolivre.newuser.User;

public class UserBuilder {

    private String email;
    private String password;

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(email, new CleanPassword(password));
    }
}
