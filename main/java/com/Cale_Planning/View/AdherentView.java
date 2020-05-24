package com.Cale_Planning.View;

import com.Cale_Planning.Models.Adherent;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.util.Calendar;
import java.util.Properties;

public class AdherentView extends JInternalFrame {
    private Adherent adherent;
    private JTextField name, surname, subscription, building, street, postalCode, city, phone, mobile, email;
    private JDatePicker birth;
    private ButtonGroup genders;
    private JTextArea comment;
    private JList boats;

    public AdherentView(Adherent adherent){
        super();
        this.adherent = adherent;
        setTitle("Fiche Adhérent");
        this.getContentPane().setBackground(Color.white);
        setLayout(new GridBagLayout());
        setView();
        setVisible(true);
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

        JRadioButton mister = new JRadioButton(Adherent.genderType.MISTER.toString());
        mister.setActionCommand(Adherent.genderType.MISTER.toString());
        JRadioButton miss = new JRadioButton(Adherent.genderType.MISS.toString());
        miss.setActionCommand(Adherent.genderType.MISS.toString());
        JRadioButton noGender = new JRadioButton(Adherent.genderType.NO_GENDER.toString());
        noGender.setActionCommand(Adherent.genderType.NO_GENDER.toString());

        switch (adherent.getGender()){
            case MISTER:
                mister.setSelected(true);
                break;
            case MISS:
                miss.setSelected(true);
                break;
            case NO_GENDER:
                noGender.setSelected(true);
                break;
            default: break;
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
        JLabel labelSurname = new JLabel("Nom");
        this.name = new JTextField(adherent.getName());
        this.surname = new JTextField(adherent.getSurname());

        JLabel birthLabel = new JLabel("Date naissance");
        JLabel subscriptionLabel = new JLabel("Date adhésion");
        this.birth = new JDateComponentFactory().createJDatePicker();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.adherent.getDateOfBirth());
        this.birth.getModel().setDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        this.subscription = new JTextField(String.valueOf(this.adherent.getSubscriptionYear()));

        this.name.setBackground(new Color(239,239,239));
        this.surname.setBackground(new Color(239,239,239));
        this.subscription.setBackground(new Color(239,239,239));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
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

    }

    private void fillDiversePanel(JPanel panel){
        panel.setLayout(new GridLayout(1,2)); // Set a layout

        JPanel commentPanel = new JPanel();
        JPanel boatsPanel = new JPanel();

        commentPanel.setLayout(new GridLayout(1,1));
        boatsPanel.setLayout(new GridLayout(1,1));

        commentPanel.setBackground(Color.white);
        boatsPanel.setBackground(Color.white);

        this.comment = new JTextArea(adherent.getComment()); // Area where the user can tap everything he wants
        // Stop text area stretching
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);

        this.boats = new JList(adherent.getBoats()); // Display all the boats of the Adherent

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

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(genders.getSelection().getActionCommand());
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
