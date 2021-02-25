package com.leodelmiro.mercadolivre.newproduct;

import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class NewProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new SpecificsNameCantBeEqualsValidator());
    }

    @PostMapping
    @Transactional
    public CreatedProductDTO insert(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid NewProductForm newProductForm) {
        User creator = (User) userDetails;
        Product newProduct = newProductForm.toModel(entityManager, creator);

        entityManager.persist(newProduct);

        return new CreatedProductDTO(newProduct);
    }
}
