package com.Cale_Planning.Models;

import com.mindfusion.scheduling.model.Appointment;

public class Reservation extends Appointment {
    private float amount;
    private float deposit;

    public Reservation(float amount, float deposit){
        super();

        this.amount = amount;
        this.deposit = deposit;
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
}
