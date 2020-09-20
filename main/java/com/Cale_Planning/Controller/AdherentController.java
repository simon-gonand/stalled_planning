package com.Cale_Planning.Controller;

import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdherentController {
    public static DefaultListModel<Adherent> getAllAdherent(){
        DefaultListModel<Adherent> adherentList = new DefaultListModel<>();
        try{
            ResultSet results = Main.getDatabase().SQLSelect("SELECT ID FROM Adherent");
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
            ResultSet results = Main.getDatabase().SQLSelect("SELECT ID FROM Adherent");
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
                            "CodePostal, Ville, Email, Telephone, Portable, Com) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    adherent.getGender().toString(), adherent.getName(), adherent.getSurname(), adherent.getDateOfBirth(),
                    adherent.getSubscriptionYear(), adherent.getAdditional(), adherent.getAddress(), adherent.getPostalCode(), adherent.getCity(),
                    adherent.getEmail(), adherent.getPhone(), adherent.getMobile(), adherent.getComment());
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
}
