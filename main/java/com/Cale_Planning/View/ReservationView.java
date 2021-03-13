package com.Cale_Planning.View;

import com.Cale_Planning.Models.Reservation;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class ReservationView extends JInternalFrame {
    private JDesktopPane mainPane;
    private Reservation reservation;

    public ReservationView (JDesktopPane mainPane, JInternalFrame stalledView, Reservation reservation) throws PropertyVetoException {
        super();

        setTitle("Planning Cale");
        this.getContentPane().setBackground(Color.white);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screenSize.width / 6 + stalledView.getWidth() / 2 - 450, screenSize.height / 2 - 350/2,
                900, 350);

        this.reservation = reservation;
        this.mainPane = mainPane;
        this.mainPane.add(this);
        this.setSelected(true);
        this.setVisible(true);
        this.setResizable(true);
    }

    public Reservation getReservation() {
        return reservation;
    }
}
