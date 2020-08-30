package com.Cale_Planning.Controller;

import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Boat;
import com.Cale_Planning.Models.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoatController {
    public static Boat[] getAllBoat() {
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

        Boat[] adherents = new Boat[boatList.size()];
        return boatList.toArray(adherents);
    }
}
