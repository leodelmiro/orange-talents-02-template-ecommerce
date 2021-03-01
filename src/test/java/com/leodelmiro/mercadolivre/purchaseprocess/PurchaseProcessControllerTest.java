package com.leodelmiro.mercadolivre.purchaseprocess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.mercadolivre.utils.builders.NewPurchaseFormBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseProcessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Deve redirecionar para gateway em caso de estoque disponível")
    @ParameterizedTest
    @CsvSource({"1", "199"})
    @WithUserDetails("email@email.com")
    public void shouldReturn200WhenStockAvailable(long quantity) throws Exception {
        NewPurchaseForm newPurchaseForm = new NewPurchaseFormBuilder().withQuantity(quantity)
                .withProductId(1L).withGateway(PaymentGateway.PAGSEGURO).build();
        String jsonBody = objectMapper.writeValueAsString(newPurchaseForm);

        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());
    }

    @DisplayName("Deve lançar uma exceção quando estoque não estiver disponível")
    @ParameterizedTest
    @CsvSource({"201", "0"})
    @WithUserDetails("email@email.com")
    public void shouldReturn400WhenStockUnavailable(long quantity) throws Exception {
        NewPurchaseForm newPurchaseForm = new NewPurchaseFormBuilder().withQuantity(quantity)
                .withProductId(1L).withGateway(PaymentGateway.PAGSEGURO).build();
        String jsonBody = objectMapper.writeValueAsString(newPurchaseForm);

        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }
}
