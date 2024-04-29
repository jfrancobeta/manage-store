package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.List;
import java.util.Map;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;


public interface IFacturaService {
    public List<Factura> findAll();

    public void save(Factura factura);

    public Factura findOne(Long id);
    
    public void Eliminar(Long id);

    public Map<String, Double> calcularGananciaMes();

    public void deleteAll();

    
        
    
}
