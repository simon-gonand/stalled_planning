package com.Cale_Planning.Models;

import com.mindfusion.scheduling.model.Appointment;

public class Reservation extends Appointment {
    private int id;
    private float amount;
    private float deposit;
    private boolean isUpToDate;
    private Boat boat;
    private Adherent adherent;

    public Reservation(int id, float amount, float deposit, boolean isUpToDate, Boat boat, Adherent adherent){
        super();

        this.id = id;
        this.amount = amount;
        this.deposit = deposit;
        this.isUpToDate = isUpToDate;
        this.boat = boat;
        this.adherent = adherent;
    }

    public float getAmount() {
        return amount;
    }

    public int getID() {return this.id;}

    public float getDeposit() {
        return deposit;
    }

    public Boolean isUpToDate() {
        return isUpToDate;
    }

    public void setUpToDate(boolean upToDate) {
        isUpToDate = upToDate;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Adherent getAdherent() {
        return adherent;
    }
}
