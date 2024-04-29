package com.jfranco.spring.tienda.springbootapptienda.models.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Factura;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Inventario;
import com.jfranco.spring.tienda.springbootapptienda.service.IClienteService;
import com.jfranco.spring.tienda.springbootapptienda.service.IFacturaService;
import com.jfranco.spring.tienda.springbootapptienda.service.IInventarioService;

@SpringBootTest
public class AuditDaoImplTest {
    @Autowired
    private AuditDaoImpl auditDao;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IInventarioService inventarioService;

    @Autowired
    private IFacturaService facturaService;

    private Cliente cliente;

    private Inventario inventario1;

    private Inventario inventario2;

    @BeforeEach
    public void setUp() {

        cliente = new Cliente("Juan", "Pérez", "1234567890", "juan@example.com", "Calle 123", "1234567890");
        clienteService.save(cliente);

        inventario1 = new Inventario("juan", "sdjsd3", 193.4F, "dfdfj", 20);
        inventarioService.save(inventario1);

        inventario2 = new Inventario("juan", "sdjs67d3", 193.4F, "dfdfj", 20);
        inventarioService.save(inventario2);

    }

    @Test
    public void testFindAllAuditCliente() {

        List<Object[]> result = auditDao.findAllAuditCliente();

        assertEquals(1, result.size());
        assertNotNull(auditDao.findAllAuditCliente());
    }

    @Test
    void testFindAllAuditInventario() {

        // Llama al método del DAO y verifica los resultados
        List<Object[]> result = auditDao.findAllAuditInventario();

        // Realiza las aserciones necesarias para verificar si los resultados son los
        // esperados
        // Por ejemplo, verifica si el tamaño de la lista resultante es correcto
        assertEquals(8, result.size());
        assertNotNull(auditDao.findAllAuditInventario());
    }

    @Test
    void testFindAllAuditFactura() {
        // Preparar datos de prueba

        Factura factura = new Factura(cliente.getId(), "1,2", 100.0, new Date());
        facturaService.save(factura);
        // Ejecutar la consulta nativa
        List<Object[]> resultados = auditDao.findAllAuditFactura();

        // Verificar los resultados
        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());

        for (Object[] resultado : resultados) {
            assertNotNull(resultado);
            assertEquals(6, resultado.length);
            assertTrue(resultado[0] instanceof Cliente);
            assertTrue(resultado[1] instanceof List);
            assertTrue(resultado[2] instanceof Double);
            assertTrue(resultado[3] instanceof Long);
            // assertTrue(resultado[4] instanceof Timestamp);
            assertTrue(resultado[5] instanceof String);
        }
    }

    @Test
    void testFindEmployeebest() {
        Factura factura = new Factura(1L, "1,2", 100.0, new Date());
        facturaService.save(factura);

        List<Object[]> resultados = auditDao.findEmployeebest();
        assertEquals(1, resultados.size());
        assertNotNull(resultados);

    }
}
