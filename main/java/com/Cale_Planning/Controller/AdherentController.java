package com.Cale_Planning.Controller;


import com.Cale_Planning.MSAccessBase;
import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdherentController {
    public static Adherent[] getAllAdherent(){
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

        Adherent[] adherents = new Adherent[adherentList.size()];
        return adherentList.toArray(adherents);
    }
}
