package com.leodelmiro.mercadolivre.newuser;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    @PastOrPresent
    @Column(updatable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt = Instant.now();

    @Deprecated
    public User() {

    }

    public User(@NotBlank @Email String email, CleanPassword cleanPassword) {
        Assert.hasLength(email, "Login não pode ser vazio");
        Assert.notNull(cleanPassword, "O Objeto senha limpa não pode ser nulo");

        this.email = email;
        this.password = cleanPassword.hash();
    }
}
