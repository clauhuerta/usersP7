package es.ufv.dis.back.fin.CHRG;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class UsuarioTest {

    @Test
    void constructorYGetters() {
        Direccion d = new Direccion("Calle", 1, "00000", "1A", "Ciudad");
        MetodoPago m = new MetodoPago(1234567890123456L, "Titular");
        UUID id = UUID.randomUUID();

        Usuario u = new Usuario(id, "Pepe", "Pérez", "12345678A", d, "pepe@mail.com", m);

        assertEquals("Pepe", u.getNombre());
        assertEquals("Pérez", u.getApellidos());
        assertEquals("12345678A", u.getNif());
        assertEquals(d, u.getDireccion());
        assertEquals(m, u.getMetodoPago());
    }

    @Test
    void settersFuncionan() {
        Usuario u = new Usuario();
        u.setNombre("Ana");
        u.setApellidos("López");
        u.setNif("87654321B");

        assertEquals("Ana", u.getNombre());
        assertEquals("López", u.getApellidos());
        assertEquals("87654321B", u.getNif());
    }

    @Test
    void direccionYEmail() {
        Direccion d = new Direccion("A", 2, "28000", "2B", "Madrid");
        Usuario u = new Usuario();
        u.setDireccion(d);
        u.setEmail("ana@mail.com");

        assertEquals("ana@mail.com", u.getEmail());
        assertEquals("Madrid", u.getDireccion().getCiudad());
    }

    @Test
    void idEsUUID() {
        UUID id = UUID.randomUUID();
        Usuario u = new Usuario();
        u.setId(id);
        assertEquals(id, u.getId());
    }
}
