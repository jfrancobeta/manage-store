package com.jfranco.spring.tienda.springbootapptienda.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jfranco.spring.tienda.springbootapptienda.service.IAudit;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private IAudit auditService;

    @GetMapping("/auditCliente")
    public String auditCliente(Model model) {
        List<Object[]> auditCliente = auditService.findAllAuditCliente();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (Object[] objects : auditCliente) {
            objects[7] = sdf.format(objects[7]);
        }
        model.addAttribute("auditCliente", auditCliente);
        return "AuditCliente";
    }

    @GetMapping("/auditInventario")
    public String auditInventario(Model model) {
        List<Object[]> auditInventario = auditService.findAllAuditInventario();
        // Formatear la fecha como desees
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (Object[] objects : auditInventario) {
            objects[6] = sdf.format(objects[6]);
        }
        model.addAttribute("auditInventario", auditInventario);
        return "AuditInventario";
    }

    @GetMapping("/auditFactura")
    public String AuditFacturas(Model model) {
        List<Object[]> auditFacturas = auditService.findAllAuditFactura();
        // Formatear la fecha como desees
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (Object[] objects : auditFacturas) {
            objects[4] = sdf.format(objects[4]);
        }
        model.addAttribute("auditFactura", auditFacturas);
        return "AuditFacturas";
    }
    
    
}
