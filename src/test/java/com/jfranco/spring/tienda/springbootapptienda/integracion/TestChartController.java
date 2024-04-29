package com.jfranco.spring.tienda.springbootapptienda.integracion;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IAudit;
import com.jfranco.spring.tienda.springbootapptienda.service.IFacturaService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestChartController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInventarioService inventarioService;

    @MockBean
    private IFacturaService facturaService;

    @MockBean
    private IAudit auditService;

    @Test
    public void testGrafico() throws Exception {
        // Mock data
        List<Inventario> inventario = Arrays.asList(
                new Inventario(1L, "Producto 1", "123456", 100.0F, "Detalles 1", 10),
                new Inventario(2L, "Producto 2", "789012", 200.0F, "Detalles 2", 20)
        );
        Map<String, Double> gananciaMes = new HashMap<>();
        gananciaMes.put("2024-03", 1000.0);
        gananciaMes.put("2024-04", 1500.0);

        List<Object[]> empleados = new ArrayList<>();
        empleados.add(new Object[]{"Empleado 1", 10});
        empleados.add(new Object[]{"Empleado 2", 8});

        // Mocking service methods
        Mockito.when(inventarioService.findAll()).thenReturn(inventario);
        Mockito.when(facturaService.calcularGananciaMes()).thenReturn(gananciaMes);
        Mockito.when(auditService.findEmployeebest()).thenReturn(empleados);

        // Perform request
        mockMvc.perform(get("/chart").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("startCharts"))
                .andExpect(model().attribute("titulo", "Dashboard"))
                .andExpect(model().attribute("nombres", Matchers.hasSize(2))) // Verificar tamaño de la lista de nombres
                .andExpect(model().attribute("cantidades", Matchers.hasSize(2))) // Verificar tamaño de la lista de cantidades
                .andExpect(model().attribute("gananciasPorMes", Matchers.hasEntry("2024-03", 1000.0))) // Verificar ganancia de enero
                .andExpect(model().attribute("gananciasPorMes", Matchers.hasEntry("2024-04", 1500.0))) // Verificar ganancia de febrero
                .andExpect(model().attribute("mesAnterior", Matchers.equalTo(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP)))) // Verificar ganancia del mes anterior
                .andExpect(model().attribute("mesActual", Matchers.equalTo(BigDecimal.valueOf(1500).setScale(2, RoundingMode.HALF_UP)))) // Verificar ganancia del mes actual
                .andExpect(model().attribute("porcentaje", Matchers.equalTo(BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP)))) // Verificar porcentaje de ganancia
                .andExpect(model().attribute("gananciasTotales", Matchers.equalTo(BigDecimal.valueOf(2500).setScale(2, RoundingMode.HALF_UP)))) // Verificar ganancia total
                .andExpect(model().attribute("empleados", Matchers.hasSize(2))); // Verificar tamaño de la lista de empleados
    }
    
}
