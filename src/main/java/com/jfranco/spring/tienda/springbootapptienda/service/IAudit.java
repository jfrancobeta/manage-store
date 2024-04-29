package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.List;

public interface IAudit {
    List<Object[]> findAllAuditCliente();
    List<Object[]> findAllAuditInventario();
    List<Object[]> findAllAuditFactura();
    List<Object[]> findEmployeebest();
}
