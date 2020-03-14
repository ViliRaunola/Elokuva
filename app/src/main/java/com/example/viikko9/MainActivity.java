package com.example.viikko9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    Kaikki kaikki = Kaikki.getInstance(); //Luodaan instanssi luokasta Kaikki, johon tallennetaan elokuvateatterit
    xml xml2 = xml.getInstance();
    private Spinner spinner;
    private static final String TAG = "MainActivity";
    ArrayList<String> elokuvat_ajalta = new ArrayList<>(); //Luodaan lista johon tallennetaan elokuvat jotka alkavat halutulla aikavälillä


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Button hae_nappi = findViewById(R.id.Hae_nappi);
        final EditText pvm_kentta = findViewById(R.id.pvm_kentta);
        final EditText kloa_kentta = findViewById(R.id.kloa_kentta);
        final EditText klol_kentta = findViewById(R.id.klol_kentta);

        final ListView list = (ListView) findViewById(R.id.lista);
        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, xml2.naytokset);



        spinner = findViewById(R.id.spinner);
        final ArrayAdapter<Teatteri> adapter = new ArrayAdapter<Teatteri>(this,android.R.layout.simple_gallery_item, kaikki.lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final ArrayAdapter adapter3 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, elokuvat_ajalta);

        hae_nappi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pvm, kloa, klol;
                int tarkistus = 1;
                pvm = pvm_kentta.getText().toString();
                kloa = kloa_kentta.getText().toString(); //14:22
                klol = klol_kentta.getText().toString(); //16:10
                xml2.luexml();

                if(pvm == ""){
                    DateFormat dF = new SimpleDateFormat("dd.MM.yyyy");
                    Date d = new Date();
                    pvm = dF.format(d);
                }



                //Tehdään virheen tarkistus spinnerin valinnalle
                if(spinner.getSelectedItem() != null) {
                    Teatteri teatteri = (Teatteri) spinner.getSelectedItem();
                    String id = teatteri.getId();
                    xml2.luexmlElokuvat(id, pvm);
                    System.out.println("KELLO ALKU NÄYTTÄÄ TÄLLE!!!!!! ###########" + kloa + "#####################");
                    if(kloa.trim().length() > 0){//Jos kellolle on annettu parametrit muutetaan listaa
                        tarkistus = 0;
                        elokuvat_ajalta.clear();
                        int alku, loppu;
                        alku = Integer.parseInt(kloa); //Muutetaan syöte luvuiksi
                        loppu = Integer.parseInt(klol);
                        for(int i = 0; i < xml2.naytokset.size(); i++){//käydään jokainen alkio läpi erikseen
                            String temp, temp4;
                            String[] temp2;
                            String[] temp3;
                            int aika;
                            temp = xml2.naytokset.get(i);
                            temp2 = temp.split(" "); //alkio 0: 2020-03-12   // alkio 1: 12:30
                            temp3 = temp2[1].split(":"); //alkio 0: 12   //alkio 1: 30
                            temp4 = temp3[0] + temp3[1];
                            aika = Integer.parseInt(temp4); //Yksi aika sotilas muodossa eli 1230

                            if( alku <= aika && aika <= loppu ){
                                elokuvat_ajalta.add(temp);
                            }
                        }
                        list.setAdapter(adapter3);
                        adapter3.notifyDataSetChanged();
                    }else {
                        spinner.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                xml2.luexmlElokuvat("1014",pvm); //Jos ei olla valittu mitään spinneristä haetaan suoraan pääkaupunkiseudun elokuvat
                spinner.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                }
                if(tarkistus == 1){ //Katsotaan onko ohjelma käynyt kellon aika vaiheessa
                    list.setAdapter(adapter2);
                }

            }
        });
    }



}

