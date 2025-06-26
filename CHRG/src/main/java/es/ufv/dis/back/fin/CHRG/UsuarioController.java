package es.ufv.dis.back.fin.CHRG;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable String id) {
        return usuarioService.getUsuarioPorId(id);
    }

    @PostMapping
    public void addUsuario(@RequestBody Usuario nuevoUsuario) {
        usuarioService.addUsuario(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public void updateUsuario(@PathVariable String id, @RequestBody Usuario usuarioActualizado) {
        usuarioService.updateUsuario(id, usuarioActualizado);
    }

    @GetMapping("/pdf")
    public ResponseEntity<String> generarPdf() {
        try {
            PDFGenerator.generar(); // Sin parámetros
            return ResponseEntity.ok("PDF generado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al generar el PDF: " + e.getMessage());
        }
    }



}
