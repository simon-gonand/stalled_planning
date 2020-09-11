package com.Cale_Planning.Models;

import com.Cale_Planning.Controller.BoatController;
import com.Cale_Planning.MSAccessBase;
import com.Cale_Planning.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Boat {
    public enum categoryType{
        MENORQUIN,
        PECHE,
        PNEUMATIQUE,
        POINTU,
        SIR,
        VEDETTE;


        @Override
        public String toString() {
            switch (this){
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

        public static categoryType parse(String string){
            switch (string){
                case "Menorquin":
                    return categoryType.MENORQUIN;
                case "Peche Promenade":
                    return categoryType.PECHE;
                case "Pneumatique":
                    return categoryType.PNEUMATIQUE;
                case "Pointu":
                    return categoryType.POINTU;
                case "SIR":
                    return categoryType.SIR;
                case "Vedette":
                    return categoryType.VEDETTE;
                default: break;
            }
            return null;
        }
    }

    public enum placeType{
        PASSAGER,
        ANNUEL,
        CLUB,
        TRADITION,
        TERRE;

        @Override
        public String toString() {
            switch (this){
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

        public static placeType parse (String string){
            switch (string){
                case "Passager":
                    return placeType.PASSAGER;
                case "Annuel":
                    return placeType.ANNUEL;
                case "Club":
                    return placeType.CLUB;
                case "Tradition":
                    return placeType.TRADITION;
                case "Terre":
                    return placeType.TERRE;
                default: break;
            }
            return null;
        }
    }

    private int id;
    private String name, registration;
    private float length, width, draught, weight;
    private Adherent owner;
    private categoryType category;
    private placeType place;

    public Boat (int id){
        this.id = id;
        try {
            ResultSet attributes = Main.getDatabase().SQLSelect("SELECT * FROM Bateau WHERE ID = " + this.id);
            attributes.next();
            this.category = categoryType.parse(attributes.getString("Categorie"));
            this.place = placeType.parse(attributes.getString("Place"));
            this.name = attributes.getString("Nom");
            this.registration = attributes.getString("Immatriculation");
            this.length = attributes.getFloat("Longueur");
            this.width = attributes.getFloat("Largeur");
            this.draught = attributes.getFloat("TirantEau");
            this.weight = attributes.getFloat("Poids");
            this.owner = new Adherent(attributes.getInt("Proprietaire"), this);

        } catch (SQLException e){
            System.out.println("Select boat query error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Boat (int id, Adherent adherent){
        this.id = id;
        try {
            ResultSet attributes = Main.getDatabase().SQLSelect("SELECT * FROM Bateau WHERE ID = " + this.id);
            attributes.next();
            this.category = categoryType.parse(attributes.getString("Categorie"));
            this.place = placeType.parse(attributes.getString("Place"));
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

    public Boat(int id, String name, String registration, float length, float width, float draught, float weight, Adherent owner,
                categoryType category, placeType place) {
        this.id = id;
        this.name = name;
        this.registration = registration;
        this.length = length;
        this.width = width;
        this.draught = draught;
        this.weight = weight;
        this.owner = owner;
        this.category = category;
        this.place = place;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        BoatController.setName(this);
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
        BoatController.setRegistration(this);
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
        BoatController.setLength(this);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        BoatController.setWidth(this);
    }

    public float getDraught() {
        return draught;
    }

    public void setDraught(float draught) {
        this.draught = draught;
        BoatController.setDraught(this);
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
        BoatController.setWeight(this);
    }

    public Adherent getOwner() {
        return owner;
    }

    public void setOwner(Adherent owner) {
        this.owner = owner;
        BoatController.setOwner(this);
    }

    public categoryType getCategory() {
        return category;
    }

    public void setCategory(categoryType category) {
        this.category = category;
        BoatController.setCategory(this);
    }

    public placeType getPlace() {
        return place;
    }

    public void setPlace(placeType place) {
        this.place = place;
        BoatController.setPlace(this);
    }
}
