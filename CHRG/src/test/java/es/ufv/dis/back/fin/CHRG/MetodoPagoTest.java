package es.ufv.dis.back.fin.CHRG;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MetodoPagoTest {

    @Test
    void constructorYGetters() {
        MetodoPago m = new MetodoPago(1111222233334444L, "Juan Pérez");

        assertEquals(1111222233334444L, m.getNumeroTarjeta());
        assertEquals("Juan Pérez", m.getNombreAsociado());
    }

    @Test
    void settersFuncionan() {
        MetodoPago m = new MetodoPago();
        m.setNumeroTarjeta(5555666677778888L);
        m.setNombreAsociado("Ana Gómez");

        assertEquals("Ana Gómez", m.getNombreAsociado());
        assertEquals(5555666677778888L, m.getNumeroTarjeta());
    }

    @Test
    void cambioDeNombre() {
        MetodoPago m = new MetodoPago();
        m.setNombreAsociado("Pedro");
        assertEquals("Pedro", m.getNombreAsociado());
    }

    @Test
    void compararNumerosTarjeta() {
        MetodoPago m1 = new MetodoPago(1234567890L, "A");
        MetodoPago m2 = new MetodoPago(1234567890L, "B");

        assertEquals(m1.getNumeroTarjeta(), m2.getNumeroTarjeta());
    }
}
