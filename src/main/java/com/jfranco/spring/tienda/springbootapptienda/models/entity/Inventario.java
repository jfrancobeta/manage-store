package com.jfranco.spring.tienda.springbootapptienda.models.entity;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Audited
public class Inventario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String codigo;

    @Positive
    @NotNull
    private Float precio;

    @NotEmpty
    private String detalles;

    @NotNull
    private int cantidad;


    private static final Long serialVersionUID = 1L;

    

    public Inventario(Long id, @NotEmpty String nombre, @NotEmpty String codigo, @Positive @NotNull Float precio,
            @NotEmpty String detalles, @NotNull int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.detalles = detalles;
        this.cantidad = cantidad;
    }

    

    public Inventario(@NotEmpty String nombre, @NotEmpty String codigo, @Positive @NotNull Float precio,
            @NotEmpty String detalles, @NotNull int cantidad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.detalles = detalles;
        this.cantidad = cantidad;
    }

    


    public Inventario() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((precio == null) ? 0 : precio.hashCode());
        result = prime * result + ((detalles == null) ? 0 : detalles.hashCode());
        result = prime * result + cantidad;
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
        Inventario other = (Inventario) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (precio == null) {
            if (other.precio != null)
                return false;
        } else if (!precio.equals(other.precio))
            return false;
        if (detalles == null) {
            if (other.detalles != null)
                return false;
        } else if (!detalles.equals(other.detalles))
            return false;
        if (cantidad != other.cantidad)
            return false;
        return true;
    }


    
}
