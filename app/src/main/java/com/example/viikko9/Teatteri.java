package com.example.viikko9;

import java.util.ArrayList;

public class Teatteri {
    private String id;
    private String nimi;
    public ArrayList<String> teatterin_elokuvat = new ArrayList<>();


    public Teatteri(String id, String nimi){
        this.id = id;
        this.nimi = nimi;
    }

    public String getName(){
        return this.nimi;
    }

    public String getId(){
        return this.id;
    }


    @Override
    public String toString(){
        return this.nimi;
    }

}
