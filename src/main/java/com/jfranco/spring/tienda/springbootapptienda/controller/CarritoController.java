package com.jfranco.spring.tienda.springbootapptienda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfranco.spring.tienda.springbootapptienda.models.domain.Carrito;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;
import com.jfranco.spring.tienda.springbootapptienda.service.IFacturaService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;


@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private IInventarioService InventarioService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IFacturaService facturaService;
    
    private List<Carrito> items = new ArrayList<>();
    private List<String> productos = new ArrayList<>();
    private Long id_cliente = null;
    private Double total = 0.0;

    @GetMapping("/formCompra")
    public String carritoCompras(Model model) {
        Carrito carrito = new Carrito();
        model.addAttribute("productos", InventarioService.findAll());
        model.addAttribute("carrito", carrito);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("listaCarrito", items);
        if (id_cliente != null) {
            model.addAttribute("clienteSeleccionado", true);
        }
        return "carritocompras";
    }

    @PostMapping("/formCompra")
    public String Agregar(Carrito carrito, BindingResult result ,Model model) {

        boolean hasErrors = false;
        if(id_cliente != null){
            carrito.setCliente(clienteService.findOne(id_cliente));
        }
            
        if (carrito.getProducto() == null) {
            
            model.addAttribute("errorProducto", "Debe seleccionar un cliente");
           
            
            hasErrors = true;
        }
        
        if (carrito.getCliente() == null) {
        
            model.addAttribute("errorCliente", "Debe seleccionar un cliente");
            
            hasErrors = true;
        }
        
        // Validar manualmente el campo cantidad antes de agregar el carrito
        if (carrito.getCantidad() == null || carrito.getCantidad() <= 0) {
            
            model.addAttribute("errorCantidad", "La cantidad debe ser mayor que cero");
            
            hasErrors = true;
        }

        
        
        

        
        
        if(!hasErrors){
            Inventario producto = carrito.getProducto();
            int cantidadCarrito = carrito.getCantidad();
            if(producto.getCantidad() < (cantidadCarrito + cantidadEnCarritoEnCarrito(carrito.getProducto().getId()))){
                model.addAttribute("errorCantidad", "no hay suficiente unidades, unidades restantes: " + (producto.getCantidad() - (cantidadEnCarritoEnCarrito(carrito.getProducto().getId()))) );
                hasErrors = true;
            }else{
                if (id_cliente == null) {
                    id_cliente = carrito.getCliente().getId();
                }
                
                total += carrito.getProducto().getPrecio() * carrito.getCantidad();
                
                items.add(carrito);
                productos.add(carrito.getProducto().getId().toString());
                return "redirect:formCompra";
            }
        }

        
        model.addAttribute("productos", InventarioService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("listaCarrito", items);
        if (id_cliente != null) {
            model.addAttribute("clienteSeleccionado", true);
        }
        return "carritocompras";
        

        
        
        
        

        
        
    }

    @GetMapping("/finalizar")
    public String finalizar(Model model, RedirectAttributes flash) {
        Factura factura = new Factura();
        if (id_cliente != null && productos != null) {
            factura.setCliente(id_cliente);
            factura.setLista(productos.toString().replace("[", "").replace("]", "").replace(" ", ""));
            //reducir decimales 
            double numeroRedondeado = Math.floor(total * 100) / 100;
            factura.setTotal(numeroRedondeado);
            
            facturaService.save(factura);
            for(Carrito item : items){
                Inventario producto1 = InventarioService.findOne(item.getProducto().getId());
                int cantidad_carrito = item.getCantidad();
                producto1.setCantidad(producto1.getCantidad()-cantidad_carrito);
                InventarioService.save(producto1);
            }
            items.clear();
            productos.clear();
            id_cliente = null;
            total = 0.0;

        } else {
            if (id_cliente == null) {
                flash.addFlashAttribute("error", "Error pruducto o cliente vacio");
            }else{
                flash.addAttribute("error","el carrito de comprasd esta vacio");
            }
        }


        return "redirect:formCompra";
    }
    public int cantidadEnCarritoEnCarrito(Long idProducto) {
        int cantidad = 0;
        for (Carrito item : items) {
            if (item.getProducto().getId().equals(idProducto)) {
                cantidad += item.getCantidad();
            }
        }
        return cantidad;
    }

    

    public void setItems(List<Carrito> items) {
        this.items = items;
    }

   

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    
   

    

    public void setProductos(List<String> productos) {
        this.productos = productos;
    }

    
    

    

}
