package com.jfranco.spring.tienda.springbootapptienda.models.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AuditDaoImpl implements AuditDao {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IInventarioService inventarioService;

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllAuditCliente() {
        return em.createNativeQuery(
                "select c.nombre,c.apellido,c.cedula,c.correo,c.direccion,c.telefono,c.id,r.timestamp as tiempo,r.username from cliente_aud c join rev r on c.rev = r.rev")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllAuditInventario() {
        return em.createNativeQuery(
                "select i.id,i.nombre,i.precio,i.codigo,i.detalles,i.cantidad,r.timestamp as tiempo,r.username from inventario_aud i join rev r on i.rev = r.rev")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllAuditFactura() {
        List<Object[]> lista = em.createNativeQuery(
                "select f.cliente,f.lista,f.total,f.id,r.timestamp as tiempo,r.username from factura_aud f join rev r on f.rev = r.rev")
                .getResultList();
        List<Object[]> resultadosTransformados = new ArrayList<>();
        for (Object[] numero : lista) {
            Cliente cliente = clienteService.findOne(Long.parseLong(numero[0].toString()));

            List<Long> inventarioIds = Arrays.stream(numero[1].toString().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            List<Inventario> inventarios = inventarioIds.stream()
                    .map(inventarioService::findOne)
                    .collect(Collectors.toList());

            Object[] resultadoTransformado = new Object[6];
            resultadoTransformado[0] = cliente;
            resultadoTransformado[1] = inventarios;
            resultadoTransformado[2] = numero[2]; // total
            resultadoTransformado[3] = numero[3]; // id
            resultadoTransformado[4] = numero[4]; // tiempo
            resultadoTransformado[5] = numero[5]; // username

            // Agregar el resultado transformado a la lista
            resultadosTransformados.add(resultadoTransformado);
        }
        return resultadosTransformados;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findEmployeebest() {
        return em
                .createNativeQuery(
                        "select count(username) as contador,username from rev join factura_aud where rev.rev = factura_aud.rev group by username order by username asc")
                .setMaxResults(5)
                .getResultList();
    }

}
