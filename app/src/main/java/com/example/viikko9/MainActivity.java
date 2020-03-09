package com.example.viikko9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        luexml();
    }



    public void luexml(){
        try {
            DocumentBuilder rakentaja = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String osoite = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = rakentaja.parse(osoite);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList lista = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            for(int i=0; i < lista.getLength(); i++){
                Node node = lista.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    System.out.print("Teatterin ID ");
                    System.out.println(element.getElementsByTagName("ID").item(0).getTextContent());
                    System.out.print("Teatterin NIMI ");
                    System.out.println(element.getElementsByTagName("Name").item(0).getTextContent());
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("###################DONE#################");
        }
    }


}

