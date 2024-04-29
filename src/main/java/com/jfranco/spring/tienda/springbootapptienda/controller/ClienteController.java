package com.jfranco.spring.tienda.springbootapptienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/cliente")
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    

    @GetMapping("/listarclientes")
    public String listarClientes(Model model,@RequestParam(required = false) String query) {
        model.addAttribute("titulo", "lista clientes");
        if (query != null && !query.isEmpty()) {
            List<Cliente> resultados = clienteService.search(query, query);
            model.addAttribute("clientes", resultados);
        }else{
            model.addAttribute("clientes", clienteService.findAll());
        }
        
        return "ListarClientes";
    }

    @GetMapping("/formClientes")
    public String formCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("titulo", "crear cliente");
        model.addAttribute("cliente", cliente);
        return "formCliente";
    }

    @PostMapping("/formClientes")
    public String guardarCliente(@Valid Cliente cliente, BindingResult result, SessionStatus status,
            Model model) {
             boolean isNewCliente = cliente.getId() == null;
        if (result.hasErrors()) {
            model.addAttribute("titulo", isNewCliente ? "Registrar Cliente" : "Editar Cliente");
            return "formCliente";
        }
        if (cliente.getId() != null) {
            clienteService.save(cliente);
        }else{
            if (clienteService.FindbyCedula(cliente.getCedula()) == null ) {
                clienteService.save(cliente);
                status.setComplete();
            }else{
                model.addAttribute("titulo", isNewCliente ? "Registrar Cliente" : "Editar Cliente");
                model.addAttribute("errorCreado", "la cedula ya esta creada");
                return "formCliente";
            }
        }
        

        return "redirect:listarclientes";

    }

    @GetMapping("/editar/{id}")
    public String Editar(@PathVariable(name = "id") Long id, Model model) {
        Cliente cliente = null;
        if (id > 0) {
            cliente = clienteService.findOne(id);

        } else {
            return "redirect:/ListarInventario";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar cliente");
        return "formCliente";
    }

}
