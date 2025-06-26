package es.ufv.dis.back.fin.CHRG;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;


import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class PDFGenerator {

    public static void generar(String inputJson, String outputPdf) {
        try {
            // Leer desde el classpath
            InputStream inputStream = PDFGenerator.class.getClassLoader().getResourceAsStream(inputJson);
            if (inputStream == null) throw new FileNotFoundException("No se encontró " + inputJson);

            InputStreamReader reader = new InputStreamReader(inputStream);
            Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuarios = new Gson().fromJson(reader, tipoLista);
            reader.close();

            // Crear PDF
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream(outputPdf));
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
