package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.*;
import java.util.*;

@Route("")
public class MainView extends VerticalLayout {

    private final Grid<Usuario> grid = new Grid<>(Usuario.class, false);
    private final Gson gson = new Gson();
    private final String apiBase = "http://localhost:8083/api/usuarios";

    public MainView() {
        setSizeFull();
        configurarGrid();
        cargarUsuarios();

        Button addButton = new Button("Añadir usuario", e -> abrirFormulario(null));
        Button pdfButton = new Button("Generar PDF", e -> generarPDF());

        HorizontalLayout botones = new HorizontalLayout(addButton, pdfButton);
        add(grid, botones);
    }

    private void configurarGrid() {
        grid.addColumn(Usuario::getNombre).setHeader("Nombre");
        grid.addColumn(Usuario::getApellidos).setHeader("Apellidos");
        grid.addColumn(Usuario::getEmail).setHeader("Email");
        grid.addColumn(Usuario::getNif).setHeader("NIF");

        grid.addColumn(new ComponentRenderer<Component, Usuario>(usuario ->
                new Button("Editar", e -> abrirFormulario(usuario))
        )).setHeader("Acción");

        grid.addItemDoubleClickListener(event -> mostrarDetalles(event.getItem()));
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setSizeFull();
    }

    private void cargarUsuarios() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBase))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuarios = gson.fromJson(response.body(), listType);
            grid.setItems(usuarios);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirFormulario(Usuario usuario) {
        Dialog dialog = new Dialog();
        VerticalLayout layout = new VerticalLayout();

        TextField nombre = new TextField("Nombre");
        TextField apellidos = new TextField("Apellidos");
        TextField nif = new TextField("NIF");
        TextField email = new TextField("Email");
        TextField calle = new TextField("Calle");
        TextField numero = new TextField("Número");
        TextField cp = new TextField("Código Postal");
        TextField pisoLetra = new TextField("Piso/Letra");
        TextField ciudad = new TextField("Ciudad");
        TextField tarjeta = new TextField("Número Tarjeta");
        TextField titular = new TextField("Nombre Tarjeta");

        if (usuario != null) {
            nombre.setValue(usuario.getNombre());
            apellidos.setValue(usuario.getApellidos());
            nif.setValue(usuario.getNif());
            email.setValue(usuario.getEmail());
            calle.setValue(usuario.getDireccion().getCalle());
            numero.setValue(String.valueOf(usuario.getDireccion().getNumero()));
            cp.setValue(usuario.getDireccion().getCodigoPostal());
            pisoLetra.setValue(usuario.getDireccion().getPisoLetra());
            ciudad.setValue(usuario.getDireccion().getCiudad());
            tarjeta.setValue(String.valueOf(usuario.getMetodoPago().getNumeroTarjeta()));
            titular.setValue(usuario.getMetodoPago().getNombreAsociado());
        }

        Button guardar = new Button("Guardar", e -> {
            Usuario u = new Usuario(
                    usuario != null ? usuario.getId() : UUID.randomUUID().toString(),
                    nombre.getValue(),
                    apellidos.getValue(),
                    nif.getValue(),
                    new Direccion(calle.getValue(), Integer.parseInt(numero.getValue()), cp.getValue(), pisoLetra.getValue(), ciudad.getValue()),
                    email.getValue(),
                    new MetodoPago(Long.parseLong(tarjeta.getValue()), titular.getValue())
            );

            guardarUsuario(u, usuario != null);
            dialog.close();
        });

        layout.add(nombre, apellidos, nif, email, calle, numero, cp, pisoLetra, ciudad, tarjeta, titular, guardar);
        dialog.add(layout);
        dialog.open();
    }

    private void guardarUsuario(Usuario u, boolean editar) {
        try {
            String json = gson.toJson(u);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBase + (editar ? "/" + u.getId() : "")))
                    .method(editar ? "PUT" : "POST", HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            client.send(request, HttpResponse.BodyHandlers.ofString());
            cargarUsuarios();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generarPDF() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBase + "/pdf"))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            client.send(request, HttpResponse.BodyHandlers.discarding());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetalles(Usuario usuario) {
        Dialog dialog = new Dialog();
        dialog.add(new VerticalLayout(
                new TextField("Nombre", usuario.getNombre(), ""),
                new TextField("Apellidos", usuario.getApellidos(), ""),
                new TextField("NIF", usuario.getNif(), ""),
                new TextField("Email", usuario.getEmail(), ""),
                new TextField("Dirección", usuario.getDireccion().getCalle() + " " + usuario.getDireccion().getNumero() +
                        ", " + usuario.getDireccion().getPisoLetra() + " " + usuario.getDireccion().getCodigoPostal() +
                        " (" + usuario.getDireccion().getCiudad() + ")"),
                new TextField("Tarjeta", String.valueOf(usuario.getMetodoPago().getNumeroTarjeta())),
                new TextField("Titular", usuario.getMetodoPago().getNombreAsociado())
        ));
        dialog.open();
    }
}
