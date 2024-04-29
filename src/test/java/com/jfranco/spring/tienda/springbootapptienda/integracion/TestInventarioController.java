package com.jfranco.spring.tienda.springbootapptienda.integracion;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestInventarioController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInventarioService inventarioService;

    @Test
    public void testListarInventario() throws Exception {
        List<Inventario> inventarios = Arrays.asList(
                new Inventario(1L,"Producto 1", "123456", 100.0F, "Detalles 1", 10),
                new Inventario(2L,"Producto 2", "789012", 200.0F, "Detalles 2", 20)
        );

        Mockito.when(inventarioService.findAll()).thenReturn(inventarios);

        mockMvc.perform(get("/inventario/ListarInventario").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("ListarInventario"))
                .andExpect(model().attribute("inventarios", Matchers.hasSize(2)))
                .andExpect(model().attribute("inventarios", Matchers.containsInAnyOrder(inventarios.toArray())));
    }

    @Test
    public void testListarInventarioQuery() throws Exception{
        List<Inventario> resultados = Arrays.asList(
            new Inventario(1L,"Producto 1", "123456", 100.0F, "Detalles 1", 10),
            new Inventario(2L,"Producto 2", "789012", 200.0F, "Detalles 2", 20)
        );
        Mockito.when(inventarioService.search(anyString(), anyString())).thenReturn(resultados);

        mockMvc.perform(get("/inventario/ListarInventario").param("query", "pro").with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("ListarInventario"))
        .andExpect(model().attribute("inventarios", Matchers.is(resultados)));

    }

    @Test
    public void testCrearInventarioForm() throws Exception {
        mockMvc.perform(get("/inventario/form").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("formInventario"))
                .andExpect(model().attributeExists("titulo"))
                .andExpect(model().attribute("titulo", "guardar Producto"))
                .andExpect(model().attributeExists("inventario"))
                .andExpect(model().attribute("inventario", Matchers.instanceOf(Inventario.class)));
    }

    @Test
    public void testGuardarInventarioConError() throws Exception {
        Inventario inventario = new Inventario();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/inventario/form").with(user("admin").password("1234").roles("ADMIN"))
                .flashAttr("inventario", inventario)
                .flashAttr("result", result))
                .andExpect(status().isOk())
                .andExpect(view().name("formInventario"));
    }

    @Test
    public void testEliminarInventario() throws Exception {
        mockMvc.perform(get("/inventario/eliminar/1").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventario/ListarInventario"));
    }

    @Test
    public void testEditarInventario() throws Exception {
        Inventario inventario = new Inventario(1L,"Producto 1", "123456", 100.0F, "Detalles 1", 10);

        when(inventarioService.findOne(1L)).thenReturn(inventario);

        mockMvc.perform(get("/inventario/editar/1").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("formInventario"))
                .andExpect(model().attribute("inventario", inventario))
                .andExpect(model().attribute("titulo", "editar Producto"));
    }
}

