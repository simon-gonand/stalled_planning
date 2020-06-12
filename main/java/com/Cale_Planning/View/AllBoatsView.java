package com.Cale_Planning.View;

import com.Cale_Planning.Controller.BoatController;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class AllBoatsView extends JInternalFrame {
    public AllBoatsView(JDesktopPane mainPane) throws PropertyVetoException {
        super();

        setTitle("Les Bateaux");
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new GridLayout(1,1));

        fillBoatsView(mainPane);

        int i = mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView || frame instanceof AllAdherentsView) {
                this.setBounds(frame.getX() + 20, frame.getY() + 20, 650, 525);
                break;
            }
            --i;
        }
        if (i < 0) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setBounds(screenSize.width / 3, screenSize.height / 4, 650, 525);
        }
        setResizable(true);
        setVisible(true);
        mainPane.add(this);
        setSelected(true);
    }

    private void fillBoatsView(JDesktopPane mainPane) {
        Boat[] allBoats = BoatController.getAllBoat();
        JList<Boat> boatJList = new JList<>(allBoats);
        boatJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    try {
                        new BoatView(boatJList.getModel().getElementAt(boatJList.locationToIndex(e.getPoint())), mainPane);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(mainPane);
                }
            }
        });

        this.add(boatJList);
    }
}
