package com.jfranco.spring.tienda.springbootapptienda.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;

public interface IInventarioDao extends JpaRepository<Inventario,Long> {
    List<Inventario> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(String nombre,String codigo);
}
