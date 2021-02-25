package com.leodelmiro.mercadolivre.newproduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.mercadolivre.utils.builders.NewProductFormBuilder;
import com.leodelmiro.mercadolivre.utils.builders.NewSpecificFormBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private NewSpecificForm newSpecificForm1;

    @BeforeEach
    void init() throws Exception {
        newSpecificForm1 = new NewSpecificFormBuilder().withName("Qualidade").withDescription("Muito Boa").build();
    }

    @Test
    @DisplayName("Deve retornar 200 quando tudo OK")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn200WhenProductValid() throws Exception {
        NewSpecificForm newSpecificForm2 = new NewSpecificFormBuilder().withName("Tamanho").withDescription("Quase um relógio do faustão").build();
        NewSpecificForm newSpecificForm3 = new NewSpecificFormBuilder().withName("Preço").withDescription("Cabe no seu bolso").build();
        List<NewSpecificForm> specifics = Arrays.asList(newSpecificForm1, newSpecificForm2, newSpecificForm3);
        NewProductForm newProductForm = new NewProductFormBuilder().withName("Produto teste").withPrice(200.0).withQuantity(200L)
                .withDescription("Só comprar e levar").withCategoryId(1L).withSpecifics(specifics).build();

        String jsonBody = objectMapper.writeValueAsString(newProductForm);

        ResultActions resultActions = mockMvc.perform(post("/api/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando nome de caracteristica for repetido")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn400WhenSpecificsHasEqualsName() throws Exception {
        NewSpecificForm newSpecificForm2 = new NewSpecificFormBuilder().withName("Tamanho").withDescription("Quase um relógio do faustão").build();
        NewSpecificForm newSpecificForm3 = new NewSpecificFormBuilder().withName("Tamanho").withDescription("Cabe no seu bolso").build();
        List<NewSpecificForm> specifics = Arrays.asList(newSpecificForm1, newSpecificForm2, newSpecificForm3);
        NewProductForm newProductForm = new NewProductFormBuilder().withName("Produto teste").withPrice(200.0).withQuantity(200L)
                .withDescription("Só comprar e levar").withCategoryId(1L).withSpecifics(specifics).build();

        String jsonBody = objectMapper.writeValueAsString(newProductForm);

        ResultActions resultActions = mockMvc.perform(post("/api/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
    }

}
