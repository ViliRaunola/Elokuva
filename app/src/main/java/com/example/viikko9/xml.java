package com.example.viikko9;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class xml {
    Kaikki kaikki = Kaikki.getInstance();

    private static xml k = new xml();

    private xml(){
    }

    public static xml getInstance(){
        return k;
    }

    public ArrayList<String> naytokset = new ArrayList<>();


    public void luexml(){
        try {
            kaikki.lista.clear();
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
                    Teatteri teatteri = new Teatteri(element.getElementsByTagName("ID").item(0).getTextContent(), element.getElementsByTagName("Name").item(0).getTextContent());
                    kaikki.lisaa(teatteri);

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


    public void luexmlElokuvat(String id, String pvm){
        try {
            naytokset.clear(); //Puhdistetaan lista ennenkuin siihen lisätään enempää kamaa
            DocumentBuilder rakentaja = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String osoite = "https://www.finnkino.fi/xml/Schedule/?area=" + id + "&dt=" + pvm;
            Document doc = rakentaja.parse(osoite);
            doc.getDocumentElement().normalize();
            NodeList lista = doc.getDocumentElement().getElementsByTagName("Show");
            for (int i = 0; i < lista.getLength(); i++) {
                Node node = lista.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String[] aika = element.getElementsByTagName("dttmShowStart").item(0).getTextContent().split("T", 2);
                    String paiva_ja_naytos = aika[0] + " " + aika[1] + " " + element.getElementsByTagName("Title").item(0).getTextContent();
                    naytokset.add(paiva_ja_naytos);
                }
            }
        }catch(SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("################### DONE %%%%%%%%%%%%%%%%%%%");
        }

    }

}
