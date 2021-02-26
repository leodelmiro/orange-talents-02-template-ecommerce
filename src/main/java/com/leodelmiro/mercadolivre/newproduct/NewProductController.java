package com.leodelmiro.mercadolivre.newproduct;

import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class NewProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new SpecificsNameCantBeEqualsValidator());
    }

    @PostMapping
    @Transactional
    public CreatedProductDTO insert(@AuthenticationPrincipal User loggedUser, @RequestBody @Valid NewProductForm newProductForm) {
        Product newProduct = newProductForm.toModel(entityManager, loggedUser);

        entityManager.persist(newProduct);

        return new CreatedProductDTO(newProduct);
    }

}
