package com.leodelmiro.mercadolivre.showdetailspage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductDetailsPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deveria retornar detalhe de produtos")
    public void getProductDetailsShouldReturnOk() throws Exception {
        mockMvc.perform(get("/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.imageLinks").exists())
                .andExpect(jsonPath("$.questions").exists())
                .andExpect(jsonPath("$.feedbacks").exists())
                .andExpect(jsonPath("$.ratingAverage").exists())
                .andExpect(jsonPath("$.totalRating").exists());
    }


    @Test
    @DisplayName("Deveria retornar BadRequest com Id de produto não existente ")
    public void insertShouldReturnBadRequestWhenProductIdDoesNotExists() throws Exception {
        mockMvc.perform(get("/products/100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
