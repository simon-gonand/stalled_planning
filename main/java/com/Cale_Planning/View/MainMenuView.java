package com.Cale_Planning.View;

import com.Cale_Planning.Main;
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
        Font font = new Font(Font.DIALOG, Font.BOLD, 20);
        JButton adherentsViewButton = new JButton("Les adhérents", new ImageIcon("src/main/resources/user.png"));
        adherentsViewButton.setFont(font);
        JButton boatViewButton = new JButton ("Les bateaux", new ImageIcon("src/main/resources/boat.png"));
        boatViewButton.setFont(font);
        JButton stalledViewButton = new JButton ("Planning Cale", new ImageIcon("src/main/resources/calendar.png"));
        stalledViewButton.setFont(font);

        JInternalFrame thisFrame = this;
        adherentsViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Main.windowManagment.isEmpty()) {
                    for (JInternalFrame frame : Main.windowManagment)
                        if (frame.getClass().equals(AllAdherentsView.class)){
                            try {
                                frame.setSelected(true);
                            } catch (PropertyVetoException ex) {
                                ex.printStackTrace();
                            }
                            return;
                        }
                }
                JDesktopPane mainPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                try {
                    AllAdherentsView view = new AllAdherentsView(mainPane);
                    Main.windowManagment.add(view);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            }
        });

        boatViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Main.windowManagment.isEmpty()) {
                    for (JInternalFrame frame : Main.windowManagment)
                        if (frame.getClass().equals(AllBoatsView.class)){
                            try {
                                frame.setSelected(true);
                            } catch (PropertyVetoException ex) {
                                ex.printStackTrace();
                            }
                            return;
                        }
                }
                JDesktopPane mainPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                try {
                    AllBoatsView view = new AllBoatsView(mainPane);
                    Main.windowManagment.add(view);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            }
        });

        stalledViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Main.windowManagment.isEmpty()) {
                    for (JInternalFrame frame : Main.windowManagment)
                        if (frame.getClass().equals(StalledView.class)){
                            try {
                                frame.setSelected(true);
                            } catch (PropertyVetoException ex) {
                                ex.printStackTrace();
                            }
                            return;
                        }
                }
                JDesktopPane mainPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                try {
                    StalledView view = new StalledView(mainPane);
                    Main.windowManagment.add(view);
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
        ++constraints.gridy;
        this.add(boatViewButton, constraints);
        ++constraints.gridy;
        this.add(stalledViewButton, constraints);
    }
}
