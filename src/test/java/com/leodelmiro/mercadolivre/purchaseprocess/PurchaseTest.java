package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.newcategory.Category;
import com.leodelmiro.mercadolivre.newproduct.NewSpecificForm;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import com.leodelmiro.mercadolivre.utils.builders.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class PurchaseTest {

    private Purchase newPurchase;

    @BeforeEach
    void init() {
        List<NewSpecificForm> specifics = List.of(
                new NewSpecificFormBuilder().withName("caracteristica1").withDescription("descricao").build(),
                new NewSpecificFormBuilder().withName("caracteristica2").withDescription("descricao").build(),
                new NewSpecificFormBuilder().withName("caracteristica3").withDescription("descricao").build());
        Category category = new CategoryBuilder().withName("categoria").build();
        User purchaser = new UserBuilder().withEmail("leonardo@email.com").withPassword("123456").build();

        Product chosenProduct = new ProductBuilder().withName("test").withPrice(BigDecimal.TEN).withQuantity(100L)
                .withDescription("descricao").withCategory(category).withOwner(purchaser).withSpecifics(specifics).build();


        newPurchase = new PurchaseBuilder().withChosenProduct(chosenProduct).withQuantity(100L).withPurchaser(purchaser)
                .withPaymentGateway(PaymentGateway.PAGSEGURO).withPurchaseStatus(PurchaseStatus.STARTED).build();
    }

    @Test
    @DisplayName("deve adicionar uma transação")
    void shouldAddTransaction() {

        ReturnPaymentGateway returnPaymentGateway = purchase -> {
            return new Transaction(PurchaseStatus.SUCCESS, "1", purchase);
        };

        newPurchase.addTransaction(returnPaymentGateway);
    }

    @Test
    @DisplayName("não pode aceitar uma transação igual")
    void shouldThrowsIllegalArgumentExceptionWhenEqualsTransaction() {

        ReturnPaymentGateway returnPaymentGateway = purchase -> {
            return new Transaction(PurchaseStatus.FAIL, "1", purchase);
        };

        newPurchase.addTransaction(returnPaymentGateway);


        ReturnPaymentGateway returnPaymentGateway2 = purchase -> {
            return new Transaction(PurchaseStatus.FAIL, "1", purchase);
        };

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            newPurchase.addTransaction(returnPaymentGateway2);
        });
    }

    @Test
    @DisplayName("não pode aceitar uma transação se o status já for de sucesso")
    void shouldThrowsIllegalArgumentExceptionWhenTransactionStatusAlreadySuccess() {

        ReturnPaymentGateway returnPaymentGateway = purchase -> {
            return new Transaction(PurchaseStatus.SUCCESS, "1", purchase);
        };

        newPurchase.addTransaction(returnPaymentGateway);


        ReturnPaymentGateway returnPaymentGateway2 = purchase -> {
            return new Transaction(PurchaseStatus.SUCCESS, "2", purchase);
        };

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            newPurchase.addTransaction(returnPaymentGateway2);
        });
    }

    @ParameterizedTest
    @DisplayName("deveria verificar se uma compra foi concluída com sucesso")
    @MethodSource("testToPurchaseSuccessfullyProcessedValues")
    void shouldVerifyIfPurchaseSuccessfullyProcessed(boolean expectedResult, Collection<ReturnPaymentGateway> returnsMethod) {
        returnsMethod.forEach(returnMethod -> newPurchase.addTransaction(returnMethod));

        Assertions.assertEquals(expectedResult, newPurchase.successfullyProcessed());
    }

    private static Stream<Arguments> testToPurchaseSuccessfullyProcessedValues() {
        ReturnPaymentGateway successReturn = (purchase -> {
            return new Transaction(PurchaseStatus.SUCCESS, "1", purchase);
        });

        ReturnPaymentGateway failReturn = (purchase -> {
            return new Transaction(PurchaseStatus.FAIL, "1", purchase);
        });

        return Stream.of(
                Arguments.of(true, List.of(successReturn)),
                Arguments.of(false, List.of(failReturn)),
                Arguments.of(false, List.of())
        );
    }

}
