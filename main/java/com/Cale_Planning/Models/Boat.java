package com.Cale_Planning.Models;

import com.Cale_Planning.MSAccessBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Boat {
    public enum categoryType{
        MENORQUIN,
        PECHE,
        PNEUMATIQUE,
        POINTU,
        SIR,
        VEDETTE
    }

    public enum placeType{
        PASSAGER,
        ANNUEL,
        CLUB,
        TRADITION,
        TERRE
    }

    private int id;
    private String name, registration;
    private float length, width, draught, weight;
    private Adherent owner;
    private categoryType category;
    private placeType place;
    private MSAccessBase database;

    public Boat (int id, MSAccessBase database){
        this.id = id;
        this.database = database;
        try {
            ResultSet attributes = database.SQLSelect("SELECT * FROM Bateau WHERE ID = " + this.id);
            attributes.next();
            StringToCategoryType(attributes.getString("Categorie"));
            StringToPlaceType(attributes.getString("Place"));
            this.name = attributes.getString("Nom");
            this.registration = attributes.getString("Immatriculation");
            this.length = attributes.getFloat("Longueur");
            this.width = attributes.getFloat("Largeur");
            this.draught = attributes.getFloat("TirantEau");
            this.weight = attributes.getFloat("Poids");
            this.owner = new Adherent(attributes.getInt("Proprietaire"), this, database);

        } catch (SQLException e){
            System.out.println("Select boat query error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Boat (int id, Adherent adherent, MSAccessBase database){
        this.id = id;
        this.database = database;
        try {
            ResultSet attributes = database.SQLSelect("SELECT * FROM Bateau WHERE ID = " + this.id);
            attributes.next();
            StringToCategoryType(attributes.getString("Categorie"));
            StringToPlaceType(attributes.getString("Place"));
            this.name = attributes.getString("Nom");
            this.registration = attributes.getString("Immatriculation");
            this.length = attributes.getFloat("Longueur");
            this.width = attributes.getFloat("Largeur");
            this.draught = attributes.getFloat("TirantEau");
            this.weight = attributes.getFloat("Poids");
            this.owner = adherent;

        } catch (SQLException e){
            System.out.println("Select boat query error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    private void StringToCategoryType (String categoryName){
        switch (categoryName){
            case "Menorquin":
                this.category = categoryType.MENORQUIN;
                break;
            case "Peche Promenade":
                this.category = categoryType.PECHE;
                break;
            case "Pneumatique":
                this.category = categoryType.PNEUMATIQUE;
                break;
            case "Pointu":
                this.category = categoryType.POINTU;
                break;
            case "SIR":
                this.category = categoryType.SIR;
                break;
            case "Vedette":
                this.category = categoryType.VEDETTE;
                break;
            default: break;
        }
    }

    private String CategoryTypeToString (categoryType category){
        switch (category){
            case MENORQUIN:
                return "Menorquin";
            case PECHE:
                return "Peche Promenade";
            case POINTU:
                return "Pointu";
            case PNEUMATIQUE:
                return "Pneumatique";
            case SIR:
                return "SIR";
            case VEDETTE:
                return "Vedette";
            default: break;
        }
        return null;
    }

    private void StringToPlaceType (String placeName){
        switch (placeName){
            case "Passager":
                this.place = placeType.PASSAGER;
                break;
            case "Annuel":
                this.place = placeType.ANNUEL;
                break;
            case "Club":
                this.place = placeType.CLUB;
                break;
            case "Tradition":
                this.place = placeType.TRADITION;
                break;
            case "Terre":
                this.place = placeType.TERRE;
                break;
            default: break;
        }
    }

    private String PlaceTypeToString (placeType place){
        switch (place){
            case PASSAGER:
                return "Passager";
            case ANNUEL:
                return "Annuel";
            case CLUB:
                return "Club";
            case TRADITION:
                return "Tradition";
            case TERRE:
                return "Terre";
            default: break;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        try {
            database.SQLUpdate("UPDATE Bateau SET Name = " + name + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Boat Name Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
        try {
            database.SQLUpdate("UPDATE Bateau SET Immatriculation = " + registration + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Registration Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
        try {
            database.SQLUpdate("UPDATE Bateau SET Longueur = " + length + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Length Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        try {
            database.SQLUpdate("UPDATE Bateau SET Largeur = " + width + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Width Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public float getDraught() {
        return draught;
    }

    public void setDraught(float draught) {
        this.draught = draught;
        try {
            database.SQLUpdate("UPDATE Bateau SET TirantEau = " + draught + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Draught Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
        try {
            database.SQLUpdate("UPDATE Bateau SET Poids = " + weight + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Weight Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Adherent getOwner() {
        return owner;
    }

    public void setOwner(Adherent owner) {
        this.owner = owner;
        try {
            database.SQLUpdate("UPDATE Bateau SET Proprietaire = " + owner.getId() + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Owner Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public categoryType getCategory() {
        return category;
    }

    public void setCategory(categoryType category) {
        this.category = category;
        try {
            database.SQLUpdate("UPDATE Bateau SET Category = " + CategoryTypeToString(category) + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Category Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public placeType getPlace() {
        return place;
    }

    public void setPlace(placeType place) {
        this.place = place;
        try {
            database.SQLUpdate("UPDATE Bateau SET Place = " + PlaceTypeToString(place) + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Place Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }
}
