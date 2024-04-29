package com.jfranco.spring.tienda.springbootapptienda.integracion;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest 
@AutoConfigureMockMvc
public class TestLoginController {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginTest() throws Exception{

        mockMvc.perform(get("/start/login").with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(view().name("login"));
    }
}
