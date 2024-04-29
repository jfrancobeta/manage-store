package com.jfranco.spring.tienda.springbootapptienda.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;

public interface  IFacturaDao extends CrudRepository<Factura,Long>{
}
