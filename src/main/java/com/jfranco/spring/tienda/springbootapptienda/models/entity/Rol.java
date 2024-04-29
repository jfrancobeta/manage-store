package com.jfranco.spring.tienda.springbootapptienda.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "rol")
public class Rol implements Serializable {
    private static final Long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @NotEmpty
    private String nombre;

    

    public Rol() {
    }

    public Rol(@NotEmpty String nombre) {
        this.nombre = nombre;
    }

    public Rol(Long idRol, @NotEmpty String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
