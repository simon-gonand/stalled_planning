package com.Cale_Planning.Controller;

import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdherentController {
    public static DefaultListModel<Adherent> getAllAdherent(){
        DefaultListModel<Adherent> adherentList = new DefaultListModel<>();
        try{
            ResultSet results = Main.getDatabase().SQLSelect("SELECT ID, Nom FROM Adherent ORDER BY Nom ASC");
            int i = 0;
            while (results.next()){
                adherentList.add(i, new Adherent(results.getInt("ID")));
                ++i;
            }
        } catch (SQLException e){
            System.err.println("Select All Adherent error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }
        return adherentList;
    }

    public static Adherent[] getAllAdherentArray(){
        List<Adherent> adherentList = new ArrayList<Adherent>();
        try{
            ResultSet results = Main.getDatabase().SQLSelect("SELECT ID, Nom FROM Adherent ORDER BY Nom ASC");
            while (results.next()){
                adherentList.add(new Adherent(results.getInt("ID")));
            }
        } catch (SQLException e){
            System.err.println("Select All Adherent error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }
        Adherent[] allAdherents = new Adherent[adherentList.size()];
        return adherentList.toArray(allAdherents);
    }

    public static Adherent getAdherentByIDComptable (int id, String name, String surname) throws SQLException {
        try{
            String str = "SELECT ID FROM Adherent WHERE IDComptable = " + id + " AND Nom = \"" + surname +
                    "\" AND Prenom = \"" + name + "\"";
            ResultSet results = Main.getDatabase().SQLSelect(str);

            if (results.next() != false)
                return new Adherent(results.getInt("ID"));
        } catch (SQLException e){
            System.err.println("Select get adherent by IDCompable error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
            throw e;
        }
        return null;
    }

    public static void deleteAdherent(Adherent adherent){
        try {
            Main.getDatabase().SQLUpdate("DELETE FROM Adherent WHERE ID = " + adherent.getId());
        } catch (SQLException e) {
            System.err.println("Delete from Adherent error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }
    }

    public static int addAdherent (Adherent adherent){
        try{
            Main.getDatabase().SQLUpdate("INSERT INTO Adherent (Genre, Nom, Prenom, DateNaissance, DateAdhesion, Batiment, Rue, " +
                            "CodePostal, Ville, Email, Telephone, Portable, Com, IDComptable) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    adherent.getGender().toString(), adherent.getSurname(), adherent.getName(), adherent.getDateOfBirth(),
                    adherent.getSubscriptionYear(), adherent.getAdditional(), adherent.getAddress(), adherent.getPostalCode(), adherent.getCity(),
                    adherent.getEmail(), adherent.getPhone(), adherent.getMobile(), adherent.getComment(), adherent.getIdComptable());
            ResultSet resultSet = Main.getDatabase().SQLSelect("SELECT ID FROM Adherent ORDER BY ID DESC LIMIT 1");
            resultSet.next();
            return resultSet.getInt("ID");
        } catch (SQLException e ){
            System.out.println("Adherent insertion error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void setSubscriptionYear(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET DateAdhesion = ? WHERE ID = ?",
                    String.valueOf(adherent.getSubscriptionYear()), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Subscription Year Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setPostalCode(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET CodePostal = ? WHERE ID = ?",
                    String.valueOf(adherent.getPostalCode()), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Postal Code Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setName(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Prenom = ? WHERE ID = ?", adherent.getName(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Name Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setSurname(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Nom = ? WHERE ID = ?", adherent.getSurname(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Surname Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setBuilding(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Batiment = ? WHERE ID = ?", adherent.getAdditional(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Building Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setAddress(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Rue = ? WHERE ID = ?", adherent.getAddress(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Address Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setCity(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Ville = ? WHERE ID = ?", adherent.getCity(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("City Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setEmail(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Email = ? WHERE ID = ?", adherent.getEmail(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Email Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setPhone(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Telephone = ? WHERE ID = ?", adherent.getPhone(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Phone Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setMobile(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Portable = ? WHERE ID = ?", adherent.getMobile(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Mobile Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setComment(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Com = ? WHERE ID = ?", adherent.getComment(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Comment Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setDateOfBirth(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET DateNaissance = ? WHERE ID = ?", adherent.getDateOfBirth(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Date of Birth Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setGender(Adherent adherent) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET Genre = ? WHERE ID = ?", adherent.getGender().toString(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("Gender Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setIdComptable (Adherent adherent){
        try {
            Main.getDatabase().SQLUpdate("UPDATE Adherent SET IDComptable = ? WHERE ID = ?", adherent.getIdComptable(), String.valueOf(adherent.getId()));
        } catch (SQLException e){
            System.out.println("IDComptable Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static DefaultListModel<Boat> GetBoats(Adherent adherent){
        try {
            DefaultListModel boats = new DefaultListModel<Boat>();
            ResultSet boatsID = Main.getDatabase().SQLSelect("SELECT ID FROM Bateau WHERE Proprietaire = " + adherent.getId());
            while (boatsID.next()){
                boats.addElement(new Boat(boatsID.getInt("ID"), adherent));
            }
            return boats;
        } catch(SQLException e){
            System.out.println("Get boat error error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
