package com.jfranco.spring.tienda.springbootapptienda.models.entity;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Audited
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    private String cedula;

    @NotEmpty(message = "El correo no puede estar vacio")
    @Email(message = "Debe ingresar un correo valido")
    private String correo;
    @NotEmpty
    private String direccion;

    @Pattern(regexp = "\\d{10}", message = "El telefono debe tener 10 digitos")
    private String telefono;

    private static final Long serialVersionUID = 1L;

    

    public Cliente(@NotEmpty String nombre, @NotEmpty String apellido, @NotEmpty String cedula,
            @NotEmpty(message = "El correo no puede estar vacio") @Email(message = "Debe ingresar un correo valido") String correo,
            @NotEmpty String direccion,
            @Pattern(regexp = "\\d{10}", message = "El telefono debe tener 10 digitos") String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    

    

    public Cliente(Long id, @NotEmpty String nombre, @NotEmpty String apellido, @NotEmpty String cedula,
            @NotEmpty(message = "El correo no puede estar vacio") @Email(message = "Debe ingresar un correo valido") String correo,
            @NotEmpty String direccion,
            @Pattern(regexp = "\\d{10}", message = "El telefono debe tener 10 digitos") String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
    }





    public Cliente() {
    }



    public Long getId() {
        return id;
    }

    public static Long getSerialversionuid() {
        return serialVersionUID;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apelido) {
        this.apellido = apelido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
