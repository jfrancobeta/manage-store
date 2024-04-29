package com.jfranco.spring.tienda.springbootapptienda.models.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Audited
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private Long Cliente;
  
    @JoinColumn(name = "productos")
    @Column(columnDefinition = "VARCHAR(255)")
    private String lista ;

    private Double Total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    

    public Factura(Long id, Long cliente, String lista, Double total, Date fechaCreacion) {
        this.id = id;
        Cliente = cliente;
        this.lista = lista;
        Total = total;
        this.fechaCreacion = fechaCreacion;
    }

    

    public Factura(Long cliente, String lista, Double total, Date fechaCreacion) {
        Cliente = cliente;
        this.lista = lista;
        Total = total;
        this.fechaCreacion = fechaCreacion;
    }

    



    public Factura() {
    }



    @PrePersist
    protected void onCreate(){
        fechaCreacion = new Date();
    }
    
    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

    private static final Long serialVersionUID = 1L;
    
    public Long getCliente() {
        return Cliente;
    }
    public void setCliente(Long cliente) {
        Cliente = cliente;
    }
   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getTotal() {
        return Total;
    }
    public void setTotal(Double total) {
        Total = total;
    }



    public Date getFechaCreacion() {
        return fechaCreacion;
    }



    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }
    
    

    
    
}
