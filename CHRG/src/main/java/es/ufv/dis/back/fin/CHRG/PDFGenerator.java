package es.ufv.dis.back.fin.CHRG;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class PDFGenerator {

    public static void generar() throws Exception {
        // Leer JSON desde resources
        InputStream inputStream = PDFGenerator.class.getClassLoader().getResourceAsStream("usuarios.json");
        if (inputStream == null) throw new FileNotFoundException("No se encontró usuarios.json");

        InputStreamReader reader = new InputStreamReader(inputStream);
        Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
        List<Usuario> usuarios = new Gson().fromJson(reader, tipoLista);
        reader.close();

        // Ruta absoluta de la raíz del proyecto (2 niveles arriba de /CHRG/target/classes/)
        File projectRoot = new File(System.getProperty("user.dir")).getParentFile();
        File outputFile = new File(projectRoot, "info.pdf");

        System.out.println("📄 Generando PDF en: " + outputFile.getAbsolutePath());

        // Crear PDF
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, new FileOutputStream(outputFile));
        doc.open();

        for (Usuario u : usuarios) {
            doc.add(new Paragraph("Nombre: " + u.getNombre()));
            doc.add(new Paragraph("Apellidos: " + u.getApellidos()));
            doc.add(new Paragraph("NIF: " + u.getNif()));
            doc.add(new Paragraph("Email: " + u.getEmail()));

            Direccion d = u.getDireccion();
            String dirCompleta = d.getCalle() + ", " + d.getNumero() + ", " + d.getPisoLetra()
                    + ", " + d.getCodigoPostal() + ", " + d.getCiudad();
            doc.add(new Paragraph("Dirección: " + dirCompleta));

            MetodoPago m = u.getMetodoPago();
            doc.add(new Paragraph("Método de pago: " + m.getNumeroTarjeta() + " (" + m.getNombreAsociado() + ")"));
            doc.add(Chunk.NEWLINE);
        }

        doc.close();
    }
}
