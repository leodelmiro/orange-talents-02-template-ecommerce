package com.leodelmiro.mercadolivre.newfeedback;

import com.leodelmiro.mercadolivre.common.validation.ProductNotFoundException;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class NewFeedbackController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/{id}/feedbacks")
    @Transactional
    public CreatedFeedbackDTO insert(@AuthenticationPrincipal UserDetails loggedUser,
                                     @PathVariable Long id, @RequestBody @Valid NewFeedbackForm newFeedbackForm) {

        User consumer = (User) loggedUser;

        Product product = entityManager.find(Product.class, id);
        if (product == null) throw new ProductNotFoundException("Produto não encontrado!");

        Feedback feedback = newFeedbackForm.toModel(product, consumer);
        entityManager.persist(feedback);

        return new CreatedFeedbackDTO(feedback);
    }
}