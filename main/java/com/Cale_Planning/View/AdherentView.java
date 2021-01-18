package com.Cale_Planning.View;

import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;

public class AdherentView extends JInternalFrame {
    private Adherent adherent;
    private JList adherentList;
    private JTextField name, surname, subscription, additional, street, postalCode, city, email;
    private JFormattedTextField phone, mobile;
    private JDatePicker birth;
    private ButtonGroup genders;
    private JTextArea comment;
    private JList boats;
    private Font fontBold = new Font(Font.DIALOG, Font.BOLD, 15);
    private Font fontPlain = new Font(Font.DIALOG, Font.PLAIN, 15);

    public AdherentView(Adherent adherent, JDesktopPane mainPane) throws PropertyVetoException {
        super();

        this.adherent = adherent;
        setTitle("Fiche Adhérent");
        this.getContentPane().setBackground(Color.white);
        setLayout(new GridBagLayout());
        setView();

        int i = mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView || frame instanceof AllAdherentsView || frame instanceof AllBoatsView) {
                this.setBounds(frame.getX() + 20, frame.getY() + 20, 750, 525);
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

    public AdherentView(JDesktopPane mainPane, JList adherentList) throws PropertyVetoException {
        super();

        this.adherentList = adherentList;
        setTitle("Fiche Adhérent");
        this.getContentPane().setBackground(Color.white);
        setLayout(new GridBagLayout());
        setView();

        int i = mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView || frame instanceof AllAdherentsView || frame instanceof AllBoatsView) {
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

    private void setView(){
        // Create all three panels
        JPanel identity = new JPanel(); // Identity of the Adherent (name, surname , gende...)
        JPanel address = new JPanel(); // Address of the Adherent
        JPanel diverse = new JPanel(); // Comment and Boats of the Adherent
        JPanel buttons = new JPanel(); // Buttons to submit or cancel

        // Set background color to white
        identity.setBackground(Color.white);
        address.setBackground(Color.white);
        diverse.setBackground(Color.white);
        buttons.setBackground(Color.white);

        // Set borders to identity and address panel
        identity.setBorder(BorderFactory.createTitledBorder("Identité"));
        address.setBorder(BorderFactory.createTitledBorder("Adresse"));

        // Function to fill all those panels
        fillIdentityPanel(identity);
        fillAddressPanel(address);
        fillDiversePanel(diverse);
        fillButtonsPanel(buttons);

        // Constraints for the layout (to place the panels)
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.333;
        constraints.weightx = 1;
        constraints.insets = new Insets(5, 15, 0, 15);
        constraints.fill = GridBagConstraints.BOTH;

        // Add panels to the view
        this.add(identity, constraints);
        ++constraints.gridy; // change row
        constraints.insets.top = 0;
        this.add(address, constraints);
        ++constraints.gridy;
        this.add(diverse, constraints);
        ++constraints.gridy;
        constraints.weighty = 0.05;
        constraints.insets.bottom = 5;
        this.add(buttons, constraints);
    }

    private void fillIdentityPanel(JPanel panel){
        panel.setLayout(new GridBagLayout());

        JRadioButton mister = new JRadioButton(Adherent.GenderType.MISTER.toString());
        mister.setActionCommand(Adherent.GenderType.MISTER.toString());
        mister.setFont(fontPlain);
        JRadioButton miss = new JRadioButton(Adherent.GenderType.MISS.toString());
        miss.setActionCommand(Adherent.GenderType.MISS.toString());
        miss.setFont(fontPlain);
        JRadioButton noGender = new JRadioButton(Adherent.GenderType.NO_GENDER.toString());
        noGender.setActionCommand(Adherent.GenderType.NO_GENDER.toString());
        noGender.setFont(fontPlain);
        if (adherent != null) {
            switch (adherent.getGender()) {
                case MISTER:
                    mister.setSelected(true);
                    break;
                case MISS:
                    miss.setSelected(true);
                    break;
                case NO_GENDER:
                    noGender.setSelected(true);
                default:
                    break;
            }
        }

        mister.setBackground(Color.white);
        miss.setBackground(Color.white);
        noGender.setBackground(Color.white);

        this.genders = new ButtonGroup();
        genders.add(mister);
        genders.add(miss);
        genders.add(noGender);

        JPanel gendersChoice = new JPanel(new GridLayout(1,0));
        gendersChoice.setBackground(Color.white);
        gendersChoice.add(mister);
        gendersChoice.add(miss);
        gendersChoice.add(noGender);

        JLabel labelName = new JLabel("Prénom");
        labelName.setFont(fontBold);
        JLabel labelSurname = new JLabel("Nom");
        labelSurname.setFont(fontBold);
        JLabel birthLabel = new JLabel("Date naissance");
        birthLabel.setFont(fontBold);
        JLabel subscriptionLabel = new JLabel("Date adhésion");
        subscriptionLabel.setFont(fontBold);
        if (adherent != null) {
            this.name = new JTextField(adherent.getName());
            this.surname = new JTextField(adherent.getSurname());

            UtilDateModel model = new UtilDateModel(this.adherent.getDateOfBirth());
            Properties properties = new Properties();
            this.birth = new JDatePickerImpl(new JDatePanelImpl(model, properties), new DateComponentFormatter());

            this.subscription = new JTextField(String.valueOf(this.adherent.getSubscriptionYear()));
        }
        else{
            this.name = new JTextField();
            this.surname = new JTextField();

            UtilDateModel model = new UtilDateModel();
            Properties properties = new Properties();
            this.birth = new JDatePickerImpl(new JDatePanelImpl(model, properties), new DateComponentFormatter());

            this.subscription = new JTextField();
        }
        this.name.setBackground(new Color(239,239,239));
        this.surname.setBackground(new Color(239,239,239));
        this.subscription.setBackground(new Color(239,239,239));

        addToPanelIdentityWithConstraints(panel, gendersChoice, labelName, labelSurname, birthLabel, subscriptionLabel);
    }

    private void addToPanelIdentityWithConstraints(JPanel panel, JPanel gendersChoice, JLabel labelName, JLabel labelSurname,
                                           JLabel birthLabel, JLabel subscriptionLabel){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 3;

        panel.add(gendersChoice, constraints);
        constraints.gridwidth = 1;
        ++constraints.gridy;
        panel.add(labelSurname, constraints);
        ++constraints.gridx;
        constraints.ipadx = 125;
        panel.add(surname, constraints);
        constraints.ipadx = 0;
        ++constraints.gridx;
        panel.add(labelName, constraints);
        constraints.ipadx = 125;
        ++constraints.gridx;
        panel.add(name, constraints);
        ++constraints.gridy;
        constraints.gridx = 0;
        constraints.ipadx = 0;
        panel.add(birthLabel, constraints);
        constraints.ipadx = 100;
        ++constraints.gridx;
        panel.add((JComponent)birth, constraints);
        constraints.ipadx = 0;
        ++constraints.gridx;
        panel.add(subscriptionLabel, constraints);
        ++constraints.gridx;
        constraints.ipadx = 125;
        panel.add(subscription, constraints);
    }

    private void fillAddressPanel(JPanel panel){
        JLabel buildingLabel = new JLabel ("Bâtiment");
        buildingLabel.setFont(fontBold);
        JLabel streetLabel = new JLabel("Rue");
        streetLabel.setFont(fontBold);
        JLabel cityLabel = new JLabel("Ville");
        cityLabel.setFont(fontBold);
        JLabel postalCodeLabel = new JLabel("Code Postal");
        postalCodeLabel.setFont(fontBold);
        JLabel phoneLabel = new JLabel("Téléphone");
        phoneLabel.setFont(fontBold);
        JLabel mobileLabel = new JLabel("Portable");
        mobileLabel.setFont(fontBold);
        JButton deletePhone = new JButton(new ImageIcon("src/main/resources/telephoneDelete.png"));
        JButton deleteMobile = new JButton(new ImageIcon("src/main/resources/telephoneDelete.png"));
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(fontBold);

        MaskFormatter fmt = null;
        try {
            fmt = new MaskFormatter("## ## ## ## ##");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.phone = new JFormattedTextField(fmt);
        this.mobile = new JFormattedTextField(fmt);

        if (adherent != null) {
            this.additional = new JTextField(this.adherent.getAdditional());

            this.street = new JTextField(this.adherent.getAddress());

            this.city = new JTextField(this.adherent.getCity());

            this.postalCode = new JTextField(String.valueOf(this.adherent.getPostalCode()));

            this.phone.setValue(this.adherent.getPhone());
            this.mobile.setValue(this.adherent.getMobile());

            this.email = new JTextField(this.adherent.getEmail());
        }
        else {
            this.additional = new JTextField();
            this.street = new JTextField();
            this.city = new JTextField();
            this.postalCode = new JTextField();
            this.email = new JTextField();
        }

        this.additional.setBackground(new Color(239, 239, 239));
        this.street.setBackground(new Color(239, 239, 239));
        this.city.setBackground(new Color(239, 239, 239));
        this.postalCode.setBackground(new Color(239, 239, 239));
        this.email.setBackground(new Color(239,239,239));

        this.phone.setColumns(14);
        this.phone.setBackground(new Color(239, 239, 239));
        this.mobile.setColumns(14);
        this.mobile.setBackground(new Color(239, 239, 239));

        deletePhone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phone.setValue("");
            }
        });
        deleteMobile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mobile.setValue("");
            }
        });

        JButton emailButton = new JButton(new ImageIcon("src/main/resources/mail.png"));
        emailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if (!email.getText().equals(null))
                        Desktop.getDesktop().mail(new URI("mailto:" + email.getText()));
                } catch (URISyntaxException | IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        addToPanelAddressWithConstraints(panel, buildingLabel, streetLabel, postalCodeLabel, cityLabel, phoneLabel,
                deletePhone, emailLabel, mobileLabel, deleteMobile, emailButton);
    }

    private void addToPanelAddressWithConstraints(JPanel panel, JLabel buildingLabel, JLabel streetLabel, JLabel postalCodeLabel, JLabel cityLabel,
                                                  JLabel phoneLabel, JButton deletePhone, JLabel emailLabel, JLabel mobileLabel, JButton deleteMobile,
                                                  JButton emailButton){
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;

        panel.add(streetLabel, constraints);
        ++constraints.gridx;
        constraints.gridwidth = 5;
        constraints.weightx = 0.9;
        panel.add(this.street, constraints);
        constraints.gridx = 0;
        ++constraints.gridy;
        constraints.gridwidth = 1;
        constraints.weightx = 0.1;
        panel.add(buildingLabel, constraints);
        ++constraints.gridx;
        constraints.gridwidth = 5;
        constraints.weightx = 0.9;
        panel.add(this.additional, constraints);
        constraints.gridx = 0;
        ++constraints.gridy;
        constraints.gridwidth = 1;
        constraints.weightx = 0.1;
        panel.add(postalCodeLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.01;
        panel.add(this.postalCode, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.01;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(cityLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 1.5;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(this.city, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.1;
        panel.add(phoneLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 1.2;
        panel.add(this.phone, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.05;
        panel.add(deletePhone, constraints);
        constraints.gridx = 0;
        ++constraints.gridy;
        constraints.weightx = 0.1;
        panel.add(emailLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 1.2;
        constraints.gridwidth = 2;
        panel.add(this.email, constraints);
        constraints.gridx += 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.05;
        constraints.gridwidth = 1;
        panel.add(emailButton, constraints);
        ++constraints.gridx;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.05;
        panel.add(mobileLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 1.2;
        panel.add(this.mobile, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.05;
        panel.add(deleteMobile, constraints);
    }

    private void fillDiversePanel(JPanel panel){
        panel.setLayout(new GridLayout(1,2)); // Set a layout

        JPanel commentPanel = new JPanel();
        JPanel boatsPanel = new JPanel();

        commentPanel.setLayout(new GridLayout(1,1));
        boatsPanel.setLayout(new GridLayout(1,1));

        commentPanel.setBackground(Color.white);
        boatsPanel.setBackground(Color.white);

        if (adherent != null) {
            this.comment = new JTextArea(adherent.getComment()); // Area where the user can tap everything he wants
            this.boats = new JList(adherent.getBoats()); // Display all the boats of the Adherent
        }
        else{
            this.comment = new JTextArea();
            this.boats = new JList();
        }
        // Stop text area stretching
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setFont(fontBold);

        final JInternalFrame thisFrame = this;
        this.boats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                    BoatView boatView = null;
                    try {
                        boatView = new BoatView((Boat) boats.getModel().getElementAt(boats.locationToIndex(e.getPoint())), desktopPane);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(desktopPane);
                }
            }
        });
        boats.setFont(new Font(Font.DIALOG, Font.BOLD, 15));

        comment.setBackground(new Color(239,239,239));
        boats.setBackground(new Color(239,239,239));

        commentPanel.setBorder(BorderFactory.createTitledBorder("Commentaire"));
        boatsPanel.setBorder(BorderFactory.createTitledBorder("Bateaux"));

        LineBorder lineBorder = ((LineBorder)((TitledBorder)commentPanel.getBorder()).getBorder()); // Get the line border from comment Panel
        comment.setBorder(lineBorder);
        boats.setBorder(lineBorder);

        commentPanel.add(comment);
        boatsPanel.add(boats);

        panel.add(commentPanel);
        panel.add(boatsPanel);
    }

    private void fillButtonsPanel (JPanel panel){
        panel.setLayout(new GridBagLayout()); // Set a layout

        JButton submit = new JButton(new ImageIcon("src/main/resources/tick.png"));
        JButton cancel = new JButton(new ImageIcon("src/main/resources/cancel.png"));

        JInternalFrame thisFrame = this;
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adherent != null) {
                    adherent.setName(name.getText());
                    adherent.setSurname(surname.getText());
                    adherent.setDateOfBirth((Date) birth.getModel().getValue());
                    adherent.setSubscriptionYear(Integer.valueOf(subscription.getText()));
                    adherent.setGender(Adherent.GenderType.parse(genders.getSelection().getActionCommand()));
                    adherent.setAdditional(additional.getText());
                    adherent.setAddress(street.getText());
                    adherent.setCity(city.getText());
                    adherent.setPostalCode(postalCode.getText());
                    adherent.setMobile(mobile.getText());
                    adherent.setPhone(phone.getText());
                    adherent.setEmail(email.getText());
                    adherent.setComment(comment.getText());
                    JOptionPane.showMessageDialog(thisFrame, "Les informations de l'adhérent " + name.getText() + " " + surname.getText() +
                            " ont bien été modifié", "Adhérent modifié", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    Adherent adherent = new Adherent(Integer.valueOf(subscription.getText()), postalCode.getText(), name.getText(),
                            surname.getText(), additional.getText(), street.getText(), city.getText(), email.getText(), phone.getText(), mobile.getText(),
                            comment.getText(), (Date) birth.getModel().getValue(), Adherent.GenderType.parse(genders.getSelection().getActionCommand()),0);
                    JOptionPane.showMessageDialog(thisFrame, "L'adhérent " + name.getText() + " " + surname.getText() +
                            " a bien été ajouté", "Adhérent ajouté", JOptionPane.INFORMATION_MESSAGE);
                    DefaultListModel defaultListModel = (DefaultListModel) adherentList.getModel();
                    defaultListModel.add(defaultListModel.size(), adherent);
                    adherentList.setModel(defaultListModel);
                    JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                    Main.windowManagment.remove(thisFrame);
                    for (JInternalFrame frame : desktopPane.getAllFrames()){
                        if (frame == thisFrame)
                            desktopPane.remove(frame);
                    }
                    SwingUtilities.updateComponentTreeUI(desktopPane);
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.windowManagment.remove(thisFrame);
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

    public void updateBoatList (DefaultListModel<Boat> boatList){
        this.boats.setModel(boatList);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public Adherent getAdherent() {
        return adherent;
    }
}
