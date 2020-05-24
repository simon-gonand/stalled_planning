package com.Cale_Planning.Models;

import com.Cale_Planning.MSAccessBase;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Adherent {
    public enum genderType{
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
    }
    private int id, subscriptionYear, postalCode;
    private String name, surname, building, address, city, email, phone, mobile, comment;
    private Date dateOfBirth;
    private genderType gender;
    private DefaultListModel<Boat> boats;
    private MSAccessBase database;

    public Adherent (int id, MSAccessBase database){
        this.id = id;
        this.database = database;
        try {
            ResultSet attributes = database.SQLSelect("SELECT * FROM Adherent WHERE ID = " + this.id);
            attributes.next();
            switch (attributes.getString("Genre")){
                case "Monsieur" :
                    this.gender = genderType.MISTER;
                    break;
                case "Madame" :
                    this.gender = genderType.MISS;
                    break;
                case "Non Genre" :
                    this.gender = genderType.NO_GENDER;
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
            ResultSet boatsID = database.SQLSelect("SELECT ID FROM Bateau WHERE Proprietaire = " + this.id);
            while (boatsID.next()){
                boats.addElement(new Boat(boatsID.getInt("ID"), this, this.database));
            }
        } catch (SQLException e){
            System.out.println("SQL Select exception n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Adherent (int id, Boat boat, MSAccessBase database){
        this.id = id;
        this.database = database;
        try {
            ResultSet attributes = database.SQLSelect("SELECT * FROM Adherent WHERE ID = " + this.id);
            attributes.next();
            switch (attributes.getString("Genre")){
                case "Monsieur" :
                    this.gender = genderType.MISTER;
                    break;
                case "Madame" :
                    this.gender = genderType.MISS;
                    break;
                case "Non Genre" :
                    this.gender = genderType.NO_GENDER;
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
            ResultSet boatsID = database.SQLSelect("SELECT ID FROM Bateau WHERE Proprietaire = " + this.id);
            while (boatsID.next()){
                boats.addElement(boat);
            }
        } catch (SQLException e){
            System.out.println("SQL Select exception n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public int getSubscriptionYear() {
        return subscriptionYear;
    }

    public void setSubscriptionYear(int subscriptionYear) {
        this.subscriptionYear = subscriptionYear;
        try {
            database.SQLUpdate("UPDATE Adherent SET DateAdhesion = " + subscriptionYear + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Subscription Year Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
        try {
            database.SQLUpdate("UPDATE Adherent SET CodePostal = " + postalCode + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Postal Code Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        try {
            database.SQLUpdate("UPDATE Adherent SET Prenom = " + name + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Name Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        try {
            database.SQLUpdate("UPDATE Adherent SET Nom = " + surname + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Surname Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
        try {
            database.SQLUpdate("UPDATE Adherent SET Batiment = " + building + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Building Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        try {
            database.SQLUpdate("UPDATE Adherent SET Rue = " + address + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Address Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        try {
            database.SQLUpdate("UPDATE Adherent SET Ville = " + city + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("City Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        try {
            database.SQLUpdate("UPDATE Adherent SET Email = " + email + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Email Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        try {
            database.SQLUpdate("UPDATE Adherent SET Telephone = " + phone + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Phone Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        try {
            database.SQLUpdate("UPDATE Adherent SET Portable = " + mobile + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Mobile Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        try {
            database.SQLUpdate("UPDATE Adherent SET Com = " + comment + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Comment Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        try {
            database.SQLUpdate("UPDATE Adherent SET DateNaissance = " + dateOfBirth + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Date of Birth Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public genderType getGender() {
        return gender;
    }

    public void setGender(genderType gender) {
        this.gender = gender;
        String genderName = gender.toString();
        try {
            database.SQLUpdate("UPDATE Adherent SET Genre = " + genderName + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Gender Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public DefaultListModel<Boat> getBoats() {
        return boats;
    }

    public void AddBoat (Boat boat){
        this.boats.addElement(boat);
    }

    public void RemoveBoat(Boat boat){
        this.boats.removeElement(boat);
    }
}


