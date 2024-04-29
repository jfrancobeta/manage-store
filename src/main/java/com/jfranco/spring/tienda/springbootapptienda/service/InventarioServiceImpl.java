package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IInventarioDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;

@Service
public class InventarioServiceImpl implements IInventarioService {

    @Autowired
    private IInventarioDao inventarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Inventario> findAll() {
        return  (List<Inventario>) inventarioDao.findAll();
    }

    @Override
    @Transactional()
    public void save(Inventario producto) {
        inventarioDao.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Inventario findOne(Long id) {
        return inventarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void Eliminar(Long id) {
        inventarioDao.deleteById(id);
    }

    @Override
    public List<Inventario> search(String nombre, String cedula) {
        return inventarioDao.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(nombre, cedula);
    }

    @Override
    public void deleteAll() {
        inventarioDao.deleteAll();
    }
    
}
