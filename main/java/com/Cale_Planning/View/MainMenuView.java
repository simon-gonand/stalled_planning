package com.Cale_Planning.View;

import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class MainMenuView extends JInternalFrame {
    public MainMenuView (){
        super();

        setTitle("Menu Principal");
        this.getContentPane().setBackground(Color.white);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, screenSize.width / 5, screenSize.height - screenSize.height/13);
        this.setLayout(new GridBagLayout());
        setResizable(true);
        setVisible(true);

        fillMainMenu();
    }

    private void fillMainMenu(){
        JButton adherentsViewButton = new JButton("Les adhérents", new ImageIcon("src/main/resources/user.png"));
        JButton boatViewButton = new JButton ("Les bateaux", new ImageIcon("src/main/resources/boat.png"));

        JInternalFrame thisFrame = this;
        adherentsViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDesktopPane mainPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                try {
                    new AdherentView(new Adherent(100000), mainPane);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            }
        });

        boatViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDesktopPane mainPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                try {
                    new BoatView(new Boat(200000), mainPane);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.ipady = 60;

        this.add(adherentsViewButton, constraints);
        constraints.gridy = 1;
        this.add(boatViewButton, constraints);
    }
}
