package com.jfranco.spring.tienda.springbootapptienda.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario,Long> {

    Usuario findByUsername(String username);

    List<Usuario> findByUsernameContainingIgnoreCase(String username);
    
 

}
