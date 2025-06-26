package es.ufv.dis.back.fin.CHRG;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

public class UsuarioServiceTest {

    private final UsuarioService service = new UsuarioService();

    @Test
    void cargarUsuariosDesdeJson() {
        List<Usuario> usuarios = service.getUsuarios();
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void obtenerUsuarioPorId() {
        Usuario ejemplo = service.getUsuarios().get(0);
        Usuario obtenido = service.getUsuarioPorId(ejemplo.getId().toString());

        assertNotNull(obtenido);
        assertEquals(ejemplo.getId(), obtenido.getId());
    }

    @Test
    void añadirNuevoUsuario() {
        int inicial = service.getUsuarios().size();

        Usuario nuevo = new Usuario();
        nuevo.setId(java.util.UUID.randomUUID());
        nuevo.setNombre("Test");
        nuevo.setApellidos("Usuario");
        nuevo.setEmail("test@ejemplo.com");
        nuevo.setNif("00000000X");
        nuevo.setDireccion(new Direccion("Calle Falsa", 123, "99999", "1Z", "Faketown"));
        nuevo.setMetodoPago(new MetodoPago(1234567890123L, "Test Usuario"));

        service.addUsuario(nuevo);
        int despues = service.getUsuarios().size();

        assertTrue(despues > inicial);
    }

    @Test
    void actualizarUsuario() {
        Usuario u = service.getUsuarios().get(0);
        String original = u.getNombre();

        u.setNombre("Nombre_" + UUID.randomUUID()); // Asegura que sea único
        service.updateUsuario(u.getId().toString(), u);

        Usuario modificado = service.getUsuarioPorId(u.getId().toString());
        assertEquals(u.getNombre(), modificado.getNombre());
    }

}
