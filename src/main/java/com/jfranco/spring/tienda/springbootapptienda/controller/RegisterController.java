package com.jfranco.spring.tienda.springbootapptienda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IUsuarioDao;
import com.jfranco.spring.tienda.springbootapptienda.models.domain.EncriptarPassword;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Rol;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Usuario;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/register")
@SessionAttributes("usuario")
public class RegisterController {

    @Autowired
    private IUsuarioDao usuarioDao;

    @GetMapping("/ListarRegister")
    public String listarregister(Model model, @RequestParam(required = false) String query) {
        if (query != null && !query.isEmpty()) {
            List<Usuario> usuarios = usuarioDao.findByUsernameContainingIgnoreCase(query);
            model.addAttribute("usuarios", usuarios);
        } else {
            model.addAttribute("usuarios", usuarioDao.findAll());
        }
        return "ListarRegister";
    }

    @GetMapping("/formRegister")
    public String getMethodName(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Registrar Usuario");
        return "formRegister";
    }

    @PostMapping("/formRegister")
    public String guardar(@Valid Usuario usuario, BindingResult result, SessionStatus status, Model model,
            @RequestParam(name = "rol", required = false) List<String> rolesSeleccionados) {
        boolean isNewUser = usuario.getIdUsuario() == null;

        if (result.hasErrors() || rolesSeleccionados == null || rolesSeleccionados.isEmpty()) {
            model.addAttribute("titulo", isNewUser ? "Registrar Usuario" : "Editar Usuario");
            model.addAttribute("errorRol", "selecciona uno o mas roles");
            return "formRegister";
        }

        Usuario existingUser = usuarioDao.findByUsername(usuario.getUsername());
        // Verificar si el usuario existe y no es el mismo que se está editando
        if (existingUser != null && (isNewUser || !existingUser.getIdUsuario().equals(usuario.getIdUsuario()))) {
            model.addAttribute("titulo", isNewUser ? "Registrar Usuario" : "Editar Usuario");
            model.addAttribute("errorUser", "el usuario ya existe");
            return "formRegister";
        }

        if (!result.hasFieldErrors("username") && !result.hasFieldErrors("password")
                && rolesSeleccionados != null) {
            List<Rol> roles = new ArrayList<>();
            for (String nombre : rolesSeleccionados) {
                Rol rol = new Rol();
                rol.setNombre(nombre);
                roles.add(rol);
            }

            usuario.setRoles(roles);
            usuario.setPassword(EncriptarPassword.encriptarPassword(usuario.getPassword()));
            System.out.println(usuario.getRoles());
            usuarioDao.save(usuario);
            status.setComplete();
        }

        return "redirect:ListarRegister";
    }

    @GetMapping("/editar/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Optional<Usuario> usuario = null;
        if (id > 0) {
            usuario = usuarioDao.findById(id);
        } else {
            return "redirect:/ListarRegister";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Editar cliente");
        return "formRegister";
    }

    @GetMapping("/eliminar/{id}")
    public String getMethodName(@PathVariable Long id) {
        if (id > 0) {
            usuarioDao.deleteById(id);
        }
        return "redirect:/register/ListarRegister";
    }

}
