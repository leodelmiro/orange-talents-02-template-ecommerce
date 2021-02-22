package com.leodelmiro.mercadolivre.newuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.mercadolivre.builders.NewUserFormBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deveria cadastrar usuário")
    public void insertShouldReturnOkWhenCreated() throws Exception {
        NewUserForm newUserForm = new NewUserFormBuilder().withEmail("new@email.com").withPassword("123456").build();
        String jsonBody = objectMapper.writeValueAsString(newUserForm);

        ResultActions resultActions = mockMvc.perform(post("/users")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria retornar BadRequest com email já existente ")
    public void insertShouldReturnBadRequestWhenEmailAlreadyExists() throws Exception {
        NewUserForm newUserWithEmailInUse = new NewUserFormBuilder().withEmail("email@email.com").withPassword("123456").build();
        String jsonBody = objectMapper.writeValueAsString(newUserWithEmailInUse);

        ResultActions resultActions = mockMvc.perform(post("/users")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
    }
}
