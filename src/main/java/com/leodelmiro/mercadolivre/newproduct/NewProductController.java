package com.leodelmiro.mercadolivre.product;

import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class NewProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Uploader uploaderFake;

    @InitBinder(value = "NewProductForm")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new SpecificsNameCantBeEqualsValidator());
    }

    @PostMapping
    @Transactional
    public CreatedProductDTO insert(@AuthenticationPrincipal UserDetails loggedUser, @RequestBody @Valid NewProductForm newProductForm) {
        User creator = (User) loggedUser;
        Product newProduct = newProductForm.toModel(entityManager, creator);

        entityManager.persist(newProduct);

        return new CreatedProductDTO(newProduct);
    }

    @PostMapping(value = "/{id}/images")
    @Transactional
    public void insertImages(@PathVariable Long id, @Valid NewImagesForm newImagesForm) {
        Product product = entityManager.find(Product.class, id);

        Set<String> links = uploaderFake.send(newImagesForm.getImages());

        product.associateImages(links);

        entityManager.merge(product);
    }

}
