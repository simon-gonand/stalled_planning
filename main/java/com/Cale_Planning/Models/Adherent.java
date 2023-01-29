package com.Cale_Planning.Models;

import com.Cale_Planning.Controller.AdherentController;
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
                case "Masculin":
                    return MISTER;
                case "Madame":
                    return MISS;
                case "Féminin":
                    return MISS;
                case "Non genré":
                    return NO_GENDER;
                case "Autre":
                    return NO_GENDER;
            }
            return null;
        }
    }
    private int id, subscriptionYear, idComptable;
    private String name, surname, additional, address, city, postalCode, email, phone, mobile, comment;
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
            this.additional = attributes.getString("Batiment");
            this.address = attributes.getString("Rue");
            this.city = attributes.getString("Ville");
            this.postalCode = attributes.getString("CodePostal");
            this.dateOfBirth = attributes.getDate("DateNaissance");
            this.subscriptionYear = attributes.getInt("DateAdhesion");
            this.email = attributes.getString("Email");
            this.phone = attributes.getString("Telephone");
            this.mobile = attributes.getString("Portable");
            this.comment = attributes.getString("Com");
            this.idComptable = attributes.getInt("IDComptable");

            boats = new DefaultListModel<Boat>();

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
            this.gender = GenderType.parse(attributes.getString("Genre"));
            this.name = attributes.getString("Prenom");
            this.surname = attributes.getString("Nom");
            this.additional = attributes.getString("Batiment");
            this.address = attributes.getString("Rue");
            this.city = attributes.getString("Ville");
            this.postalCode = attributes.getString("CodePostal");
            this.dateOfBirth = attributes.getDate("DateNaissance");
            this.subscriptionYear = attributes.getInt("DateAdhesion");
            this.email = attributes.getString("Email");
            this.phone = attributes.getString("Telephone");
            this.mobile = attributes.getString("Portable");
            this.comment = attributes.getString("Com");
            this.idComptable = attributes.getInt("IDComptable");

            boats = getBoats();
            boats.addElement(boat);
        } catch (SQLException e){
            System.out.println("SQL Select exception n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public Adherent(int subscriptionYear, String postalCode, String name, String surname, String additional, String address,
                    String city, String email, String phone, String mobile, String comment, Date dateOfBirth, GenderType gender, int idComptable) {
        this.subscriptionYear = subscriptionYear;
        this.postalCode = postalCode;
        this.name = name;
        this.surname = surname;
        this.additional = additional;
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
        this.idComptable = idComptable;
        int id = AdherentController.addAdherent(this);
        if (id != 0)
            this.id = id;
        this.boats = new DefaultListModel<Boat>();
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
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

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
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
        return AdherentController.GetBoats(this);
    }

    public void addBoat (Boat boat){
        this.boats.addElement(boat);
    }

    public void removeBoat(Boat boat){
        this.boats.removeElement(boat);
    }

    public int getIdComptable() {
        return idComptable;
    }

    public void setIdComptable(int idComptable) {
        this.idComptable = idComptable;
        AdherentController.setIdComptable(this);
    }
}


