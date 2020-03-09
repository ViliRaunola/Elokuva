package com.example.viikko9;

import java.util.ArrayList;


//On singleton luokka, sillä ei tarvita enempää
public class Kaikki {
    private static Kaikki k = new Kaikki();

    private Kaikki(){
    }

    public static Kaikki getInstance(){
        return k;
    }


    public ArrayList<Teatteri> lista = new ArrayList<Teatteri>();

    public void lisaa(Teatteri i){
        lista.add(i);
    }

    public void tulosta(){
        for(int i = 0; i<lista.size(); i++){
            System.out.println("Nimi ja id on ###################");
            System.out.println(lista.get(i).getName());
            System.out.println(lista.get(i).getId());
        }
    }

}
