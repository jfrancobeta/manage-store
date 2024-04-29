package com.jfranco.spring.tienda.springbootapptienda.integracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jfranco.spring.tienda.springbootapptienda.models.domain.FacturaDetallada;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;
import com.jfranco.spring.tienda.springbootapptienda.service.IFacturaService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest 
@AutoConfigureMockMvc
public class TestFacturaController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFacturaService facturaService;

    @MockBean
    private IClienteService clienteService;

    @MockBean
    private IInventarioService inventarioService;

    @Test
    public void TestFacturas() throws Exception {
        // Simula una lista de facturas
        Factura factura1 = new Factura(1L, 1L, "1,2,3", 300.0, new Date());
        Factura factura2 = new Factura(2L, 1L, "1,2", 250.0, new Date());
        Factura[] facturas = {factura1, factura2};

        // Simula una lista de clientes
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNombre("Cliente 1");
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("Cliente 2");
        Mockito.when(clienteService.findOne(1L)).thenReturn(cliente1);
        Mockito.when(clienteService.findOne(2L)).thenReturn(cliente2);

        // Simula una lista de productos en inventario
        Inventario producto1 = new Inventario();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        Inventario producto2 = new Inventario();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");
        Inventario producto3 = new Inventario();
        producto3.setId(3L);
        producto3.setNombre("Producto 3");
        Inventario producto4 = new Inventario();
        producto4.setId(4L);
        producto4.setNombre("Producto 4");
        Inventario producto5 = new Inventario();
        producto5.setId(5L);
        producto5.setNombre("Producto 5");

        Mockito.when(inventarioService.findOne(1L)).thenReturn(producto1);
        Mockito.when(inventarioService.findOne(2L)).thenReturn(producto2);
        Mockito.when(inventarioService.findOne(3L)).thenReturn(producto3);
        Mockito.when(inventarioService.findOne(4L)).thenReturn(producto4);
        Mockito.when(inventarioService.findOne(5L)).thenReturn(producto5);

        Mockito.when(facturaService.findAll()).thenReturn(Arrays.asList(facturas));

        // Crea un HashMap de facturas detalladas
        Map<Long, FacturaDetallada> facturaTotal = new HashMap<>();
        for (Factura factura : facturas) {
            // Obtener el cliente
            Cliente cliente = clienteService.findOne(factura.getCliente());
            // Crear lista de productos
            List<Inventario> productos = new ArrayList<>(); 
            String lista =  factura.getLista();
            String[] elementos = lista.split(",");
            for (String productoid : elementos) {
                Inventario producto = inventarioService.findOne(Long.parseLong(productoid));
                productos.add(producto);
            }
            // Crear FacturaDetallada
            FacturaDetallada facturaDetallada = new FacturaDetallada();
            facturaDetallada.setCliente(cliente);
            facturaDetallada.setProductos(productos);
            facturaDetallada.setTotal(factura.getTotal());
            facturaDetallada.setFechaCreacion(factura.getFechaCreacion());
            // Agregar facturaDetallada al HashMap
            facturaTotal.put(factura.getId(), facturaDetallada);
        }

        mockMvc.perform(get("/factura/listarFacturas").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("ListarFacturas"))
                .andExpect(model().attributeExists("facturas"))
                .andExpect(model().attribute("facturas", Matchers.aMapWithSize(2)))
                .andExpect(model().attribute("facturas", Matchers.equalTo(facturaTotal)));


    }

}
