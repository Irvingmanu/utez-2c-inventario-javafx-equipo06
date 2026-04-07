package com.example.integradora.controllers;

import com.example.integradora.modelo.Producto;
import com.example.integradora.services.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class MainController {


    @FXML
    private TableView<Producto> tableView;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TableColumn<Producto, String> colCategoria;

    ProductoService productoService = new ProductoService();

    private final ObservableList<Producto> data = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        tableView.setItems(data);
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        onRecargarTabla();
    }

    public void onRecargarTabla() {
        try {
            data.clear();
            List<Producto> productosDelArchivo = productoService.loadForListView();
            data.addAll(productosDelArchivo);

        } catch (Exception e) {
            mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onAbrirFormulario() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/integradora/views/FormView.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            FormController formController = fxmlLoader.getController();
            formController.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Formulario del Producto");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void onEliminar() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        Producto productoSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (index >= 0) {
            try {
                String codigo = productoSeleccionado.getCodigo();
                String nombre = productoSeleccionado.getNombre();
                String precio = String.valueOf(productoSeleccionado.getPrecio());
                String stock = String.valueOf(productoSeleccionado.getStock());
                String categoria = productoSeleccionado.getCategoria();

                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Aviso");
                confirmacion.setHeaderText(null);
                confirmacion.setContentText("Quieres eliminar el producto seleccionado?");

                confirmacion.showAndWait();

                if (confirmacion.getResult() == javafx.scene.control.ButtonType.OK) {
                    productoService.eliminarProducto(index, codigo, nombre, precio, stock, categoria);
                    data.remove(productoSeleccionado);

                    mostrarMensaje("Éxito", "Producto eliminado correctamente", Alert.AlertType.INFORMATION);
                }

            } catch (Exception e) {
                mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Aviso", "Debe seleccionar primero para eliminar", Alert.AlertType.WARNING);
        }
    }

    public void onEditar() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        Producto productoSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (index >= 0) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/integradora/views/FormView.fxml"));

                Scene scene = new Scene(fxmlLoader.load());

                FormController formController = fxmlLoader.getController();
                formController.setMainController(this);

                Stage stage = new Stage();
                stage.setTitle("Formulario del Producto");
                stage.setScene(scene);
                formController.cargarProducto(productoSeleccionado, index);
                stage.show();

            } catch (Exception e) {
                mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Aviso", "Debe seleccionar primero para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onSearch() {
        String filtro = txtBuscar.getText().toLowerCase();
        if (filtro.isEmpty()) {
            tableView.setItems(data);
        } else {
            tableView.setItems(data.filtered(producto ->
                    producto.getNombre().toLowerCase().contains(filtro) || producto.getCodigo().toLowerCase().contains(filtro)));
        }
    }

    public void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}