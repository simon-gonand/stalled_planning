package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoatView extends JInternalFrame {
    private Boat boat;
    private JTextField name, registration, length, width, draught, weight;
    private JComboBox<Adherent> owner;
    private JComboBox<Boat.categoryType> category;
    private ButtonGroup place;


    public BoatView(Boat boat){
        super();
        this.boat = boat;

        setTitle("Fiche Bateau " + boat.getName());
        this.getContentPane().setBackground(Color.white);
        setLayout(new GridLayout(4,1));

        setVisible(true);

        JPanel boatPropertiesPanel = new JPanel();
        JPanel ownerCategoryPanel = new JPanel();
        JPanel placePanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        boatPropertiesPanel.setBackground(Color.white);
        ownerCategoryPanel.setBackground(Color.white);
        placePanel.setBackground(Color.white);
        buttonPanel.setBackground(Color.white);

        fillBoatPropertiesPanel(boatPropertiesPanel);
        fillOwnerCategoryPanel(ownerCategoryPanel);
        fillPlacePanel(placePanel);
        fillButtonPanel(buttonPanel);

        this.add(boatPropertiesPanel);
        this.add(ownerCategoryPanel);
        this.add(placePanel);
        this.add(buttonPanel);
    }

    private void fillBoatPropertiesPanel (JPanel panel){
        panel.setLayout(new GridLayout(2,1));

        JPanel firstRow = new JPanel(new GridBagLayout());
        JPanel secondRow = new JPanel(new GridLayout(1,8));

        firstRow.setBackground(Color.white);
        secondRow.setBackground(Color.white);

        JLabel nameLabel = new JLabel("Nom");
        this.name = new JTextField(boat.getName());
        this.name.setBackground(new Color(239,239,239));
        JLabel registrationLabel = new JLabel("Immatriculation");
        this.registration = new JTextField(boat.getRegistration());
        this.registration.setBackground(new Color(239,239,239));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.3;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.LINE_END;

        firstRow.add(nameLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.7;
        firstRow.add(name, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.3;
        firstRow.add(registrationLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.7;
        firstRow.add(registration, constraints);

        JLabel lengthLabel = new JLabel ("Longueur (m)");
        this.length = new JTextField(String.valueOf(boat.getLength()));
        this.length.setBackground(new Color(239,239,239));
        JLabel widthLabel = new JLabel ("Largeur (m)");
        this.width = new JTextField(String.valueOf(boat.getWidth()));
        this.width.setBackground(new Color(239,239,239));
        JLabel draughtLabel = new JLabel ("Tirant d'eau (m)");
        this.draught = new JTextField(String.valueOf(boat.getDraught()));
        this.draught.setBackground(new Color(239,239,239));
        JLabel weightLabel = new JLabel ("Poids (kg)");
        this.weight = new JTextField(String.valueOf(boat.getWeight()));
        this.weight.setBackground(new Color(239,239,239));

        secondRow.add(lengthLabel);
        secondRow.add(length);
        secondRow.add(widthLabel);
        secondRow.add(width);
        secondRow.add(draughtLabel);
        secondRow.add(draught);
        secondRow.add(weightLabel);
        secondRow.add(weight);

        panel.add(firstRow);
        panel.add(secondRow);
    }

    private void fillOwnerCategoryPanel(JPanel panel){
        panel.setLayout(new GridLayout(2,2));

        JLabel categoryLabel = new JLabel("Categorie");
        this.category = new JComboBox<Boat.categoryType>(Boat.categoryType.values());
        this.category.setSelectedItem(boat.getCategory());
        JLabel ownerLabel = new JLabel("Propriétaire");
        Adherent[] allAdherents = AdherentController.getAllAdherent();
        this.owner = new JComboBox<Adherent>(allAdherents);
        for (Adherent adherent : allAdherents){
            if (adherent.getId() == boat.getOwner().getId())
                owner.setSelectedItem(adherent);
        }

        panel.add(categoryLabel);
        panel.add(ownerLabel);
        panel.add(category);
        panel.add(owner);
    }

    private void fillPlacePanel (JPanel panel){
        panel.setLayout(new GridBagLayout());

        JRadioButton temporary = new JRadioButton(Boat.placeType.PASSAGER.toString());
        temporary.setActionCommand(Boat.placeType.PASSAGER.toString());
        temporary.setBackground(Color.white);
        JRadioButton annual = new JRadioButton(Boat.placeType.ANNUEL.toString());
        annual.setActionCommand(Boat.placeType.ANNUEL.toString());
        annual.setBackground(Color.white);
        JRadioButton club = new JRadioButton(Boat.placeType.CLUB.toString());
        club.setActionCommand(Boat.placeType.CLUB.toString());
        club.setBackground(Color.white);
        JRadioButton tradition = new JRadioButton(Boat.placeType.TRADITION.toString());
        tradition.setActionCommand(Boat.placeType.TRADITION.toString());
        tradition.setBackground(Color.white);
        JRadioButton ground = new JRadioButton(Boat.placeType.TERRE.toString());
        ground.setActionCommand(Boat.placeType.TERRE.toString());
        ground.setBackground(Color.white);

        switch (this.boat.getPlace()){
            case PASSAGER:
                temporary.setSelected(true);
                break;
            case ANNUEL:
                annual.setSelected(true);
                break;
            case CLUB:
                club.setSelected(true);
                break;
            case TRADITION:
                tradition.setSelected(true);
                break;
            case TERRE:
                ground.setSelected(true);
                break;
            default: break;
        }

        this.place = new ButtonGroup();
        place.add(temporary);
        place.add(annual);
        place.add(club);
        place.add(tradition);
        place.add(ground);

        JLabel placeLabel = new JLabel("Place");
        JPanel placePanel = new JPanel(new GridLayout(1,4));
        placePanel.add(temporary);
        placePanel.add(annual);
        placePanel.add(club);
        placePanel.add(tradition);
        placePanel.add(ground);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.LINE_START;

        panel.add(placeLabel, constraints);
        ++constraints.gridy;
        panel.add(placePanel, constraints);
    }

    private void fillButtonPanel(JPanel panel){
        panel.setLayout(new GridBagLayout()); // Set a layout

        JButton submit = new JButton(new ImageIcon("src/main/resources/tick.png"));
        JButton cancel = new JButton(new ImageIcon("src/main/resources/cancel.png"));

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boat.setName(name.getText());
                boat.setRegistration(registration.getText());
                boat.setLength(Float.valueOf(length.getText()));
                boat.setWidth(Float.valueOf(width.getText()));
                boat.setDraught(Float.valueOf(draught.getText()));
                boat.setWeight(Float.valueOf(weight.getText()));
                boat.setPlace(Boat.placeType.parse(place.getSelection().getActionCommand()));
                boat.setCategory((Boat.categoryType) category.getSelectedItem());
                boat.setOwner((Adherent) owner.getSelectedItem());
            }
        });

        final JInternalFrame thisFrame = this;
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                for (JInternalFrame frame : desktopPane.getAllFrames()){
                    if (frame == thisFrame)
                        desktopPane.remove(frame);
                }
                SwingUtilities.updateComponentTreeUI(desktopPane);
            }
        });

        // Set constraints to display the buttons
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.weightx = 1;

        panel.add(submit, constraints);
        ++constraints.gridx;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(cancel, constraints);
    }
}
