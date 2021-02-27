package com.leodelmiro.mercadolivre.showdetailspage;

import com.leodelmiro.mercadolivre.common.validation.ProductNotFoundException;
import com.leodelmiro.mercadolivre.newproduct.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("/products")
public class ProductDetailsPageController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public ProductDetailsPageDTO findProductById(@PathVariable Long id) {
        Product chosenProduct = entityManager.find(Product.class, id);
        if (chosenProduct == null) throw new ProductNotFoundException("Id informado não existe");

        return new ProductDetailsPageDTO(chosenProduct);
    }
}
