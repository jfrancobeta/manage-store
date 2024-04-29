package com.jfranco.spring.tienda.springbootapptienda.integracion;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IAudit;

@SpringBootTest
@AutoConfigureMockMvc
public class TestAuditController {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAudit auditService;

    @Test
    public void testAuditCliente() throws Exception {
        // Simula los datos de auditoría del cliente
        List<Object[]> auditClienteData = Arrays.asList(
                new Object[]{1L, "Nombre 1", "Apellido 1", "123456", "correo1@example.com", "Dirección 1", "111-111-1111", new Date(), "Usuario 1"},
                new Object[]{2L, "Nombre 2", "Apellido 2", "654321", "correo2@example.com", "Dirección 2", "222-222-2222", new Date(), "Usuario 2"}
        );

        when(auditService.findAllAuditCliente()).thenReturn(auditClienteData);

        mockMvc.perform(get("/audit/auditCliente").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("AuditCliente"))
                .andExpect(model().attributeExists("auditCliente"))
                .andExpect(model().attribute("auditCliente", auditClienteData));
    }

    @Test
    public void testAuditInventario() throws Exception {
        // Simula los datos de auditoría del inventario
        List<Object[]> auditInventarioData = Arrays.asList(
                new Object[]{1L, "Producto 1", 100.0, "123ABC", "Detalles 1", 10, new Date(), "Usuario 1"},
                new Object[]{2L, "Producto 2", 150.0, "456DEF", "Detalles 2", 20, new Date(), "Usuario 2"}
        );

        when(auditService.findAllAuditInventario()).thenReturn(auditInventarioData);

        mockMvc.perform(get("/audit/auditInventario").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("AuditInventario"))
                .andExpect(model().attributeExists("auditInventario"))
                .andExpect(model().attribute("auditInventario", auditInventarioData));
    }

    @Test
    public void testAuditFactura() throws Exception {
        // Simula los datos de auditoría de las facturas
        List<Object[]> auditFacturaData = Arrays.asList(
                new Object[]{new Cliente("cliente","2","1111539845","juad","calle 24","304101223"), Arrays.asList(new Inventario("Producto 1", "Código1", 100.0f, "Detalles1", 10),
                new Inventario("Producto 2", "Código2", 150.0f, "Detalles2", 15)), 250.0,1L, new Date(), "Usuario 1"},
                new Object[]{new Cliente("cliente","2","1111539845","juad","calle 24","304101223"), Arrays.asList(new Inventario("Producto 1", "Código1", 100.0f, "Detalles1", 10),
                new Inventario("Producto 2", "Código2", 150.0f, "Detalles2", 15)), 300.0,2L,new Date(), "Usuario 2"}
        );

        when(auditService.findAllAuditFactura()).thenReturn(auditFacturaData);

        mockMvc.perform(get("/audit/auditFactura").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("AuditFacturas"))
                .andExpect(model().attributeExists("auditFactura"))
                .andExpect(model().attribute("auditFactura", auditFacturaData));
    }
}
