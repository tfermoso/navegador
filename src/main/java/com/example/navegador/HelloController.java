package com.example.navegador;

import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class HelloController{
    @FXML
    private Label welcomeText,lblRespuesta;
    @FXML
    private TextField txtURL;
    @FXML
    private ImageView imgBandera;
    private Pokemon pokemon;
    private int pos;


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
            //String respuesta=peticionHttpGet(url)
            String url2="https://pokeapi.co/api/v2/pokemon/"+txtURL.getText()+"/";
            String respuesta=peticionHttpGet(url2);
            Gson gson=new Gson();
            JsonElement pk=JsonParser.parseString(respuesta);

            String name=pk.getAsJsonObject().get("name").getAsString();
            int id=((JsonObject) pk).get("id").getAsInt();
           pokemon=Pokemon.crearPokemon(name,id);
            String img1=((JsonObject) pk).get("sprites").getAsJsonObject().get("back_default").getAsString();
            String img2=((JsonObject) pk).get("sprites").getAsJsonObject().get("back_shiny").getAsString();
            String img3=((JsonObject) pk).get("sprites").getAsJsonObject().get("front_default").getAsString();
            String img4=((JsonObject) pk).get("sprites").getAsJsonObject().get("front_shiny").getAsString();
            pokemon.getImgs().add(img1);
            pokemon.getImgs().add(img2);
            pokemon.getImgs().add(img3);
            pokemon.getImgs().add(img4);
            Image img=new Image(pokemon.getImgs().get(2));


            guardarImagen(pokemon.getImgs().get(2),pokemon.getName()+".png","C:\\Users\\usuario\\Desktop\\pokemon");



            imgBandera.setImage(new Image(pokemon.getImgs().get(2)));
            pos=2;
            lblRespuesta.setText(pokemon.getName());





        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void siguienteImagen(){
        pos++;
        if(pos>3) pos=0;
        imgBandera.setImage(new Image(pokemon.getImgs().get(pos)));


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

    private void guardarImagen(String urlString, String filename,String savePath) throws IOException {
        URL url = new URL(urlString);
        // conexión abierta
        URLConnection con = url.openConnection();
        // Establece el tiempo de espera de la solicitud en 5 s
        con.setConnectTimeout(5*1000);
        // flujo de entrada
        InputStream is = con.getInputStream();

        // Búfer de datos de 1K
        byte[] bs = new byte[1024];
        // La longitud de los datos leídos
        int len;
        // flujo de archivo de salida
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"/"+filename);
        // empieza a leer
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // Finalizar, cerrar todos los enlaces
        os.close();
        is.close();
    }
}