package com.jfranco.spring.tienda.springbootapptienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@SessionAttributes("inventario")
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private IInventarioService InventarioService;

    

    @GetMapping("/ListarInventario")
    public String Listar(Model model,@RequestParam(required = false) String query) {
        model.addAttribute("titulo", "listado Productos");
        if(query != null && !query.isEmpty()){
            model.addAttribute("inventarios", InventarioService.search(query, query));
        }else{
            model.addAttribute("inventarios", InventarioService.findAll());
        }
        
        return "ListarInventario";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Inventario producto = new Inventario();
        model.addAttribute("titulo", "guardar Producto");
        model.addAttribute("inventario", producto);
        return "formInventario";
    }

    @PostMapping("/form")
    public String Guardar(@Valid Inventario inventario,BindingResult result, SessionStatus status,
     Model model, RedirectAttributes flash) {

        boolean isNewInv = inventario.getId() == null;
        if (result.hasErrors()) {
            model.addAttribute("titulo", isNewInv ? "guardar Producto" : "Editar Cliente");
            return "formInventario";
        }
        InventarioService.save(inventario);
        status.setComplete();
        return "redirect:ListarInventario";
    }

    @GetMapping("/eliminar/{id}")
    public String Eliminar(@PathVariable(name = "id") Long id, Model model) {
        if (id > 0) {
            InventarioService.Eliminar(id);
        }
        return "redirect:/inventario/ListarInventario";
    }

    @GetMapping("/editar/{id}")
    public String Editar(@PathVariable(name = "id") Long id, Model model) {
        Inventario inventario = null;
        if (id > 0) {
            inventario = InventarioService.findOne(id);

        } else {
            return "redirect:/ListarInventario";
        }
        model.addAttribute("inventario", inventario);
        model.addAttribute("titulo", "editar Producto");
        return "formInventario";
    }

    

}
