package com.jfranco.spring.tienda.springbootapptienda.models.domain;



import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;





public class Carrito {
    
    
    private Inventario producto;

    
    private Integer cantidad;

    
    private Cliente cliente;



    public Carrito() {
    }



    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Inventario getProducto() {
        return producto;
    }

    public void setProducto(Inventario producto) {
        this.producto = producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    

   
    

    

    

    

    
     
  
}
