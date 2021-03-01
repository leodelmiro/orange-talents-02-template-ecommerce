package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_purchases")
public class Purchase {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    @Valid
    private Product chosenProduct;

    @Positive
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "purchaser_id")
    @NotNull
    @Valid
    private User purchaser;

    @Enumerated
    @NotNull
    PaymentGateway gateway;

    @NotNull
    PurchaseStatus status;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

    @Deprecated
    public Purchase() {
    }

    public Purchase(@NotNull @Valid Product chosenProduct, @Positive long quantity,
                    @NotNull @Valid User purchaser, @NotNull PaymentGateway gateway,
                    @NotNull PurchaseStatus status) {
        Assert.notNull(chosenProduct, "Produto não pode ser nulo!");
        Assert.state(quantity > 0, "Quantidade deve ser maior que 0!");
        Assert.notNull(purchaser, "Comprador não pode ser nulo!");
        Assert.notNull(gateway, "gateway não pode ser nulo!");
        Assert.notNull(status, "Status da compra não pode ser nulo!");

        this.chosenProduct = chosenProduct;
        this.quantity = quantity;
        this.purchaser = purchaser;
        this.gateway = gateway;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Product getChosenProduct() {
        return chosenProduct;
    }

    public Long getQuantity() {
        return quantity;
    }

    public User getPurchaser() {
        return purchaser;
    }

    public User getProductOwner() {
        return getChosenProduct().getOwner();
    }

    public String redirectUrl(
            UriComponentsBuilder uriComponentsBuilder) {
        return this.gateway.createUrlReturn(this, uriComponentsBuilder);
    }

    public void addTransaction(@Valid ReturnPaymentGateway request) {
        Transaction newTransaction = request.toTransaction(this);
        Assert.isTrue(!this.transactions.contains(newTransaction), "Já existe uma transação igual a essa!");

        Assert.isTrue(!successfullyProcessed(), "Essa compra já foi concluída com sucesso");

        this.transactions.add(newTransaction);
    }

    private Set<Transaction> successfullyCompletedTransactions() {
        Set<Transaction> successfullyCompletedTransactions =
                this.transactions.stream().filter(Transaction::successfullyCompleted).collect(Collectors.toSet());

        Assert.isTrue(successfullyCompletedTransactions.size() <= 1, "Não deve ter mais de uma transação concluída com sucesso!");

        return successfullyCompletedTransactions;
    }

    public boolean successfullyProcessed() {
        return !successfullyCompletedTransactions().isEmpty();
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", chosenProduct=" + chosenProduct +
                ", quantity=" + quantity +
                ", purchaser=" + purchaser +
                ", gateway=" + gateway +
                ", status=" + status +
                ", transactions=" + transactions +
                '}';
    }
}
