package com.jfranco.spring.tienda.springbootapptienda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.AuditDao;

@SpringBootTest
public class AuditServiceImplTest {
    @Mock
    private AuditDao auditDaoMock;

    @InjectMocks
    private AuditServiceImpl auditService;

    @BeforeEach
    public void setUp() {
        // Configuración de comportamiento simulado para el mock de AuditDao
        Mockito.when(auditDaoMock.findAllAuditCliente())
                .thenReturn(Arrays.asList(new Object[]{"audit1"}, new Object[]{"audit2"}));

        Mockito.when(auditDaoMock.findAllAuditInventario())
                .thenReturn(Arrays.asList(new Object[]{"audit3"}, new Object[]{"audit4"}));

        Mockito.when(auditDaoMock.findAllAuditFactura())
                .thenReturn(Arrays.asList(new Object[]{"audit5"}, new Object[]{"audit6"}));

        Mockito.when(auditDaoMock.findEmployeebest())
                .thenReturn(Arrays.asList(new Object[]{"employee1"}, new Object[]{"employee2"}));
    }

    @Test
    public void testFindAllAuditCliente() {
        List<Object[]> audits = auditService.findAllAuditCliente();
        assertEquals(2, audits.size());
        assertEquals("audit1", audits.get(0)[0]);
        assertEquals("audit2", audits.get(1)[0]);
    }

    @Test
    public void testFindAllAuditInventario() {
        List<Object[]> audits = auditService.findAllAuditInventario();
        assertEquals(2, audits.size());
        assertEquals("audit3", audits.get(0)[0]);
        assertEquals("audit4", audits.get(1)[0]);
    }

    @Test
    public void testFindAllAuditFactura() {
        List<Object[]> audits = auditService.findAllAuditFactura();
        assertEquals(2, audits.size());
        assertEquals("audit5", audits.get(0)[0]);
        assertEquals("audit6", audits.get(1)[0]);
    }

    @Test
    public void testFindEmployeebest() {
        List<Object[]> employees = auditService.findEmployeebest();
        assertEquals(2, employees.size());
        assertEquals("employee1", employees.get(0)[0]);
        assertEquals("employee2", employees.get(1)[0]);
    }
}
