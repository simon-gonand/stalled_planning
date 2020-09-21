package com.Cale_Planning;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Parser {
    private File inputFile;

    public Parser(String fileName){
        inputFile = new File(fileName);
    }

    public void importAdherents(JDesktopPane mainPane) throws ParserConfigurationException, IOException, SAXException, ParseException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        NodeList adherentList = doc.getElementsByTagName("TABLE_Adherent");
        for (int i = 0; i < adherentList.getLength(); ++i){
            Node adherentNode = adherentList.item(i);
            if (adherentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element adherentElement = (Element) adherentNode;
                int subscriptionYear = 0;
                try {
                    if (!adherentElement.getElementsByTagName("Date_d_adhésion").item(0).getTextContent().equals(""))
                        subscriptionYear = Integer.valueOf(
                                adherentElement.getElementsByTagName("Date_d_adhésion").item(0).getTextContent().substring(6));
                } catch (NumberFormatException e){
                    JInternalFrame frame = mainPane.getAllFrames()[0];
                    JOptionPane.showMessageDialog(frame, "La date d'inscription de l'adhérent " +
                            adherentElement.getElementsByTagName("Nom").item(0).getTextContent() + " " +
                            adherentElement.getElementsByTagName("Prénom").item(0).getTextContent() +
                            " a mal été saisie par l'utilisateur", "Problème avec la date d'inscription", JOptionPane.ERROR_MESSAGE);
                }
                String postalCode = adherentElement.getElementsByTagName("Code_postal").item(0).getTextContent();
                try {
                    if (postalCode.length() == 5)
                        postalCode = postalCode.substring(0, 5);
                } catch (StringIndexOutOfBoundsException e){
                    JInternalFrame frame = mainPane.getAllFrames()[0];
                    JOptionPane.showMessageDialog(frame, "Le code postal de l'adhérent " +
                            adherentElement.getElementsByTagName("Nom").item(0).getTextContent() + " " +
                            adherentElement.getElementsByTagName("Prénom").item(0).getTextContent() +
                            " a mal été saisi par l'utilisateur", "Problème avec le code postal", JOptionPane.ERROR_MESSAGE);
                }

                Date date = new Date();
                if (!adherentElement.getElementsByTagName("Né_e__le").item(0).getTextContent().equals("")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                    date = formatter.parse(adherentElement.getElementsByTagName("Né_e__le").item(0).getTextContent());
                }

                Adherent.GenderType genderType = Adherent.GenderType.parse(
                        adherentElement.getElementsByTagName("Sexe").item(0).getTextContent());


                String phone = adherentElement.getElementsByTagName("Tél_Domicile").item(0).getTextContent();
                String mobile = adherentElement.getElementsByTagName("Tél_Portable").item(0).getTextContent();
                if (phone.length() != 0 && phone.charAt(0) == '_')
                    phone = "";
                if (mobile.length() != 0 && mobile.charAt(0) == '_')
                    mobile = "";

                if (phone.length() == 14)
                    phone = phone.replace(".", " ");
                else {
                    int index = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    while (index < phone.length()){
                        stringBuilder.append(phone.charAt(index));
                        if (index % 2 == 1)
                            stringBuilder.append(" ");
                        ++index;
                    }
                    phone = stringBuilder.toString();
                }

                if (mobile.length() == 14)
                    mobile = mobile.replace(".", " ");
                else {
                    int index = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    while (index < mobile.length()){
                        stringBuilder.append(mobile.charAt(index));
                        if (index % 2 == 1)
                            stringBuilder.append(" ");
                        ++index;
                    }
                    mobile = stringBuilder.toString();
                }
                new Adherent(subscriptionYear, postalCode,
                        adherentElement.getElementsByTagName("Prénom").item(0).getTextContent(),
                        adherentElement.getElementsByTagName("Nom").item(0).getTextContent(),
                        adherentElement.getElementsByTagName("Complément_d_adresse").item(0).getTextContent(),
                        adherentElement.getElementsByTagName("N__et_nom_de_la_voie").item(0).getTextContent(),
                        adherentElement.getElementsByTagName("Commune").item(0).getTextContent(),
                        adherentElement.getElementsByTagName("E-mail").item(0).getTextContent(),
                        phone, mobile, "", date, genderType);
            }
        }
    }
}
