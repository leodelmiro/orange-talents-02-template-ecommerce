package com.leodelmiro.mercadolivre.newfeedback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.mercadolivre.utils.builders.NewFeedbackFormBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewFeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private NewFeedbackForm newFeedbackForm;

    @Test
    @DisplayName("Deve retornar 200 quando tudo OK")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn200() throws Exception {
        newFeedbackForm = new NewFeedbackFormBuilder().withTitle("Muito bom!").withDescription("Bom mesmo!").withRating(5).build();
        String jsonContent = objectMapper.writeValueAsString(newFeedbackForm);

        mockMvc.perform(post("/products/1/feedbacks")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar 400 quando id do produto não existir")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn400WhenProductIDDoesNotExists() throws Exception {
        newFeedbackForm = new NewFeedbackFormBuilder().withTitle("Muito bom!").withDescription("Bom mesmo!").withRating(5).build();
        String jsonContent = objectMapper.writeValueAsString(newFeedbackForm);

        mockMvc.perform(post("/products/100/feedbacks")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando for passado algum atributo inválido")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn400WhenInvalidAttribute() throws Exception {
        newFeedbackForm = new NewFeedbackFormBuilder().withTitle("").withDescription("Bom mesmo!").withRating(5).build();
        String jsonContent = objectMapper.writeValueAsString(newFeedbackForm);

        mockMvc.perform(post("/products/1/feedbacks")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
