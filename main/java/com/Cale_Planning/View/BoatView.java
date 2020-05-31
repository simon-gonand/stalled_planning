package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.awt.*;
import java.util.zip.GZIPInputStream;

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
        setLayout(new GridLayout(3,1));

        setVisible(true);

        JPanel boatPropertiesPanel = new JPanel();
        JPanel ownerCategoryPanel = new JPanel();
        JPanel placePanel = new JPanel();

        fillBoatPropertiesPanel(boatPropertiesPanel);
        fillOwnerCategoryPanel(ownerCategoryPanel);
        fillPlacePanel(placePanel);

        this.add(boatPropertiesPanel);
        this.add(ownerCategoryPanel);
        this.add(placePanel);
    }

    private void fillBoatPropertiesPanel (JPanel panel){
        panel.setLayout(new GridLayout(2,1));

        JPanel firstRow = new JPanel(new GridBagLayout());
        JPanel secondRow = new JPanel(new GridLayout(1,8));

        JLabel nameLabel = new JLabel("Nom");
        this.name = new JTextField(boat.getName());
        JLabel registrationLabel = new JLabel("Immatriculation");
        this.registration = new JTextField(boat.getRegistration());

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
        JLabel widthLabel = new JLabel ("Largeur (m)");
        this.width = new JTextField(String.valueOf(boat.getWidth()));
        JLabel draughtLabel = new JLabel ("Tirant d'eau (m)");
        this.draught = new JTextField(String.valueOf(boat.getDraught()));
        JLabel weightLabel = new JLabel ("Poids (kg)");
        this.weight = new JTextField(String.valueOf(boat.getWeight()));

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
        this.owner = new JComboBox<Adherent>(AdherentController.getAllAdherent());
        this.owner.setSelectedItem(boat.getOwner());

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
}
