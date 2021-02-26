package com.leodelmiro.mercadolivre.newproduct;

import com.leodelmiro.mercadolivre.common.validation.FileCantBeEmptyException;
import com.leodelmiro.mercadolivre.common.validation.ProductNotFoundException;
import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class NewImageController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Uploader uploaderFake;

    @PostMapping(value = "/{id}/images")
    @Transactional
    public InsertedProductImageDTO insertImages(
            @AuthenticationPrincipal UserDetails loggedUser, @PathVariable Long id, @Valid NewImagesForm newImagesForm) {
        User owner = (User) loggedUser;
        Product product = entityManager.find(Product.class, id);

        if (product == null) throw new ProductNotFoundException("Produto não encontrado!");
        if (!product.belongsToUser(owner)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        if (newImagesForm.hasNullFile()) throw new FileCantBeEmptyException("Arquivo de imagem não pode ser vazio!");

        Set<String> links = uploaderFake.send(newImagesForm.getImages());
        product.associateImages(links);

        entityManager.merge(product);

        return new InsertedProductImageDTO(product);
    }
}
