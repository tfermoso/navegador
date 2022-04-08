package com.example.navegador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HelloController{
    @FXML
    private Label welcomeText,lblRespuesta;
    @FXML
    private TextField txtURL;
    @FXML
    private ImageView imgBandera;


    public void initialize() {
        txtURL.setText("https://");
    }
    @FXML
    protected void onButtonClick() {
        String url=txtURL.getText();
        if(url.indexOf("https://")<0){
            url="https://"+url;
        }
        try {
            String respuesta=peticionHttpGet(url);
            Gson gson=new Gson();

            //JsonArray arry = new JsonParser().parse(respuesta).getAsJsonArray();
            JsonArray jsonArray = JsonParser.parseString(respuesta).getAsJsonArray();
            String pais=jsonArray.get(0).getAsJsonObject().get("name").getAsJsonObject().get("nativeName").getAsJsonObject().get("spa").getAsJsonObject().get("official").getAsString();
            String bandera=jsonArray.get(0).getAsJsonObject().get("flags").getAsJsonObject().get("png").getAsString();
            imgBandera.setImage(new Image(bandera));
            lblRespuesta.setText(pais);




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String peticionHttpGet(String urlParaVisitar) throws Exception {
        // Esto es lo que vamos a devolver
        StringBuilder resultado = new StringBuilder();
        // Crear un objeto de tipo URL
        URL url = new URL(urlParaVisitar);

        // Abrir la conexión e indicar que será de tipo GET
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        // Búferes para leer
        BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String linea;
        // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
        while ((linea = rd.readLine()) != null) {
            resultado.append(linea);
        }
        // Cerrar el BufferedReader
        rd.close();
        // Regresar resultado, pero como cadena, no como StringBuilder
        return resultado.toString();
    }
}