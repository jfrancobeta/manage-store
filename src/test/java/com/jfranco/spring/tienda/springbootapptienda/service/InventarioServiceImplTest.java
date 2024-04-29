package com.jfranco.spring.tienda.springbootapptienda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IInventarioDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;

@SpringBootTest
public class InventarioServiceImplTest {
    @Mock
    private IInventarioDao inventarioDaoMock;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    @BeforeEach
    public void setUp() {
        // Configurar el comportamiento simulado para el mock de IInventarioDao
        List<Inventario> inventarios = new ArrayList<>();
        inventarios.add(new Inventario(1L, "Producto1", "COD1", 10.0f, "Detalles1", 100));
        inventarios.add(new Inventario(2L, "Producto2", "COD2", 20.0f, "Detalles2", 200));
        when(inventarioDaoMock.findAll()).thenReturn(inventarios);

        when(inventarioDaoMock.findById(1L)).thenReturn(Optional.of(inventarios.get(0)));
        when(inventarioDaoMock.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase("Producto", "COD")).thenReturn(inventarios);
    }

    @Test
    public void testFindAll() {
        List<Inventario> inventarios = inventarioService.findAll();
        assertEquals(2, inventarios.size());
    }

    @Test
    public void testSave() {
        Inventario producto = new Inventario("Producto3", "COD3", 30.0f, "Detalles3", 300);
        inventarioService.save(producto);
        verify(inventarioDaoMock, times(1)).save(producto);
    }

    @Test
    public void testFindOne() {
        Inventario producto = inventarioService.findOne(1L);
        assertNotNull(producto);
    }

    @Test
    public void testEliminar() {
        inventarioService.Eliminar(1L);
        verify(inventarioDaoMock, times(1)).deleteById(1L);
    }

    @Test
    public void testSearch() {
        
        List<Inventario> inventarios = inventarioService.search("Producto", "COD");
        assertEquals(2, inventarios.size());
    }

    @Test
    public void testDeleteAll() {
        inventarioService.deleteAll();
        verify(inventarioDaoMock, times(1)).deleteAll();
    }
}
