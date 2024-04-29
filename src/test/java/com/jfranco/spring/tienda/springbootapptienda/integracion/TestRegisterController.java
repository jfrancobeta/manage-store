package com.jfranco.spring.tienda.springbootapptienda.integracion;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jfranco.spring.tienda.springbootapptienda.models.dao.IUsuarioDao;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Rol;
import com.jfranco.spring.tienda.springbootapptienda.models.entity.Usuario;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRegisterController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUsuarioDao usuarioDao;

    @Test
    public void testListarRegister() throws Exception {
        // Crear una lista de usuarios de ejemplo
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "user1", "1234", Arrays.asList(new Rol(1L, "ADMIN"))));

        // Simular el comportamiento del repositorio de usuarios
        when(usuarioDao.findAll()).thenReturn(usuarios);

        // Realizar solicitud GET a /register/ListarRegister
        mockMvc.perform(get("/register/ListarRegister").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("ListarRegister"))
                .andExpect(model().attribute("usuarios", Matchers.hasSize(1))); // Verificar que se devuelvan todos los
                                                                                // usuarios
    }

    @Test
    public void testFormRegister() throws Exception {
        // Realizar solicitud GET a /register/formRegister
        mockMvc.perform(get("/register/formRegister").with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("formRegister"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("titulo", "Registrar Usuario"));
    }

    // @Test
    // public void testGuardarNuevoUsuario() throws Exception {
        

    //     // Simular el comportamiento del repositorio de usuarios
    //     when(usuarioDao.findByUsername("nuevoUsuario")).thenReturn(null);

    //     // Realizar solicitud POST a /register/formRegister para guardar un nuevo
    //     // usuario
    //     mockMvc.perform(post("/register/formRegister").with(user("admin").password("1234").roles("ADMIN"))
    //             .param("username", "nuevoUsuario")
    //             .param("password", "password")
    //             .param("rolesSeleccionados", "ROLE_USER"))
    //             .andExpect(status().is3xxRedirection()) 
    //             .andExpect(redirectedUrl("ListarRegister"));
    // }

    // @Test
    // public void testGuardarUsuarioExistente() throws Exception {
    //     // Datos de usuario para la prueba
    //     Usuario usuario = new Usuario();
    //     usuario.setIdUsuario(1L); 
    //     usuario.setUsername("usuarioExistente");
    //     usuario.setPassword("password");
    //     List<Rol> rolesSeleccionados = Arrays.asList(new Rol(1L, "ADMIN"));
    //     usuario.setRoles(rolesSeleccionados);

    //     // Simular el comportamiento del repositorio de usuarios
    //     when(usuarioDao.findByUsername("usuarioExistente")).thenReturn(usuario);

    //     // Realizar solicitud POST a /register/formRegister para guardar un usuario
    //     // existente
    //     mockMvc.perform(post("/register/formRegister").with(user("admin").password("1234").roles("ADMIN"))
    //             .param("username", "usuarioExistente")
    //             .param("password", "password")
    //             .param("rolesSeleccionados", "ROLE_USER"))
    //             .andExpect(status().isOk()) // Verificar que la vista se muestra nuevamente
    //             .andExpect(view().name("formRegister")) // Verificar que se muestra la vista de registro
    //             .andExpect(model().attributeExists("errorUser")); // Verificar que se muestra el mensaje de error de
    //                                                               // usuario existente
    // }

    @Test
    public void testEditarUsuario() throws Exception {
        // Datos de usuario para la prueba
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L); // Supongamos que este usuario ya existe en la base de datos
        usuario.setUsername("usuarioExistente");
        usuario.setPassword("password");

        // Simular el comportamiento del repositorio de usuarios
        when(usuarioDao.findById(1L)).thenReturn(Optional.of(usuario));

        // Realizar solicitud GET a /register/editar/{id} para editar un usuario
        // existente
        mockMvc.perform(get("/register/editar/{id}", 1L).with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().isOk()) // Verificar que la vista se muestra correctamente
                .andExpect(view().name("formRegister")) // Verificar que se muestra la vista de registro
                .andExpect(model().attributeExists("usuario")) // Verificar que se carga el usuario en el modelo
                .andExpect(model().attribute("titulo", "Editar cliente")); // Verificar que el título es correcto
    }

    @Test
    public void testEliminarUsuario() throws Exception {
        // Realizar solicitud GET a /register/eliminar/{id} para eliminar un usuario
        mockMvc.perform(get("/register/eliminar/{id}", 1L).with(user("admin").password("1234").roles("ADMIN")))
                .andExpect(status().is3xxRedirection()) // Verificar redirección
                .andExpect(redirectedUrl("/register/ListarRegister")); // Verificar que se redirija a ListarRegister
    }

}
