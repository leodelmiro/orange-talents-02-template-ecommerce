package com.leodelmiro.mercadolivre.newuser;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class NewUserController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public void insert(@RequestBody @Valid NewUserForm newUserForm) {
        User newUser = newUserForm.toModel();

        entityManager.persist(newUser);
    }
}
