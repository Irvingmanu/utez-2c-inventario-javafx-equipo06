package com.example.integradora.services;

import com.example.integradora.modelo.Producto;
import com.example.integradora.repositories.FileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    FileRepository fileRepository = new FileRepository();

    public List<Producto> loadForListView() throws IOException {
        List<String> lines = fileRepository.readAllLines();
        List<Producto> result = new ArrayList<>();

        for (String line : lines) {
            if (line == null || line.isBlank()) continue;

            String[] parts = line.split(",");
            String codigo = parts[0];
            String nombre = parts[1];
            String precio = parts[2];
            String stock = parts[3];
            String categoria = parts[4];

            double precioValido = Double.parseDouble(precio.trim());
            int stockValido = Integer.parseInt(stock.trim());

            Producto producto = new Producto(codigo, nombre, precioValido, stockValido, categoria);
            result.add(producto);
        }
        return result;
    }

    public void agregarProducto(String codigo, String nombre, String precio, String stock, String categoria) throws IOException {
        validarProducto(codigo, nombre, precio, stock, categoria);
        if (existeCodigo(codigo)){
            throw new IllegalArgumentException("Ya existe un producto con ese codigo");
        }

        fileRepository.addNewLine(codigo + "," + nombre + "," + precio + "," + stock + "," + categoria+"\n");
    }

    public void actualizarProducto(int index, String codigo, String nombre, String precio, String stock, String categoria) throws IOException {
        validarProducto(codigo, nombre, precio, stock, categoria);

        List<String> listaOriginal = fileRepository.readAllLines();
        List<String> cleanLines = new ArrayList<>();

        for (String line : listaOriginal) {
            if (line != null && !line.isEmpty()) {
                cleanLines.add(line);
            }
        }

        cleanLines.set(index, codigo + "," + nombre + "," + precio + "," + stock + "," + categoria);

        fileRepository.saveFile(cleanLines);
    }

    public void eliminarProducto(int index, String codigo, String nombre, String precio, String stock, String categoria) throws IOException {

        validarProducto(codigo, nombre, precio, stock, categoria);

        List<String> listaOriginal = fileRepository.readAllLines();
        List<String> cleanLines = new ArrayList<>();

        for (String line : listaOriginal) {
            if (line != null && !line.isEmpty()) {
                cleanLines.add(line);
            }
        }

        cleanLines.remove(index);

        fileRepository.saveFile(cleanLines);
    }
    public boolean existeCodigo(String codigoNuevo) throws IOException {
        List<Producto> listaProductos = loadForListView();
        for (Producto producto : listaProductos){
            if (producto.getCodigo().equals(codigoNuevo)) {
                return true;
            }
        }
        return false;
    }

    public void validarProducto(String codigo, String nombre, String precio, String stock, String categoria){
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo no debe estar vacio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no debe estar vacio");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre debe tener minimo 3 caracteres");
        }
        try {
            String pre = (precio == null) ? "" : precio.trim();
            double precioValido = Double.parseDouble(pre);

            if (precioValido < 1) throw new IllegalArgumentException("El precio debe ser mayor a 0");

        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                throw new IllegalArgumentException("El precio debe ser numerico");
            }
            throw e;
        }
        try {
            String sk = (stock == null) ? "" : stock.trim();
            int stockValido = Integer.parseInt(sk);

            if (stockValido < 0) throw new IllegalArgumentException("El stock debe ser mayor o igual a 0");

        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                throw new IllegalArgumentException("El stock debe ser un numero entero");
            }
            throw e;
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("La categoria no debe estar vacia");
        }
    }
}
