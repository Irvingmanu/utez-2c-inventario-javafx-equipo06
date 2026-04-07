module com.example.integradora {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integradora to javafx.fxml;
    opens com.example.integradora.services to javafx.fxml;
    opens com.example.integradora.controllers to javafx.fxml;
    opens com.example.integradora.repositories to javafx.fxml;
    opens com.example.integradora.modelo to javafx.base;
    exports com.example.integradora;
}