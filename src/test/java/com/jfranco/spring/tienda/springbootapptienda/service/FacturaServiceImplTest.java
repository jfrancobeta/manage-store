package com.jfranco.spring.tienda.springbootapptienda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IFacturaDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;

@SpringBootTest
public class FacturaServiceImplTest {
    @Mock
    private IFacturaDao facturaDaoMock;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    @BeforeEach
    public void setUp() {
        // Configurar el comportamiento simulado para el mock de IFacturaDao
        List<Factura> facturas = new ArrayList<>();
        facturas.add(new Factura(1L, 1L, "Producto1, Producto2", 100.0, new Date()));
        facturas.add(new Factura(2L, 1L, "Producto3, Producto4", 150.0, new Date()));
        when(facturaDaoMock.findAll()).thenReturn(facturas);
        
        
    }

    @Test
    public void testFindAll() {
        List<Factura> facturas = facturaService.findAll();
        assertEquals(2, facturas.size());
    }

    @Test
    public void testSave() {
        Factura factura = new Factura(3L, 2L, "Producto5, Producto6", 200.0, new Date());
        facturaService.save(factura);
        verify(facturaDaoMock, times(1)).save(factura);
    }

    @Test
    public void testFindOne() {
        when(facturaDaoMock.findById(any())).thenReturn(Optional.of( new Factura(1L, 1L, "Producto1, Producto2", 100.0, new Date())));
        Factura factura = facturaService.findOne(1L);
        assertNotNull(factura);
    }

    @Test
    public void testEliminar() {
        facturaService.Eliminar(1L);
        verify(facturaDaoMock, times(1)).deleteById(1L);
    }

    @Test
    public void testCalcularGananciaMes() {
        // Configurar el comportamiento simulado para el método convertirAFechaAnioMes() en FacturaServiceImpl
        

       
            
        

        Map<String, Double> gananciaMes = facturaService.calcularGananciaMes();
        assertNotNull(gananciaMes);
        assertEquals(1, gananciaMes.size());
    }

    @Test
    public void testDeleteAll() {
        facturaService.deleteAll();
        verify(facturaDaoMock, times(1)).deleteAll();
    }
}
