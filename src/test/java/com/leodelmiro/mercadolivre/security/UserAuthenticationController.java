package com.leodelmiro.mercadolivre.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leodelmiro.mercadolivre.builders.LoginInputDtoBuilder;
import com.leodelmiro.mercadolivre.common.security.LoginInputDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserAuthenticationController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deveria retornar Ok e um Token quando estiver tudo OK ")
    void shouldReturn200AndTokenWhenValid() throws Exception {
        LoginInputDTO loginInputDTO = new LoginInputDtoBuilder().withEmail("email@email.com").withPassword("123456").build();
        String jsonBody = objectMapper.writeValueAsString(loginInputDTO);

        ResultActions resultActions = mockMvc.perform(post("/api/auth")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/json;charset=UTF-8"));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$").exists());
        resultActions.andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    @DisplayName("Deveria retornar BadRequest ao tentar logar com email que não está cadastrado")
    void shouldReturn400WhenInvalid() throws Exception {
        LoginInputDTO loginInputDTO = new LoginInputDtoBuilder().withEmail("otheremail@email.com").withPassword("123456").build();
        String jsonBody = objectMapper.writeValueAsString(loginInputDTO);

        ResultActions resultActions = mockMvc.perform(post("/api/auth")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/json;charset=UTF-8"));

        resultActions.andExpect(status().isBadRequest());
    }
}
