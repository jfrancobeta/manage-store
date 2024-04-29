package com.jfranco.spring.tienda.springbootapptienda.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IAudit;
import com.jfranco.spring.tienda.springbootapptienda.service.IFacturaService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

@Controller
public class ChartsController {

    @Autowired
    private IInventarioService inventarioService;

    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private IAudit auditService;

    @GetMapping({ "/chart", "/", "" })
    public String Grafico(Model model, @AuthenticationPrincipal User user) {
        List<Inventario> inventario = inventarioService.findAll();

        model.addAttribute("titulo", "Dashboard");
        System.out.println("--------------------------------------------------------------------");
        System.out.println(user);
        Map<String, Double> gananciaMes = facturaService.calcularGananciaMes();
        model.addAttribute("gananciasPorMes", gananciaMes);

        List<String> nombres = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        for (Inventario inventario2 : inventario) {
            nombres.add(inventario2.getNombre());
            cantidades.add(inventario2.getCantidad());
        }

        BigDecimal mesAnterior = new BigDecimal(
                gananciaMes.getOrDefault(YearMonth.now().minusMonths(1).toString(), 0.0));

        BigDecimal mesActual = new BigDecimal(gananciaMes.getOrDefault(YearMonth.now().toString(), 0.0));
        model.addAttribute("mesAnterior", mesAnterior.setScale(2, RoundingMode.HALF_UP));

        model.addAttribute("mesActual", mesActual.setScale(2, RoundingMode.HALF_UP));// (mesAnterior/mesActual)*100

        if (mesAnterior.compareTo(BigDecimal.ZERO) != 0) { // Verifica que mesAnterior no sea cero
            BigDecimal gananciaPorcentaje = ((mesActual.subtract(mesAnterior)).divide(mesAnterior, 4,
                    RoundingMode.HALF_UP)).multiply(new BigDecimal(100.0));
            model.addAttribute("porcentaje", gananciaPorcentaje.setScale(2, RoundingMode.HALF_UP));
        } else {
            model.addAttribute("porcentaje", BigDecimal.ZERO); // Si mesAnterior es cero, el porcentaje también será
                                                               // cero
        }
        model.addAttribute("nombres", nombres);
        model.addAttribute("cantidades", cantidades);

        Double GananciasTotales = 0.0;
        // ganancias totales
        for (Double valor : gananciaMes.values()) {
            GananciasTotales += valor;
        }
        model.addAttribute("gananciasTotales", new BigDecimal(GananciasTotales).setScale(2, RoundingMode.HALF_UP));


        //mejores empleados
        List<Object[]> empleados = auditService.findEmployeebest();
        model.addAttribute("empleados", empleados);
        return "startCharts";
    }

}
