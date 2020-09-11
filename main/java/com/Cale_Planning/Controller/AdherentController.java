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
}
