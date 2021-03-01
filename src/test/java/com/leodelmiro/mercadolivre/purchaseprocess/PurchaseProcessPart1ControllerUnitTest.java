package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.fakeendpoints.Emails;
import com.leodelmiro.mercadolivre.newcategory.Category;
import com.leodelmiro.mercadolivre.newproduct.NewSpecificForm;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.CleanPassword;
import com.leodelmiro.mercadolivre.newuser.User;
import com.leodelmiro.mercadolivre.utils.builders.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class PurchaseProcessPart1ControllerUnitTest {

    @Mock
    private User user;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Emails emails;

    @InjectMocks
    private PurchaseProcessPart1Controller controller;

    private List<NewSpecificForm> specifics;
    private Category category;
    private User owner;
    private UriComponentsBuilder uriComponentsBuilder;

    @BeforeEach
    void init() {
        specifics = List.of(new NewSpecificForm("caracteristica1", "descricao"),
                new NewSpecificForm("caracteristica2", "descricao"),
                new NewSpecificForm("caracteristica3", "descricao"));
        category = new Category("categoria");
        owner = new UserBuilder().withEmail("leonardo@email.com").withPassword("123456").build();

        uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");
    }

    @Test
    @DisplayName("Deve redirecionar para gateway em caso de estoque disponível")
    @WithMockUser("email@email.com")
    public void shouldRedirectToGatewayWhenStockAvailable() throws Exception {
        Product product = new Product("nome", BigDecimal.TEN, 1L, "descricao", category, owner, specifics);

        Mockito.when(entityManager.find(Product.class, 1L)).thenReturn(product);

        Mockito.doAnswer(invocation -> {
            Purchase savedPurchase = invocation.<Purchase>getArgument(0);
            ReflectionTestUtils.setField(savedPurchase, "id", 1L);
            return null;
        }).when(entityManager).persist(any(Purchase.class));

        NewPurchaseForm form = new NewPurchaseForm(1L, 1L, PaymentGateway.PAGSEGURO);
        String url = controller.purchaseProcess(user, form, uriComponentsBuilder);
        Assertions.assertEquals("pagseguro.com/1?redirectUrl=http://localhost:8080/retorno-pagseguro/1", url);

        ArgumentCaptor<Purchase> purchaseArgumentCaptor = ArgumentCaptor.forClass(Purchase.class);
        Mockito.verify(emails).newPurchase(purchaseArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando estoque não estiver disponível")
    @WithMockUser("email@email.com")
    public void shouldThrowsBindExceptionWhenStockUnavailable() {
        Product product = new Product("nome", BigDecimal.TEN, 1L, "descricao", category, owner, specifics);

        Mockito.when(entityManager.find(Product.class, 1L)).thenReturn(product);

        NewPurchaseForm form = new NewPurchaseForm(2L, 1L, PaymentGateway.PAGSEGURO);

        Assertions.assertThrows(BindException.class, () -> {
            controller.purchaseProcess(user, form, uriComponentsBuilder);
        });

        Mockito.verify(emails, never()).newPurchase(any());
    }
}
