package es.ufv.dis.back.fin.CHRG;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioService {

    private final String JSON_PATH = "usuarios.json";
    private final Gson gson = new Gson();

    public List<Usuario> getUsuarios() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(JSON_PATH);
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuarios = gson.fromJson(reader, tipoLista);
            return usuarios != null ? usuarios : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Usuario getUsuarioPorId(String id) {
        return getUsuarios().stream()
                .filter(u -> u.getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addUsuario(Usuario nuevo) {
        List<Usuario> usuarios = getUsuarios();
        if (nuevo.getId() == null) {
            nuevo.setId(UUID.randomUUID());
        }
        usuarios.add(nuevo);
        guardar(usuarios);
    }

    public void updateUsuario(String id, Usuario actualizado) {
        List<Usuario> usuarios = getUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().toString().equals(id)) {
                usuarios.set(i, actualizado);
                break;
            }
        }
        guardar(usuarios);
    }

    private void guardar(List<Usuario> usuarios) {
        try {
            // Obtener la ruta real del archivo en resources
            URL resourceUrl = getClass().getClassLoader().getResource(JSON_PATH);
            if (resourceUrl == null) throw new FileNotFoundException("No se encontró usuarios.json en classpath");

            File file = new File(resourceUrl.toURI());
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(usuarios, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
