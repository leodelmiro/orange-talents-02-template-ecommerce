package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.fakeendpoints.Emails;
import com.leodelmiro.mercadolivre.newcategory.Category;
import com.leodelmiro.mercadolivre.newproduct.NewSpecificForm;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import com.leodelmiro.mercadolivre.utils.builders.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class NewPurchaseEventsTest {

    @Mock
    private SuccessPurchaseEvent successPurchaseEvent;

    @Mock
    private Emails emails;

    @InjectMocks
    private NewPurchaseEvents newPurchaseEvents;

    private Product chosenProduct;
    private User purchaser;
    private Purchase purchasedCompleted;

    @BeforeEach
    void init() {
        List<NewSpecificForm> specifics = List.of(
                new NewSpecificFormBuilder().withName("caracteristica1").withDescription("descricao").build(),
                new NewSpecificFormBuilder().withName("caracteristica2").withDescription("descricao").build(),
                new NewSpecificFormBuilder().withName("caracteristica3").withDescription("descricao").build());
        Category category = new CategoryBuilder().withName("categoria").build();
        purchaser = new UserBuilder().withEmail("email@email.com").withPassword("123456").build();

        chosenProduct = new ProductBuilder().withName("test").withPrice(BigDecimal.TEN).withQuantity(100L)
                .withDescription("descricao").withCategory(category).withOwner(purchaser).withSpecifics(specifics).build();
    }

    @Test
    @DisplayName("deveria disparar eventos de sucesso")
    void shouldProcessSuccessPurchaseEvents() {
        purchasedCompleted =
                new PurchaseBuilder().withChosenProduct(chosenProduct).withQuantity(1L).withPurchaser(purchaser)
                .withPaymentGateway(PaymentGateway.PAGSEGURO).withPurchaseStatus(PurchaseStatus.SUCCESS).buildWithTransaction();

        Set<SuccessPurchaseEvent> events = Set.of(successPurchaseEvent);
        newPurchaseEvents = new NewPurchaseEvents(events, emails);

        newPurchaseEvents.process(purchasedCompleted);

        Mockito.verify(successPurchaseEvent).process(purchasedCompleted);
    }

    @Test
    @DisplayName("deveria disparar eventos de falha")
    void shouldSendEmailWhenPurchaseAttemptFail() {
        Purchase purchasedFailed =
                new PurchaseBuilder().withChosenProduct(chosenProduct).withQuantity(100L).withPurchaser(purchaser)
                .withPaymentGateway(PaymentGateway.PAGSEGURO).withPurchaseStatus(PurchaseStatus.FAIL).build();

        newPurchaseEvents.process(purchasedFailed);

        Mockito.verify(emails).failPayment(purchasedFailed);
    }

}
