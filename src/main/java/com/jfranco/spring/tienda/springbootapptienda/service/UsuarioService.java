package com.jfranco.spring.tienda.springbootapptienda.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IUsuarioDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Rol;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Usuario;



 @Service("userDetailsService")
 public class UsuarioService implements UserDetailsService{

     @Autowired
     private IUsuarioDao usuarioDao;

    @Override
     @Transactional(readOnly = true)
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario usuario = usuarioDao.findByUsername(username);
         if (usuario == null) {
             throw new UsernameNotFoundException(username);
        }

         var roles = new ArrayList<GrantedAuthority>();

         for (Rol rol : usuario.getRoles()) {
             roles.add(new SimpleGrantedAuthority(rol.getNombre()));
         }
         return new User(usuario.getUsername(),usuario.getPassword(),roles);
     }
    
 }
