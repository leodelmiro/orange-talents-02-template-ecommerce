package com.leodelmiro.mercadolivre.newcategory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.mercadolivre.utils.builders.NewCategoryFormBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deveria cadastrar categoria")
    @WithUserDetails("email@email.com")
    public void insertShouldReturnOkWhenCreated() throws Exception {
        NewCategoryForm newCategory = new NewCategoryFormBuilder().withName("Eletrônicos").withMotherCategoryId(1L).build();
        String jsonBody = objectMapper.writeValueAsString(newCategory);

        ResultActions resultActions = mockMvc.perform(post("/categories")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria retornar BadRequest com nome já existente ")
    @WithUserDetails("email@email.com")
    public void insertShouldReturnBadRequestWhenNameAlreadyExists() throws Exception {
        NewCategoryForm newCategory = new NewCategoryFormBuilder().withName("Geral").withMotherCategoryId(1L).build();
        String jsonBody = objectMapper.writeValueAsString(newCategory);

        ResultActions resultActions = mockMvc.perform(post("/categories")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deveria retornar BadRequest com Id de categoria mão não existente ")
    @WithUserDetails("email@email.com")
    public void insertShouldReturnBadRequestWhenMotherCategoryIdInvalid() throws Exception {
        NewCategoryForm newCategory = new NewCategoryFormBuilder().withName("Geral").withMotherCategoryId(3L).build();
        String jsonBody = objectMapper.writeValueAsString(newCategory);

        ResultActions resultActions = mockMvc.perform(post("/categories")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
    }
}
