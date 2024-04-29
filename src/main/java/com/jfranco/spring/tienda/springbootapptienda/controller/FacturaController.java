package com.jfranco.spring.tienda.springbootapptienda.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jfranco.spring.tienda.springbootapptienda.models.domain.FacturaDetallada;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;
import com.jfranco.spring.tienda.springbootapptienda.service.IFacturaService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

@Controller
@RequestMapping("/factura")
public class FacturaController {
    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IInventarioService inventarioService;

    @GetMapping("/listarFacturas")
    public String Facturas(Model model){
        model.addAttribute("titulo", "listar facturas");
        List<Factura> facturas = facturaService.findAll();
        Map<Long, FacturaDetallada> facturaTotal = new HashMap<>();
        for (Factura factura : facturas) {
            Cliente cliente = clienteService.findOne(factura.getCliente());
            Double total = factura.getTotal();
            Date fecha = factura.getFechaCreacion();
            List<Inventario> productos = new ArrayList<>(); 
            String lista =  factura.getLista();
            String[] elementos = lista.split(",");
            for (String productoid : elementos) {
                Inventario producto = inventarioService.findOne(Long.parseLong(productoid));
                productos.add(producto);

            }
            FacturaDetallada facturaDetallada = new FacturaDetallada();
            facturaDetallada.setCliente(cliente);
            facturaDetallada.setProductos(productos);
            facturaDetallada.setTotal(total);
            facturaDetallada.setFechaCreacion(fecha);
            facturaTotal.put(factura.getId(),facturaDetallada);

        }
        model.addAttribute("facturas", facturaTotal);
        return "ListarFacturas";
    }
}
