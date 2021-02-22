package com.leodelmiro.mercadolivre.newuser;

import com.leodelmiro.mercadolivre.validation.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewUserForm {

    @NotBlank
    @Email
    @UniqueValue(fieldName = "email", domainClass = User.class)
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    public NewUserForm(@NotBlank @Email String email, @NotBlank @Size(min = 6) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User toModel() {
        return new User(email, new CleanPassword(password));
    }
}
