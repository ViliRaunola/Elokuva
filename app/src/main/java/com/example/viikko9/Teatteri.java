package com.example.viikko9;

public class Teatteri {
    private String id;
    private String nimi;

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
