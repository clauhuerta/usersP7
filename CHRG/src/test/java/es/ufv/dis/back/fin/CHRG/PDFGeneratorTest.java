package es.ufv.dis.back.fin.CHRG;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PDFGeneratorTest {

    @Test
    void generarPdfCreaArchivo() {
        String output = "test_output.pdf";
        PDFGenerator.generar("usuarios.json", output);
        File f = new File(output);
        assertTrue(f.exists() && f.length() > 0);
        f.delete(); // limpieza
    }

    @Test
    void generarPdfNoExcepcionaConArchivoValido() {
        assertDoesNotThrow(() -> PDFGenerator.generar("usuarios.json", "info.pdf"));
    }

    @Test
    void generarPdfConArchivoInexistente() {
        String fakeInput = "no-existe.json";
        String fakeOutput = "fake.pdf";

        PDFGenerator.generar(fakeInput, fakeOutput);

        File f = new File(fakeOutput);
        assertFalse(f.exists(), "El archivo PDF no debería haberse creado");
    }


    @Test
    void pdfContieneAlgoDeContenido() {
        String output = "contenido.pdf";
        PDFGenerator.generar("usuarios.json", output);
        File f = new File(output);
        assertTrue(f.length() > 100);
        f.delete();
    }
}
