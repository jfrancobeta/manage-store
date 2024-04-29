package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.AuditDao;

@Service
public class AuditServiceImpl implements IAudit {
    @Autowired
    private AuditDao Auditdao;

    @Override
    public List<Object[]> findAllAuditCliente() {
        return Auditdao.findAllAuditCliente();
    }

    @Override
    public List<Object[]> findAllAuditInventario() {
        return Auditdao.findAllAuditInventario();
    }

    @Override
    public List<Object[]> findAllAuditFactura() {
        return Auditdao.findAllAuditFactura();
    }

    @Override
    public List<Object[]> findEmployeebest() {
        return Auditdao.findEmployeebest();
    }
}
