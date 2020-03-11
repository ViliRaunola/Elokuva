package com.example.viikko9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Button hae_nappi = findViewById(R.id.Hae_nappi);

        final ListView list = (ListView) findViewById(R.id.lista);
        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, xml2.naytokset);


        spinner = findViewById(R.id.spinner);
        final ArrayAdapter<Teatteri> adapter = new ArrayAdapter<Teatteri>(this,android.R.layout.simple_gallery_item, kaikki.lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        hae_nappi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dF = new SimpleDateFormat("dd.MM.yyyy");
                Date d = new Date();
                xml2.luexml();

                //Tehdään virheen tarkistus spinnerin valinnalle
                if(spinner.getSelectedItem() != null) {
                    Teatteri teatteri = (Teatteri) spinner.getSelectedItem();
                    String id = teatteri.getId();
                    xml2.luexmlElokuvat(id, dF.format(d));
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    xml2.luexmlElokuvat("1034",dF.format(d));
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                list.setAdapter(adapter2);
            }
        });
    }



}

