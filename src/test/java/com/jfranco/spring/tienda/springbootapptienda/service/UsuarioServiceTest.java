package com.jfranco.spring.tienda.springbootapptienda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IUsuarioDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Rol;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Usuario;
@SpringBootTest
public class UsuarioServiceTest {
    @Mock
    private IUsuarioDao usuarioDaoMock;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        // Configurar el comportamiento simulado para el mock de IUsuarioDao
        Usuario usuario = new Usuario("usuario1", "contraseña", List.of(new Rol("ROLE_USER")));
        when(usuarioDaoMock.findByUsername("usuario1")).thenReturn(usuario);
    }

    @Test
    public void testLoadUserByUsername() {
        UserDetails userDetails = usuarioService.loadUserByUsername("usuario1");
        assertNotNull(userDetails);
        assertEquals("usuario1", userDetails.getUsername());
        assertEquals("contraseña", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }
}
