package es.ufv.dis.back.fin.CHRG;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DireccionTest {

    @Test
    void constructorCompleto() {
        Direccion d = new Direccion("Gran Vía", 100, "28013", "4C", "Madrid");

        assertEquals("Gran Vía", d.getCalle());
        assertEquals(100, d.getNumero());
        assertEquals("28013", d.getCodigoPostal());
        assertEquals("4C", d.getPisoLetra());
        assertEquals("Madrid", d.getCiudad());
    }

    @Test
    void settersFuncionan() {
        Direccion d = new Direccion();
        d.setCalle("Mayor");
        d.setNumero(50);
        d.setCodigoPostal("28014");
        d.setPisoLetra("3B");
        d.setCiudad("Toledo");

        assertEquals("Mayor", d.getCalle());
        assertEquals(50, d.getNumero());
        assertEquals("Toledo", d.getCiudad());
    }

    @Test
    void gettersRetornanDatosCorrectos() {
        Direccion d = new Direccion("Ronda", 3, "30001", "1D", "Murcia");

        assertAll(
                () -> assertEquals("Ronda", d.getCalle()),
                () -> assertEquals(3, d.getNumero()),
                () -> assertEquals("30001", d.getCodigoPostal())
        );
    }

    @Test
    void compararDosInstancias() {
        Direccion d1 = new Direccion("A", 1, "00000", "1A", "Ciudad");
        Direccion d2 = d1;
        assertSame(d1, d2);
    }
}
