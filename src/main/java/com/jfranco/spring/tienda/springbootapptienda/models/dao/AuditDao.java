package com.jfranco.spring.tienda.springbootapptienda.models.dao;


import java.util.List;



public interface AuditDao {

    List<Object[]> findAllAuditCliente();
    List<Object[]> findAllAuditInventario();
    List<Object[]> findAllAuditFactura();
    List<Object[]> findEmployeebest();

} 