package com.jfranco.spring.tienda.springbootapptienda.models.domain;

import java.util.Date;
import java.util.List;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;

public class FacturaDetallada {
    private Cliente cliente;
    private List<Inventario> productos;
    private Double total;
    private Date fechaCreacion;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Inventario> getProductos() {
        return productos;
    }

    public void setProductos(List<Inventario> productos) {
        this.productos = productos;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((productos == null) ? 0 : productos.hashCode());
        result = prime * result + ((total == null) ? 0 : total.hashCode());
        result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FacturaDetallada other = (FacturaDetallada) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (productos == null) {
            if (other.productos != null)
                return false;
        } else if (!productos.equals(other.productos))
            return false;
        if (total == null) {
            if (other.total != null)
                return false;
        } else if (!total.equals(other.total))
            return false;
        if (fechaCreacion == null) {
            if (other.fechaCreacion != null)
                return false;
        } else if (!fechaCreacion.equals(other.fechaCreacion))
            return false;
        return true;
    }

    

}
