package com.example.navegador;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private String name;
    private int id;
    private List<String> imgs;

    private Pokemon(String name, int id) {
        this.name = name;
        this.id = id;
        imgs=new ArrayList<>();

    }
    public static Pokemon crearPokemon(String name, int id){

        return new Pokemon(name,id);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
