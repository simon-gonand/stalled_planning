package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class BoatView extends JInternalFrame {
    private Boat boat;
    private JList<Boat> boatJList;
    private JTextField name, registration, length, width, draught, weight;
    private JTextArea comments;
    private JComboBox<Adherent> owner;
    private JComboBox<Boat.categoryType> category;
    private ButtonGroup place;
    private Font fontPlain = new Font(Font.DIALOG, Font.PLAIN, 13);
    private Font fontBold = new Font(Font.DIALOG, Font.BOLD, 13);


    public BoatView(Boat boat, JDesktopPane mainPane) throws PropertyVetoException {
        super();
        this.boat = boat;

        setTitle("Fiche Bateau " + boat.getName());
        this.getContentPane().setBackground(Color.white);
        setLayout(new GridLayout(5,1));

        int i = mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView || frame instanceof AllAdherentsView || frame instanceof AllBoatsView) {
                this.setBounds(frame.getX() + 20, frame.getY() + 20, 900, 350);
                break;
            }
            --i;
        }
        if (i < 0) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setBounds(screenSize.width / 3, screenSize.height / 4, 900, 350);
        }

        setResizable(true);
        setVisible(true);

        JPanel boatPropertiesPanel = new JPanel();
        JPanel ownerCategoryPanel = new JPanel();
        JPanel placePanel = new JPanel();
        JPanel commentPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        boatPropertiesPanel.setBackground(Color.white);
        ownerCategoryPanel.setBackground(Color.white);
        placePanel.setBackground(Color.white);
        commentPanel.setBackground(Color.white);
        buttonPanel.setBackground(Color.white);

        fillBoatPropertiesPanel(boatPropertiesPanel);
        fillOwnerCategoryPanel(ownerCategoryPanel);
        fillPlacePanel(placePanel);
        fillCommentPanel(commentPanel);
        fillButtonPanel(buttonPanel);

        this.add(boatPropertiesPanel);
        this.add(ownerCategoryPanel);
        this.add(placePanel);
        this.add(commentPanel);
        this.add(buttonPanel);

        mainPane.add(this);
        setSelected(true);
    }

    public BoatView(JDesktopPane mainPane, JList<Boat> boatJList) throws PropertyVetoException {
        super();

        this.boatJList = boatJList;
        setTitle("Fiche Bateau ");
        this.getContentPane().setBackground(Color.white);
        setLayout(new GridLayout(5,1));

        int i = mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView || frame instanceof AllAdherentsView || frame instanceof AllBoatsView) {
                this.setBounds(frame.getX() + 20, frame.getY() + 20, 800, 250);
                break;
            }
            --i;
        }
        if (i < 0) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setBounds(screenSize.width / 3, screenSize.height / 4, 800, 250);
        }

        setResizable(true);
        setVisible(true);

        JPanel boatPropertiesPanel = new JPanel();
        JPanel ownerCategoryPanel = new JPanel();
        JPanel placePanel = new JPanel();
        JPanel commentPanel= new JPanel();
        JPanel buttonPanel = new JPanel();

        boatPropertiesPanel.setBackground(Color.white);
        ownerCategoryPanel.setBackground(Color.white);
        placePanel.setBackground(Color.white);
        commentPanel.setBackground(Color.white);
        buttonPanel.setBackground(Color.white);

        fillBoatPropertiesPanel(boatPropertiesPanel);
        fillOwnerCategoryPanel(ownerCategoryPanel);
        fillPlacePanel(placePanel);
        fillCommentPanel(commentPanel);
        fillButtonPanel(buttonPanel);

        this.add(boatPropertiesPanel);
        this.add(ownerCategoryPanel);
        this.add(placePanel);
        this.add(commentPanel);
        this.add(buttonPanel);

        mainPane.add(this);
        setSelected(true);
    }

    private void fillBoatPropertiesPanel (JPanel panel){
        panel.setLayout(new GridLayout(2,1));

        JPanel firstRow = new JPanel(new GridBagLayout());
        JPanel secondRow = new JPanel(new GridLayout(1,8));

        firstRow.setBackground(Color.white);
        secondRow.setBackground(Color.white);

        if (boat == null){
            this.name = new JTextField();
            this.registration = new JTextField();
            this.length = new JTextField();
            this.width = new JTextField();
            this.draught = new JTextField();
            this.weight = new JTextField();
        } else {
            this.name = new JTextField(boat.getName());
            this.registration = new JTextField(boat.getRegistration());
            this.length = new JTextField(String.valueOf(boat.getLength()));
            this.width = new JTextField(String.valueOf(boat.getWidth()));
            this.draught = new JTextField(String.valueOf(boat.getDraught()));
            this.weight = new JTextField(String.valueOf(boat.getWeight()));
        }

        JLabel nameLabel = new JLabel("Nom");
        nameLabel.setFont(fontBold);
        this.name.setBackground(new Color(239,239,239));
        JLabel registrationLabel = new JLabel("Immatriculation");
        registrationLabel.setFont(fontBold);
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
        lengthLabel.setFont(fontBold);
        this.length.setBackground(new Color(239,239,239));
        JLabel widthLabel = new JLabel ("Largeur (m)");
        widthLabel.setFont(fontBold);
        this.width.setBackground(new Color(239,239,239));
        JLabel draughtLabel = new JLabel ("Tirant d'eau (m)");
        draughtLabel.setFont(fontBold);
        this.draught.setBackground(new Color(239,239,239));
        JLabel weightLabel = new JLabel ("Poids (kg)");
        weightLabel.setFont(fontBold);
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
        categoryLabel.setFont(fontBold);
        this.category = new JComboBox<Boat.categoryType>(Boat.categoryType.values());
        JLabel ownerLabel = new JLabel("Propriétaire");
        ownerLabel.setFont(fontBold);
        Adherent[] allAdherents = AdherentController.getAllAdherentArray();
        this.owner = new JComboBox<Adherent>(allAdherents);

        if (boat != null) {
            this.category.setSelectedItem(boat.getCategory());
            for (Adherent adherent : allAdherents){
                if (adherent.getId() == boat.getOwner().getId())
                    owner.setSelectedItem(adherent);
            }
        }

        panel.add(categoryLabel);
        panel.add(ownerLabel);
        panel.add(category);
        panel.add(owner);
    }

    private void fillPlacePanel (JPanel panel){
        panel.setLayout(new GridBagLayout());

        JRadioButton temporary = new JRadioButton(Boat.placeType.PASSAGER.toString());
        temporary.setFont(fontPlain);
        temporary.setActionCommand(Boat.placeType.PASSAGER.toString());
        temporary.setBackground(Color.white);
        JRadioButton annual = new JRadioButton(Boat.placeType.ANNUEL.toString());
        annual.setFont(fontPlain);
        annual.setActionCommand(Boat.placeType.ANNUEL.toString());
        annual.setBackground(Color.white);
        JRadioButton club = new JRadioButton(Boat.placeType.CLUB.toString());
        club.setFont(fontPlain);
        club.setActionCommand(Boat.placeType.CLUB.toString());
        club.setBackground(Color.white);
        JRadioButton tradition = new JRadioButton(Boat.placeType.TRADITION.toString());
        tradition.setFont(fontPlain);
        tradition.setActionCommand(Boat.placeType.TRADITION.toString());
        tradition.setBackground(Color.white);
        JRadioButton ground = new JRadioButton(Boat.placeType.TERRE.toString());
        ground.setFont(fontPlain);
        ground.setActionCommand(Boat.placeType.TERRE.toString());
        ground.setBackground(Color.white);

        if (boat != null){
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
        }

        this.place = new ButtonGroup();
        place.add(temporary);
        place.add(annual);
        place.add(club);
        place.add(tradition);
        place.add(ground);

        JLabel placeLabel = new JLabel("Place");
        placeLabel.setFont(fontBold);
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

    private void fillCommentPanel(JPanel panel){
        panel.setLayout(new GridLayout(1,1));

        if (boat == null)
            this.comments = new JTextArea();
        else
            this.comments = new JTextArea(boat.getComment());
        // Stop text area stretching
        comments.setLineWrap(true);
        comments.setWrapStyleWord(true);
        comments.setFont(fontBold);

        comments.setBackground(new Color(239,239,239));

        panel.setBorder(BorderFactory.createTitledBorder("Commentaires"));

        panel.add(comments);
    }

    private void fillButtonPanel(JPanel panel){
        panel.setLayout(new GridBagLayout()); // Set a layout

        JButton submit = new JButton(new ImageIcon("src/main/resources/tick.png"));
        JButton cancel = new JButton(new ImageIcon("src/main/resources/cancel.png"));

        final JInternalFrame thisFrame = this;
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (boat != null) {
                    boat.setName(name.getText());
                    boat.setRegistration(registration.getText());
                    boat.setLength(Float.valueOf(length.getText()));
                    boat.setWidth(Float.valueOf(width.getText()));
                    boat.setDraught(Float.valueOf(draught.getText()));
                    boat.setWeight(Float.valueOf(weight.getText()));
                    boat.setPlace(Boat.placeType.parse(place.getSelection().getActionCommand()));
                    boat.setCategory((Boat.categoryType) category.getSelectedItem());
                    boat.setComment(comments.getText());

                    Adherent oldOwner = boat.getOwner();
                    if (oldOwner.getId() != ((Adherent) owner.getSelectedItem()).getId()) {
                        boat.setOwner((Adherent) owner.getSelectedItem());
                        oldOwner.removeBoat(boat);
                        ((Adherent) owner.getSelectedItem()).addBoat(boat);
                        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                        for (JInternalFrame frame : desktopPane.getAllFrames()) {
                            if (frame instanceof AdherentView) {
                                AdherentView adherentView = (AdherentView) frame;
                                if (adherentView.getAdherent().getId() == ((Adherent) owner.getSelectedItem()).getId())
                                    adherentView.updateBoatList(((Adherent) owner.getSelectedItem()).getBoats());
                                else if (adherentView.getAdherent().getId() == oldOwner.getId())
                                    adherentView.updateBoatList(oldOwner.getBoats());
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(thisFrame, "Les informations du bateau " + name.getText() +
                            " ont bien été modifié", "Bateau modifié", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    Boat boat = new Boat(name.getText(), registration.getText(), Float.valueOf(length.getText()), Float.valueOf(width.getText()),
                            Float.valueOf(draught.getText()), Float.valueOf(weight.getText()), (Adherent) owner.getSelectedItem(),
                            (Boat.categoryType) category.getSelectedItem(), Boat.placeType.parse(place.getSelection().getActionCommand()),
                            comments.getText());
                    JOptionPane.showMessageDialog(thisFrame, "Le bateau " + name.getText() +
                            " a bien été ajouté", "Bateau ajouté", JOptionPane.INFORMATION_MESSAGE);
                    DefaultListModel defaultListModel = (DefaultListModel) boatJList.getModel();
                    defaultListModel.add(defaultListModel.size(), boat);
                    boatJList.setModel(defaultListModel);
                    JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                    SwingUtilities.updateComponentTreeUI(desktopPane);
                }
            }
        });

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
