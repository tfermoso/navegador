module com.example.navegador {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.commons.io;

    opens com.example.navegador to javafx.fxml;
    exports com.example.navegador;

}