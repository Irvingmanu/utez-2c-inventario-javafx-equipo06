Proyecto Intregrador Sistema de gestión de inventario

Este proyecto es una aplicación de escritorio desarrollada en Java utilizando JavaFX. Permite gestionar un catálogo de productos 
con operaciones CRUD y un sistema de archivos persistente.

-Cómo funciona

Posee la capacidad de registrar productos introduciendo un código, nombre, precio, stock y categoría del producto.
Los productos se guardan automáticamente en un archivo productos.csv el cual funciona como nuestra base de datos local.
El programa busca o crea automáticamente una carpeta llamada archivo en la raíz del proyecto. Dentro de la carpeta genera 
el archivo productos.csv con codificación UTF-8, lo que permite el uso de caracteres especiales sin errores.
Usa vistas FXML que nos permiten darle una interfaz gráfica, intuitiva y clara para el usuario.

-Tecnologías utilizadas
Lenguaje: Java 21

Framework de GUI: JavaFX

Patrón de Diseño: Modelo-Vista-Controlador (MVC).

-Estructura del código
Se dividió en distintas clases cumpliendo con el patrón de diseño MVC, las cuales fueron:

com.example.integradora.modelo = Contiene la clase Producto que define los atributos que tendrá cada producto (código, nombre, precio, stock, categoría).

com.example.integradora.repositories = La clase FileRepository se encarga de la lectura y escritura física del archivo CSV.

com.example.integradora.controllers = Es la lógica de la interfaz, conecta la vista con el backend (MainController y FormController).

com.example.integradora.services = Procesa la lógica de negocio antes de guardar los datos, hace validaciones y se comunica con FileRepository para manipular el archivo.

-Pasos para la instalación y uso

1.- Clonar el repositorio.

2.- Abrir el proyecto en un editor de código.

3.- Asegurarse de tener condigurado el SDK de Java y las liberías de JavaFX

4.- Localizar la clase Launcher y correr el programa.

-Autores

Irvingmanu --- Irving Manuel Flores Torrescano

bebecanijo2 --- Axel Sanchez Aldana Aragon 

Todos los derechos reservados ©️
