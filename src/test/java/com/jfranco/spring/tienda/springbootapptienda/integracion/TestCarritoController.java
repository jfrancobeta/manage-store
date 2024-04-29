package com.jfranco.spring.tienda.springbootapptienda.integracion;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.jfranco.spring.tienda.springbootapptienda.controller.CarritoController;
import com.jfranco.spring.tienda.springbootapptienda.models.domain.Carrito;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;
import com.jfranco.spring.tienda.springbootapptienda.service.IFacturaService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCarritoController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInventarioService inventarioService;

    @MockBean
    private IClienteService clienteService;

    @MockBean
    private IFacturaService facturaService;

    @InjectMocks
    private CarritoController carritoController;

    @Test
    void testCarritoCompras() throws Exception {
        mockMvc.perform(get("/carrito/formCompra").with(user("username").password("password").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("carritocompras"))
                .andExpect(model().attributeExists("productos"))
                .andExpect(model().attributeExists("carrito"))
                .andExpect(model().attributeExists("clientes"))
                .andExpect(model().attributeExists("listaCarrito"));
    }

    @Test
    void testAgregar() throws Exception {
        // Setup
        Inventario producto = new Inventario();
        producto.setId(1L);
        producto.setCantidad(10);
        producto.setPrecio(20.0f);

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Carrito carrito = new Carrito();
        carrito.setProducto(producto);
        carrito.setCantidad(5);
        carrito.setCliente(cliente);

        when(clienteService.findOne(anyLong())).thenReturn(cliente);
        when(inventarioService.findOne(anyLong())).thenReturn(producto);

        mockMvc.perform(post("/carrito/formCompra").with(user("username").password("password").roles("USER"))
                .flashAttr("carrito", carrito))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("formCompra"));
    }

    // @Test
    // void testAgregarcantidadError() throws Exception {
    // // Setup
    // Inventario producto = new Inventario();
    // producto.setId(1L);
    // producto.setCantidad(10);
    // producto.setPrecio(20.0f);

    // Cliente cliente = new Cliente();
    // cliente.setId(1L);

    // Carrito carrito = new Carrito();
    // carrito.setProducto(producto);
    // carrito.setCantidad(16);
    // carrito.setCliente(cliente);

    // mockMvc.perform(post("/carrito/formCompra").with(user("username").password("password").roles("USER"))
    // .flashAttr("carrito", carrito))
    // .andExpect(view().name("carritocompras"));
    // }

    @Test
    void testAgregarErrorproducto() throws Exception {
        // Setup
        Carrito carrito = new Carrito();
        carrito.setProducto(null);
        carrito.setCantidad(null);
        carrito.setCliente(null);

        mockMvc.perform(post("/carrito/formCompra").with(user("username").password("password").roles("USER"))
                .flashAttr("carrito", carrito))
                .andExpect(model().attributeExists("errorProducto"))
                .andExpect(model().attributeExists("errorCliente"))
                .andExpect(model().attributeExists("errorCantidad"))
                .andExpect(view().name("carritocompras"));
    }

    @Test
    public void testFinalizar() throws Exception {
        Factura factura = new Factura();
        factura.setCliente(1L);
        factura.setLista("1");
        factura.setTotal(100.0);
        List<Carrito> items = new ArrayList<>();
        // Agregamos valores a la lista items
        Carrito carrito = new Carrito();
        Inventario producto = new Inventario(1L,"juan","sdjsd3",193.4F,"dfdfj",20);
        Cliente cliente = new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890");
        cliente.setId(1L);
        carrito.setProducto(producto);
        carrito.setCliente(cliente);
        carrito.setCantidad(4);
        items.add(carrito);

        carritoController.setItems(items);
        List<String> productos = new ArrayList<>();
        productos.add("1");
        carritoController.setProductos(productos);
        carritoController.setId_cliente(1l);

        when(inventarioService.findOne(anyLong())).thenReturn(producto);

        mockMvc.perform(get("/carrito/finalizar").with(user("username").password("password").roles("USER")))
                .andExpect(redirectedUrl("formCompra"))
                .andExpect(flash().attribute("error", Matchers.nullValue()));

        
        // assertEquals(0, carritoController.getItems().size());
        // assertEquals(0, carritoController.getProductos().size());
        // assertEquals(null, carritoController.getId_cliente());
        // assertEquals(0.0, carritoController.getTotal());
    }

    @Test
    public void testFinalizarError() throws Exception {
        Factura factura = new Factura();
        factura.setCliente(1L);
        factura.setLista("1");
        factura.setTotal(100.0);
        List<Carrito> items = new ArrayList<>();
        // Agregamos valores a la lista items
        Carrito carrito = new Carrito();
        Inventario producto = new Inventario(1L,"juan","sdjsd3",193.4F,"dfdfj",20);
        Cliente cliente = new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890");
        cliente.setId(1L);
        carrito.setProducto(producto);
        carrito.setCliente(cliente);
        carrito.setCantidad(4);
        items.add(carrito);

        carritoController.setItems(items);
        List<String> productos = new ArrayList<>();
        productos.add("1");
        carritoController.setProductos(productos);

        when(inventarioService.findOne(anyLong())).thenReturn(producto);

        mockMvc.perform(get("/carrito/finalizar").with(user("username").password("password").roles("USER")))
                .andExpect(redirectedUrl("formCompra"))
                .andExpect(flash().attribute("error", Matchers.containsString("Error pruducto o cliente vacio")));

        
        
    }

}
