package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.List;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;

public interface IInventarioService {
    public List<Inventario> findAll();

    public void save(Inventario cliente);

    public Inventario findOne(Long id);
    
    public void Eliminar(Long id);

    public List<Inventario> search(String nombre,String cedula);

    public void deleteAll();
}
