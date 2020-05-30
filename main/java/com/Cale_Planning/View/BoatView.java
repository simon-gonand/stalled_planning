package com.Cale_Planning.View;

import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.awt.*;

public class BoatView extends JInternalFrame {
    private Boat boat;

    public BoatView(Boat boat){
        super();

        this.boat = boat;
        setTitle("Fiche Bateau");
        this.getContentPane().setBackground(Color.white);
        setLayout(new GridLayout());

        setVisible(true);
    }
}
