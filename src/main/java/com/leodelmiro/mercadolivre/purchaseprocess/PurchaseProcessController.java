package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.common.Emails;
import com.leodelmiro.mercadolivre.common.validation.ProductNotFoundException;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/purchases")
public class PurchaseProcessController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Emails emails;

    @PostMapping
    @Transactional
    public String purchaseProcess(@AuthenticationPrincipal User loggedUser,
                                  @RequestBody @Valid NewPurchaseForm newPurchaseForm,
                                  UriComponentsBuilder uriComponentsBuilder) throws BindException {

        Product productToPurchase = entityManager.find(Product.class, newPurchaseForm.getProductId());
        if (productToPurchase == null) throw new ProductNotFoundException("Id do produto não existe!");

        long quantity = newPurchaseForm.getQuantity();
        boolean reduced = productToPurchase.reduceStock(quantity);

        if (reduced) {
            PaymentGateway gateway = newPurchaseForm.getGateway();
            Purchase newPurchase = new Purchase(productToPurchase, quantity, loggedUser, gateway, PurchaseStatus.STARTED);
            entityManager.persist(newPurchase);
            emails.newPurchase(newPurchase);

            return newPurchase.redirectUrl(uriComponentsBuilder);
        }

        BindException stockProblem = new BindException(newPurchaseForm, "NewPurchaseForm");
        stockProblem.reject(null, "Não foi possível realizar a compra por conta do estoque!");
        throw stockProblem;
    }
}
