package com.leodelmiro.mercadolivre.newquestion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.mercadolivre.utils.builders.NewQuestionFormBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class NewQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private NewQuestionForm newQuestionForm;

    @Test
    @DisplayName("Deve retornar 200 quando tudo OK")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn200() throws Exception {
        newQuestionForm = new NewQuestionFormBuilder().withTitle("Teria para pronta entrega?").build();
        String jsonContent = objectMapper.writeValueAsString(newQuestionForm);

        mockMvc.perform(post("/products/1/questions")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar 400 quando id do produto não existir")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn400WhenProductIDDoesNotExists() throws Exception {
        newQuestionForm = new NewQuestionFormBuilder().withTitle("Teria para pronta entrega?").build();
        String jsonContent = objectMapper.writeValueAsString(newQuestionForm);

        mockMvc.perform(post("/products/100/questions")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando for passado algum título vazio")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn400WhenInvalidAttribute() throws Exception {
        newQuestionForm = new NewQuestionFormBuilder().withTitle("").build();
        String jsonContent = objectMapper.writeValueAsString(newQuestionForm);

        mockMvc.perform(post("/products/1/questions")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
