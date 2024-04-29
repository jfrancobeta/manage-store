package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.List;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;


public interface IClienteService {
    public List<Cliente> findAll();

    public void save(Cliente cliente);

    public Cliente findOne(Long id);
    
    public void Eliminar(Long id);

    public Cliente FindbyCedula(String cedula);

    public List<Cliente> search(String nombre, String cedula);

    public void deleteAll();
}
