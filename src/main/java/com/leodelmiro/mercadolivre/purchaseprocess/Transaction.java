package com.leodelmiro.mercadolivre.purchaseprocess;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private PurchaseStatus status;

    @NotBlank
    private String transactionGatewayId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    private Instant instant = Instant.now();

    @Deprecated
    public Transaction() {

    }

    public Transaction(@NotNull PurchaseStatus status, @NotBlank String transactionGatewayId, @NotNull @Valid Purchase purchase){
        this.status = status;
        this.transactionGatewayId = transactionGatewayId;
        this.purchase = purchase;
    }

    public boolean successfullyCompleted() {
        return this.status.equals(PurchaseStatus.SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", status=" + status +
                ", transactionGatewayId='" + transactionGatewayId + '\'' +
                ", instant=" + instant +
                '}';
    }
}
