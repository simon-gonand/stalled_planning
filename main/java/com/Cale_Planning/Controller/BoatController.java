package com.Cale_Planning.Controller;

import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import com.Cale_Planning.Models.Reservation;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoatController {
    public static DefaultListModel<Boat> getAllBoat() {
        DefaultListModel<Boat> boatList = new DefaultListModel<Boat>();
        try {
            ResultSet results = Main.getDatabase().SQLSelect("SELECT ID FROM Bateau");
            int i = 0;
            while (results.next()) {
                boatList.add(i, new Boat(results.getInt("ID")));
                ++i;
            }
        } catch (SQLException e) {
            System.err.println("Select All Boat error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }

        return boatList;
    }

    public static Boat[] getAllBoatArray() {
        List<Boat> boatList = new ArrayList<Boat>();
        try {
            ResultSet results = Main.getDatabase().SQLSelect("SELECT ID FROM Bateau");
            while (results.next()) {
                boatList.add(new Boat(results.getInt("ID")));
            }
        } catch (SQLException e) {
            System.err.println("Select All Boat error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }

        Boat[] boats = new Boat[boatList.size()];
        return boatList.toArray(boats);
    }

    public static void deleteBoat (Boat boat){
        try {
            Main.getDatabase().SQLUpdate("DELETE FROM Bateau WHERE ID = " + boat.getId());
        } catch (SQLException e) {
            System.err.println("Delete from Boat error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }
    }

    public static int addBoat (Boat boat){
        try{
            Main.getDatabase().SQLUpdate("INSERT INTO Bateau (Nom, Immatriculation, Categorie, Longueur, Largeur, TirantEau, " +
                            "Poids, Place, Proprietaire) VALUES (?,?,?,?,?,?,?,?,?)",
                    boat.getName(), boat.getRegistration(), boat.getCategory().toString(), boat.getLength(), boat.getWidth(), boat.getDraught(),
                    boat.getWeight(), boat.getPlace().toString(), boat.getOwner().getId());
            ResultSet resultSet = Main.getDatabase().SQLSelect("SELECT ID FROM Bateau ORDER BY ID DESC LIMIT 1");
            resultSet.next();
            return resultSet.getInt("ID");
        } catch (SQLException e ){
            System.out.println("Boat insertion error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void setName(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Nom = ? WHERE ID = ?", boat.getName(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Boat Name Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setRegistration(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Immatriculation = ? WHERE ID = ?",
                    boat.getRegistration(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Registration Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setLength(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Longueur = ? WHERE ID = ?", boat.getLength(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Length Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setWidth(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Largeur = ? WHERE ID = ?", boat.getWidth(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Width Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setDraught(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET TirantEau = ? WHERE ID = ?", boat.getDraught(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Draught Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setWeight(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Poids = ? WHERE ID = ?", boat.getWeight(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Weight Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setOwner(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Proprietaire = ? WHERE ID = ?",
                    boat.getOwner().getId(),String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Owner Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setCategory(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Categorie = ? WHERE ID = ?",
                    boat.getCategory().toString(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Category Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }

    public static void setPlace(Boat boat) {
        try {
            Main.getDatabase().SQLUpdate("UPDATE Bateau SET Place = ? WHERE ID = ?", boat.getPlace().toString(), String.valueOf(boat.getId()));
        } catch (SQLException e){
            System.out.println("Place Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }
}
