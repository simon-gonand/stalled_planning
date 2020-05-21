package com.Cale_Planning.Models;

import com.Cale_Planning.MSAccessBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Adherent {
    private enum genderType{
        MISTER, MISS, NO_GENDER
    }
    private int id, subscriptionYear, postalCode;
    private String name, surname, building, address, city, email, phone, mobile, comment;
    private Date dateOfBirth;
    private genderType gender;
    private MSAccessBase database;

    public Adherent (int id, MSAccessBase database){
        this.id = id;
        this.database = database;
        database.connect();
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
        String genderName = "";
        switch (gender){
            case MISTER:
                genderName = "Monsieur";
                break;
            case MISS:
                genderName = "Madame";
                break;
            case NO_GENDER:
                genderName = "Non Genre";
                break;
            default: break;
        }
        try {
            database.SQLUpdate("UPDATE Adherent SET Genre = " + genderName + " WHERE ID = " + this.id);
        } catch (SQLException e){
            System.out.println("Gender Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }
}


