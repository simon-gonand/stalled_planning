package com.Cale_Planning.Models;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.MSAccessBase;
import com.Cale_Planning.Main;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Adherent {
    public enum GenderType{
        MISTER, MISS, NO_GENDER;

        @Override
        public String toString() {
            switch (this){
                case MISTER:
                    return "Monsieur";
                case MISS:
                    return "Madame";
                case NO_GENDER:
                    return "Non genré";
            };
            return null;
        }

        public static GenderType parse (String s){
            switch (s){
                case "Monsieur":
                    return MISTER;
                case "Madame":
                    return MISS;
                case "Non genré":
                    return NO_GENDER;
                default: break;
            }
            return null;
        }
    }
    private int id, subscriptionYear, postalCode;
    private String name, surname, building, address, city, email, phone, mobile, comment;
    private Date dateOfBirth;
    private GenderType gender;
    private DefaultListModel<Boat> boats;

    public Adherent (int id){
        this.id = id;
        try {
            ResultSet attributes = Main.getDatabase().SQLSelect("SELECT * FROM Adherent WHERE ID = " + this.id);
            attributes.next();
            this.gender = GenderType.parse(attributes.getString("Genre"));
            this.name = attributes.getString("Prenom");
            this.surname = attributes.getString("Nom");
            this.building = attributes.getString("Batiment");
            this.address = attributes.getString("Rue");
            this.city = attributes.getString("Ville");
            this.postalCode = attributes.getInt("CodePostal");
            this.dateOfBirth = attributes.getDate("DateNaissance");
            this.subscriptionYear = attributes.getInt("DateAdhesion");
            this.email = attributes.getString("Email");
            this.phone = attributes.getString("Telephone");
            this.mobile = attributes.getString("Portable");
            this.comment = attributes.getString("Com");

            boats = new DefaultListModel<Boat>();
            ResultSet boatsID = Main.getDatabase().SQLSelect("SELECT ID FROM Bateau WHERE Proprietaire = " + this.id);
            while (boatsID.next()){
                boats.addElement(new Boat(boatsID.getInt("ID"), this));
            }
        } catch (SQLException e){
            System.out.println("SQL Select exception n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Adherent (int id, Boat boat){
        this.id = id;
        try {
            ResultSet attributes = Main.getDatabase().SQLSelect("SELECT * FROM Adherent WHERE ID = " + this.id);
            attributes.next();
            switch (attributes.getString("Genre")){
                case "Monsieur" :
                    this.gender = GenderType.MISTER;
                    break;
                case "Madame" :
                    this.gender = GenderType.MISS;
                    break;
                case "Non Genre" :
                    this.gender = GenderType.NO_GENDER;
                    break;
                default : break;
            }
            this.name = attributes.getString("Prenom");
            this.surname = attributes.getString("Nom");
            this.building = attributes.getString("Batiment");
            this.address = attributes.getString("Rue");
            this.city = attributes.getString("Ville");
            this.postalCode = attributes.getInt("CodePostal");
            this.dateOfBirth = attributes.getDate("DateNaissance");
            this.subscriptionYear = attributes.getInt("DateAdhesion");
            this.email = attributes.getString("Email");
            this.phone = attributes.getString("Telephone");
            this.mobile = attributes.getString("Portable");
            this.comment = attributes.getString("Com");

            boats = new DefaultListModel<Boat>();
            ResultSet boatsID = Main.getDatabase().SQLSelect("SELECT ID FROM Bateau WHERE Proprietaire = " + this.id);
            while (boatsID.next()){
                boats.addElement(boat);
            }
        } catch (SQLException e){
            System.out.println("SQL Select exception n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Adherent(int subscriptionYear, int postalCode, String name, String surname, String building, String address,
                    String city, String email, String phone, String mobile, String comment, Date dateOfBirth, GenderType gender) {
        this.subscriptionYear = subscriptionYear;
        this.postalCode = postalCode;
        this.name = name;
        this.surname = surname;
        this.building = building;
        this.address = address;
        this.city = city;
        this.email = email;
        if (phone.equals("              "))
            phone = "";
        this.phone = phone;
        if (mobile.equals("              "))
            mobile = "";
        this.mobile = mobile;
        this.comment = comment;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        int id = AdherentController.addAdherent(this);
        if (id != 0)
            this.id = id;
    }

    @Override
    public String toString() {
        return this.surname + " " + this.name;
    }

    public int getId() {
        return id;
    }

    public int getSubscriptionYear() {
        return subscriptionYear;
    }

    public void setSubscriptionYear(int subscriptionYear) {
        this.subscriptionYear = subscriptionYear;
        AdherentController.setSubscriptionYear(this);
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
        AdherentController.setPostalCode(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        AdherentController.setName(this);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        AdherentController.setSurname(this);
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
        AdherentController.setBuilding(this);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        AdherentController.setAddress(this);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        AdherentController.setCity(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        AdherentController.setEmail(this);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.equals("              "))
            phone = "";
        this.phone = phone;
        AdherentController.setPhone(this);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        if (mobile.equals("              "))
            mobile = "";
        this.mobile = mobile;
        AdherentController.setMobile(this);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        AdherentController.setComment(this);
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        AdherentController.setDateOfBirth(this);
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
        AdherentController.setGender(this);
    }

    public DefaultListModel<Boat> getBoats() {
        return boats;
    }

    public void addBoat (Boat boat){
        this.boats.addElement(boat);
    }

    public void removeBoat(Boat boat){
        this.boats.removeElement(boat);
    }
}


