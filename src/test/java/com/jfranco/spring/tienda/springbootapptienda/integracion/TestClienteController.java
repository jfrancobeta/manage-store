package com.jfranco.spring.tienda.springbootapptienda.integracion;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;

@SpringBootTest// las pruebas de integracion adiferencia de las pruebas unitarias se realizan para test todas las capas
@AutoConfigureMockMvc 
public class TestClienteController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClienteService clienteService;

    @Test
    public void testListarClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(
             new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890"),
             new Cliente("María", "López", "0987654321", "maria@example.com", "Avenida 456", "0987654321")
        );

         Mockito.when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/cliente/listarclientes").with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("ListarClientes"))
        .andExpect(model().attribute("clientes", Matchers.hasSize(2)))
        .andExpect(model().attribute("clientes",Matchers.containsInAnyOrder(clientes.toArray())));


    }

    @Test
    public void testListarClientesQuery() throws Exception{
        List<Cliente> resultados = Arrays.asList(
            new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890"),
            new Cliente("María", "López", "0987654321", "maria@example.com", "Avenida 456", "0987654321")
        );
        Mockito.when(clienteService.search(anyString(), anyString())).thenReturn(resultados);

        mockMvc.perform(get("/cliente/listarclientes").param("query", "juan").with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("ListarClientes"))
        .andExpect(model().attribute("clientes", Matchers.is(resultados)));

    }

    @Test
    public void testFormCliente() throws Exception{
        mockMvc.perform(get("/cliente/formClientes").with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("formCliente"))
        .andExpect(model().attributeExists("titulo"))
        .andExpect(model().attribute("titulo", Matchers.is("crear cliente")))
        .andExpect(model().attributeExists("cliente"))
        .andExpect(model().attribute("cliente", Matchers.instanceOf(Cliente.class)));

    }

    @Test
    public void guardarClienteConError() throws Exception{
        Cliente cliente = new Cliente();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/cliente/formClientes").with(user("admin").password("1234").roles("ADMIN"))
        .flashAttr("cliente", cliente)
        .flashAttr("result", result))
        .andExpect(status().isOk())
        .andExpect(view().name("formCliente"));
        
    }

    @Test
    public void testGuardarCliente_existingCedula()throws Exception{
        Cliente cliente = new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890");
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(clienteService.FindbyCedula(anyString())).thenReturn(cliente);

        mockMvc.perform(post("/cliente/formClientes").flashAttr("cliente", cliente).flashAttr("result", result).param("cedula", cliente.getCedula() ).with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("formCliente"))
        .andExpect(model().attributeExists("errorCreado"));
    }

    @Test
    public void testGuardarCliente_Seccessful() throws Exception{
        Cliente cliente = new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890");
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(clienteService.FindbyCedula(anyString())).thenReturn(null);

        mockMvc.perform(post("/cliente/formClientes").flashAttr("cliente", cliente).flashAttr("result", result).with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("listarclientes"));
    }

    @Test
    public void testEditarCliente() throws Exception{
        Cliente cliente = new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890");
        cliente.setId(1L);

        when(clienteService.findOne(1L)).thenReturn(cliente);

        mockMvc.perform(get("/cliente/editar/1").with(user("admin").password("1234").roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("formCliente"))
        .andExpect(model().attribute("cliente", cliente))
        .andExpect(model().attribute("titulo", "Editar cliente"));
    }
    
}
