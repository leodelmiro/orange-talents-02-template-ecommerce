package com.leodelmiro.mercadolivre.newuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_users")
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("user"));
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
