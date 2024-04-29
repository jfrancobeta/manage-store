package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IClienteDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDao ClienteDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) ClienteDao.findAll();
    }

    @Override
    @Transactional
    public void save(Cliente producto) {
        ClienteDao.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return ClienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void Eliminar(Long id) {
        ClienteDao.deleteById(id);
    }

    @Override
    public Cliente FindbyCedula(String cedula) {
        try {
            Query query = em.createQuery("select c from Cliente c where c.cedula = :cedula ")
                    .setParameter("cedula", cedula);
            return (Cliente) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Cliente> search(String nombre, String cedula) {
        return ClienteDao.findByNombreContainingIgnoreCaseOrCedulaContainingIgnoreCase(nombre, cedula);
    }

    @Override
    public void deleteAll() {
        ClienteDao.deleteAll();
    }

}
