module com.example.navegador {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.navegador to javafx.fxml;
    exports com.example.navegador;

}