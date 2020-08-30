package com.Cale_Planning.Models;

import com.mindfusion.scheduling.model.Appointment;

public class Reservation extends Appointment {
    private float amount;
    private float deposit;
    private Boat boat;

    public Reservation(float amount, float deposit, Boat boat){
        super();

        this.amount = amount;
        this.deposit = deposit;
        this.boat = boat;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }
}
