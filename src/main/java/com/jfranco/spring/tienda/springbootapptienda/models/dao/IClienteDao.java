package com.jfranco.spring.tienda.springbootapptienda.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;


public interface IClienteDao extends CrudRepository<Cliente,Long> {
    List<Cliente> findByNombreContainingIgnoreCaseOrCedulaContainingIgnoreCase(String nombre, String cedula);
}
