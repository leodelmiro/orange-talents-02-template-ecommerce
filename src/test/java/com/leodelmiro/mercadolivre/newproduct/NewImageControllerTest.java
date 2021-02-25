package com.leodelmiro.mercadolivre.newproduct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewImageControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private MockMultipartFile image;

    @Test
    @DisplayName("Deve retornar 200 quando tudo OK")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn200WhenImageOk() throws Exception {
        image = new MockMultipartFile("images", "image1", null, (InputStream) null);

        mockMvc.perform(multipart("/products/1/images")
                .file(image))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 400 quando arquivo for vazio")
    @WithUserDetails("email@email.com")
    public void insertShouldReturn400WhenFileIsEmpty() throws Exception {
        image = new MockMultipartFile("images", null, null, (InputStream) null);

        mockMvc.perform(multipart("/products/1/images")
                .file(image))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 403 quando usuário não for o dono do produto")
    @WithUserDetails("test@email.com")
    public void insertShouldReturn403WhenIsNotTheProductOwner() throws Exception {
        image = new MockMultipartFile("images", null, null, (InputStream) null);

        mockMvc.perform(multipart("/products/1/images")
                .file(image))
                .andExpect(status().isForbidden());
    }
}
