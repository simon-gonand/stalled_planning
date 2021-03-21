package com.Cale_Planning.Controller;

import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import com.Cale_Planning.Models.Reservation;
import com.mindfusion.common.DateTime;
import com.mindfusion.drawing.Colors;
import com.mindfusion.drawing.GradientBrush;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.model.Style;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class StalledController {
    public static Reservation createAppointment (Calendar calendar, DateTime startDate, DateTime endDate, int cale, Adherent adherent,
                                          Color color, int id, float amount, float deposit, Boat boat, boolean isUpToDate) {
        Reservation appointment = new Reservation(id, amount, deposit, isUpToDate, boat, adherent);
        appointment.setHeaderText(adherent.getSurname() + " " + adherent.getName());
        appointment.setStartTime(startDate);
        appointment.setEndTime(endDate);
        appointment.getContacts().add(calendar.getContacts().get(cale - 1));
        Style style = appointment.getStyle();
        style.setLineColor(color);
        style.setFillColor(color);
        style.setBrush(new GradientBrush(Colors.White, color, 90));
        if (!isUpToDate) {
            style.setHeaderTextColor(Color.RED);
            style.setHeaderFont(new Font(Font.DIALOG, Font.ITALIC, 15));
        }
        else
            style.setHeaderFont(new Font(Font.DIALOG, Font.PLAIN, 13));

        calendar.getSchedule().getItems().add(appointment);
        calendar.repaint();

        return appointment;
    }

    public static void createAppointment (Calendar calendar, Reservation reservation){
        Style style = reservation.getStyle();
        if (!reservation.isUpToDate()) {
            style.setHeaderTextColor(Color.RED);
            style.setHeaderFont(new Font(Font.DIALOG, Font.ITALIC | Font.BOLD, 15));
        }
        else {
            style.setHeaderTextColor(Color.black);
            style.setHeaderFont(new Font(Font.DIALOG, Font.PLAIN, 13));
        }
        calendar.getSchedule().getItems().add(reservation);
        calendar.repaint();
    }

    public static int addAppointmentToDatabase(Adherent adherent, DateTime startDate, DateTime endDate, int cale,
                                                Color color, float amount, float deposit, Boat boat, boolean isUpToDate){
        try {
            Main.getDatabase().SQLUpdate("INSERT INTO Reservation (Adherent, DateDebut, DateFin, Cale, Couleur, Montant, Caution, Bateau, " +
                            "CotisationAJour) VALUES(?,?,?,?,?,?,?,?,?)", adherent.getId(), startDate, endDate, cale, colorToName(color), amount,
                    deposit, boat.getId(), isUpToDate);
            ResultSet resultSet = Main.getDatabase().SQLSelect("SELECT ID FROM Reservation ORDER BY ID DESC LIMIT 1");
            resultSet.next();
            return resultSet.getInt("ID");
        } catch (SQLException ex) {
            System.out.println("Reservation insertion error n° " + ex.getErrorCode() + "What goes wrong ?");
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public static void deleteAppointmentOfDatabase(Reservation reservation){
        try {
            Main.getDatabase().SQLUpdate("DELETE FROM Reservation WHERE ID = ?", reservation.getID());
        } catch (SQLException e) {
            System.err.println("Delete from Reservation error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }
    }

    public static void deleteAppointmentOfDatabase(int id){
        try {
            Main.getDatabase().SQLUpdate("DELETE FROM Reservation WHERE ID = ?", id);
        } catch (SQLException e) {
            System.err.println("Delete from Reservation error n° " + e.getErrorCode());
            System.err.println("What goes wrong ? " + e.getMessage());
        }
    }

    public static String colorToName (Color color){
        if (color.getBlue() == 254 || color.getBlue() == 127 || color.getBlue() == 174)
            color = new Color(color.getRed(), color.getGreen(), color.getBlue() + 1, color.getAlpha());
        if (color.getGreen() == 254 || color.getGreen() == 127 || color.getGreen() == 174)
            color = new Color(color.getRed(), color.getGreen() + 1, color.getBlue(), color.getAlpha());
        if (color.getRed() == 254 || color.getRed() == 127)
            color = new Color(color.getRed() + 1, color.getGreen(), color.getBlue(), color.getAlpha());
        if (color.equals(Color.blue))
            return "Blue";
        if (color.equals(Color.cyan))
            return "Cyan";
        if (color.equals(Color.RED))
            return "Red";
        if (color.equals(Color.orange))
            return "Orange";
        if (color.equals(Color.yellow))
            return "Yellow";
        if (color.equals(Color.green))
            return "Green";
        if (color.equals(Color.pink))
            return "Pink";
        if (color.equals(Color.MAGENTA))
            return "Magenta";
        if (color.equals(Color.gray))
            return "Gray";
        return "No color found";
    }

    public static int calculateAmount (DateTime startDate, DateTime endDate){
        int amount = 35;
        int weekToRemove = 0;
        float nbDays = TimeUnit.DAYS.convert(endDate.toJavaCalendar().getTimeInMillis() - startDate.toJavaCalendar().getTimeInMillis(),
                TimeUnit.MILLISECONDS);
        if (startDate.getMonth() == 2 || endDate.getMonth() == 2 || (startDate.getMonth() < 2 && endDate.getMonth() > 2))
            nbDays += 2;
        if (nbDays >= 7 && nbDays < 30){
            int nbWeeks = Math.round(nbDays) / 7;
            int rest = Math.round(nbDays % 7);
            if (rest <= 1)
                amount = nbWeeks * 40;
            else
                amount = nbWeeks * 40 + 35;

            //amount -= (weekToRemove - 1) * 5;
        }
        else if (nbDays >= 30){
            int nbMonth = Math.round(nbDays) / 30;
            int rest = Math.round(nbDays % 30);
            if (rest <= 1)
                amount = nbMonth * 120;
            else if (nbDays - nbMonth * 30 < 7)
                amount = nbMonth * 120 + 35;
            else if (nbDays - nbMonth * 30 >= 7){
                int stayingWeek = Math.round(nbDays - nbMonth * 30) / 7;
                int restWeek = Math.round(nbDays - nbMonth * 30) % 7;
                if (restWeek <= 1)
                    amount = nbMonth * 120 + stayingWeek * 40;
                else
                    amount = nbMonth * 120 + stayingWeek * 40 + 35;
            }
        }
        return amount;
    }

    public static void setUpToDate (Reservation reservation){
        try {
            Main.getDatabase().SQLUpdate("UPDATE Reservation SET CotisationAJour = ? WHERE ID = ?", reservation.isUpToDate(),
                    String.valueOf(reservation.getID()));
        } catch (SQLException e){
            System.out.println("Boat Name Update error n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }
}
