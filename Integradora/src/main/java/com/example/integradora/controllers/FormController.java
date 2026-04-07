package com.example.integradora.controllers;

import com.example.integradora.modelo.Producto;
import com.example.integradora.services.ProductoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormController {
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtCategoria;

    ProductoService productoService = new ProductoService();
    private MainController mainController;

    private int indexEdicion = -1;

    public void cargarProducto(Producto producto, int index) {
        this.indexEdicion = index;
        txtCodigo.setText(producto.getCodigo());
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));
        txtCategoria.setText(producto.getCategoria());
    }

    public void setMainController(MainController controladorReal) {
        this.mainController = controladorReal;
    }

    public void onGuardar() {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String precio = txtPrecio.getText();
            String stock = txtStock.getText();
            String categoria = txtCategoria.getText();

            if (indexEdicion == -1) {
                productoService.agregarProducto(codigo, nombre, precio, stock, categoria);
                mostrarMensaje("Exito", "El producto fue agregado con exito", Alert.AlertType.INFORMATION);

                Stage stage = (Stage) txtCodigo.getScene().getWindow();
                stage.close();
                mainController.onRecargarTabla();
            } else {
                productoService.actualizarProducto(indexEdicion, codigo, nombre, precio, stock, categoria);
                mostrarMensaje("Exito", "El producto fue actualizado con exito", Alert.AlertType.INFORMATION);

                Stage stage = (Stage) txtCodigo.getScene().getWindow();
                stage.close();
                mainController.onRecargarTabla();
            }
        } catch (Exception e) {
            mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void onCancelar() {
        Stage stage = (Stage) txtCodigo.getScene().getWindow();
        stage.close();
    }

    public void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
