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

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IClienteDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Cliente;

import jakarta.persistence.EntityManager;

@SpringBootTest
public class ClienteServiceImplTest {

    @Mock
    private IClienteDao clienteDaoMock;

    @Mock
    private EntityManager entityManagerMock;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    public void setUp() {
        // Configurar el comportamiento simulado para el mock de IClienteDao
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(
                new Cliente(1L, "Nombre1", "Apellido1", "1234567890", "correo1@example.com", "Dirección1",
                        "1234567890"));
        clientes.add(
                new Cliente(2L, "Nombre2", "Apellido2", "0987654321", "correo2@example.com", "Dirección2",
                        "0987654321"));
        when(clienteDaoMock.findAll()).thenReturn(clientes);

        // Configurar el comportamiento simulado para el mock de EntityManager
        Cliente cliente = new Cliente(3L, "Nombre", "Apellido", "1234567890", "correo@example.com", "Dirección",
                "1234567890");

        when(clienteDaoMock.findByNombreContainingIgnoreCaseOrCedulaContainingIgnoreCase("Nombre", "1234567890"))
                .thenReturn(clientes);

        when(clienteDaoMock.findById(3L)).thenReturn(Optional.of(cliente));

    }

    @Test
    public void testFindAll() {
        List<Cliente> clientes = clienteService.findAll();
        assertEquals(2, clientes.size());
    }

    @Test
    public void testSave() {
        Cliente cliente = new Cliente("Nombre3", "Apellido3", "1111111111", "correo3@example.com", "Dirección3",
                "1111111111");
        clienteService.save(cliente);
        verify(clienteDaoMock, times(1)).save(cliente);
    }

    @Test
    public void testFindOne() {
        Cliente cliente = clienteService.findOne(3L);
        assertNotNull(cliente);
    }

    @Test
    public void testEliminar() {
        clienteService.Eliminar(1L);
        verify(clienteDaoMock, times(1)).deleteById(1L);
    }

    // @Test
    // public void testFindbyCedula() {
    //     Cliente cliente = clienteService.FindbyCedula("123456");
    //     assertNull(cliente);
    // }

    @Test
    public void testSearch() {
        List<Cliente> clientes = clienteService.search("Nombre", "1234567890");
        assertEquals(2, clientes.size());
    }

    @Test
    public void testDeleteAll() {
        clienteService.deleteAll();
        verify(clienteDaoMock, times(1)).deleteAll();
    }
}
