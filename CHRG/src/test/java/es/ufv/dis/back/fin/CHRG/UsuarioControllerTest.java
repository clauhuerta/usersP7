package es.ufv.dis.back.fin.CHRG;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    void testGetAllUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUsuarioPorIdInexistente() throws Exception {
        mockMvc.perform(get("/api/usuarios/{id}", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(content().string("")); // devolverá null
    }

    @Test
    void testPostUsuario() throws Exception {
        Usuario nuevo = new Usuario();
        nuevo.setId(UUID.randomUUID());
        nuevo.setNombre("Test");
        nuevo.setApellidos("Apellido");
        nuevo.setNif("12345678X");
        nuevo.setEmail("test@email.com");
        nuevo.setDireccion(new Direccion("Calle", 1, "00000", "1A", "Ciudad"));
        nuevo.setMetodoPago(new MetodoPago(1234567890123456L, "Test"));

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(nuevo)))
                .andExpect(status().isOk());
    }

    @Test
    void testPutUsuario() throws Exception {
        Usuario u = new Usuario();
        u.setId(UUID.randomUUID());
        u.setNombre("Nuevo");
        u.setApellidos("Apellido");
        u.setNif("98765432Z");
        u.setEmail("nuevo@email.com");
        u.setDireccion(new Direccion("Calle", 2, "11111", "2B", "Ciudad"));
        u.setMetodoPago(new MetodoPago(111122223333L, "Nuevo"));

        // Simula PUT a un ID cualquiera
        mockMvc.perform(put("/api/usuarios/" + u.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(u)))
                .andExpect(status().isOk());
    }
}
