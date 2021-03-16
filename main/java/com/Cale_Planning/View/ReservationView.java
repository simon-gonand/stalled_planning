package com.Cale_Planning.View;

import com.Cale_Planning.Controller.StalledController;
import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Reservation;
import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.model.ItemList;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationView extends JInternalFrame {
    private JDesktopPane mainPane;
    private com.mindfusion.scheduling.Calendar calendar;
    private Reservation reservation;
    private Map<String, JDatePickerImpl> datePickers;
    private JFormattedTextField amountText;
    private static JButton selectedColor;

    public ReservationView (JDesktopPane mainPane, JInternalFrame stalledView, com.mindfusion.scheduling.Calendar calendar, Reservation reservation) throws PropertyVetoException {
        super();

        setTitle("Planning Cale");
        this.getContentPane().setBackground(Color.white);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screenSize.width / 6 + stalledView.getWidth() / 2 - 450, screenSize.height / 2 - 350/2,
                900, 350);

        this.calendar = calendar;
        this.reservation = reservation;
        this.mainPane = mainPane;
        this.mainPane.add(this);

        setView();

        this.setSelected(true);
        this.setVisible(true);
        this.setResizable(true);
    }

    public void setView(){
        this.setLayout(new GridBagLayout());
        JLabel adherentName = new JLabel(reservation.getBoat().getOwner().getSurname() + " " + reservation.getBoat().getOwner().getName());
        adherentName.setFont(new Font(adherentName.getFont().getName(), Font.BOLD, 30));
        adherentName.setBackground(Color.white);

        JPanel boatInfo = new JPanel(new GridLayout(2,1));
        boatInfo.setBackground(Color.white);
        JLabel boatName = new JLabel(reservation.getBoat().getName());
        boatName.setFont(new Font(boatName.getFont().getName(), Font.BOLD, 20));
        boatInfo.add(boatName);
        JPanel info = new JPanel(new GridLayout(2, 5, 10,3));
        info.setBackground(Color.white);
        JLabel lengthLabel = new JLabel("Longueur");
        lengthLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel widthLabel = new JLabel("Largeur");
        widthLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel draughtLabel = new JLabel("Tirant d'eau");
        draughtLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel weightLabel = new JLabel("Poids");
        weightLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel categoryLabel = new JLabel("Type");
        categoryLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel length = new JLabel(String.valueOf(reservation.getBoat().getLength()));
        length.setHorizontalAlignment(JLabel.CENTER);
        JLabel width = new JLabel(String.valueOf(reservation.getBoat().getWidth()));
        width.setHorizontalAlignment(JLabel.CENTER);
        JLabel draught = new JLabel(String.valueOf(reservation.getBoat().getDraught()));
        draught.setHorizontalAlignment(JLabel.CENTER);
        JLabel weight = new JLabel(String.valueOf(reservation.getBoat().getWeight()));
        weight.setHorizontalAlignment(JLabel.CENTER);
        JLabel category = new JLabel(reservation.getBoat().getCategory().toString());
        category.setHorizontalAlignment(JLabel.CENTER);

        info.add(lengthLabel);
        info.add(widthLabel);
        info.add(draughtLabel);
        info.add(weightLabel);
        info.add(categoryLabel);
        info.add(length);
        info.add(width);
        info.add(draught);
        info.add(weight);
        info.add(category);

        boatInfo.add(info);

        JPanel booking = new JPanel(new GridBagLayout());
        booking.setBackground(Color.white);
        booking.setBorder(BorderFactory.createTitledBorder("Réservation"));
        fillBookingPanel(booking);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        this.add(adherentName, constraints);
        ++constraints.gridy;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(boatInfo, constraints);
        ++constraints.gridy;
        this.add(booking, constraints);
    }

    public void fillBookingPanel(JPanel panel) {
        JInternalFrame thisFrame = this;

        JPanel stalledChoicePanel = new JPanel(new GridLayout(6,1));
        ButtonGroup stalledChoice = fillStalledChoicePanel(stalledChoicePanel);
        stalledChoicePanel.setBackground(Color.white);

        JPanel dateChoice = new JPanel(new GridBagLayout());
        dateChoice.setBackground(Color.white);
        datePickers = fillDateChoicePanel(dateChoice);

        JPanel colorChoice = new JPanel(new GridLayout(3,3));
        colorChoice.setBackground(Color.white);
        colorChoice.setBorder(BorderFactory.createTitledBorder("Couleurs"));
        fillColorChoicePanel(colorChoice);

        JPanel amountDeposit = new JPanel (new GridLayout(2,1));
        amountDeposit.setBackground(Color.white);
        JPanel amount = new JPanel (new GridLayout(2,1));
        amount.setBackground(Color.white);
        JLabel amountLabel = new JLabel("Montant");
        NumberFormatter mask = new NumberFormatter();
        amountText = new JFormattedTextField(mask);
        amountText.setValue(reservation.getAmount());

        JPanel deposit = new JPanel(new GridLayout(2,1));
        deposit.setBackground(Color.white);
        JLabel depositLabel = new JLabel("Caution");
        JFormattedTextField depositText = new JFormattedTextField(mask);
        depositText.setValue(reservation.getDeposit());

        amount.add(amountLabel);
        amount.add(amountText);
        deposit.add(depositLabel);
        deposit.add(depositText);

        amountDeposit.add(amount);
        amountDeposit.add(deposit);

        JPanel buttons = new JPanel (new GridLayout(2,1));
        buttons.setBackground(Color.white);
        JButton submitButton = new JButton("Valider");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateModel modelStartDate = datePickers.get("from").getModel();
                DateModel modelEndDate = datePickers.get("to").getModel();
                if (modelStartDate.getValue() == null || modelEndDate.getValue() == null){
                    JOptionPane.showMessageDialog(thisFrame, "Vous n'avez pas saisi de dates", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DateTime startDate = new DateTime(modelStartDate.getYear(), modelStartDate.getMonth() + 1, modelStartDate.getDay(),
                        12, 00, 00);
                if (startDate.compareTo(DateTime.today()) == -1){
                    JOptionPane.showMessageDialog(thisFrame, "Vous ne pouvez pas saisir une date antérieure à la date d'aujourd'hui",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DateTime endDate = new DateTime(modelEndDate.getYear(), modelEndDate.getMonth() + 1, modelEndDate.getDay(),
                        12, 00, 00);
                if (endDate.compareTo(startDate) == 0){
                    JOptionPane.showMessageDialog(thisFrame, "La date de fin de réservation est la même que la date de début", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (reservation == null){
                    JOptionPane.showMessageDialog(thisFrame,"Vous n'avez pas sélectionnez de couleur","Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int cale = 0;
                switch (stalledChoice.getSelection().getActionCommand()){
                    case "Stalled1":
                        cale = 1;
                        break;
                    case "Stalled2":
                        cale = 2;
                        break;
                    case "Stalled3":
                        cale = 3;
                        break;
                    case "Stalled4":
                        cale = 4;
                        break;
                    case "Stalled5":
                        cale = 5;
                        break;
                    case "Stalled6":
                        cale = 6;
                        break;
                    default:
                        break;
                }

                ItemList reservations = calendar.getSchedule().getAllItems(startDate, endDate);
                for (int i = 0; i < reservations.size(); ++i){
                    if (reservations.get(i).getContacts().get(0).getLastName().equals(
                            reservation.getContacts().get(0).getLastName())) break;
                    if (reservations.get(i).getContacts().get(0).getLastName().equals(String.valueOf(cale))){
                        if (endDate.compareTo(reservations.get(i).getStartTime()) != 0) {
                            JOptionPane.showMessageDialog(thisFrame, "Une réservation a déjà lieu ce jour-ci sur la cale n° " + cale, "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                }

                StalledController.deleteAppointmentOfDatabase(reservation);
                int id = StalledController.addAppointmentToDatabase(reservation.getAdherent(), startDate, endDate, cale, selectedColor.getBackground().brighter(),
                        Float.valueOf(amountText.getValue().toString()), Float.valueOf(depositText.getValue().toString()), reservation.getBoat(),
                        reservation.isUpToDate());
                reservation.setID(id);
                reservation.setStartTime(startDate);
                reservation.setEndTime(endDate);
                reservation.getContacts().remove(0);
                reservation.getContacts().add(calendar.getContacts().get(cale - 1));
                reservation.setAmount(Float.valueOf(amountText.getValue().toString()));
                reservation.setDeposit(Float.valueOf(depositText.getValue().toString()));
                reservation.getStyle().setFillColor(selectedColor.getBackground().brighter());
                reservation.getStyle().setLineColor(selectedColor.getBackground().brighter());
                JOptionPane.showMessageDialog(thisFrame, "La réservation a bien été modifiée", "Modification réussie",
                        JOptionPane.INFORMATION_MESSAGE);

                calendar.repaint();
                Main.windowManagment.remove(thisFrame);
                JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, thisFrame);
                for (JInternalFrame frame : desktopPane.getAllFrames()){
                    if (frame == thisFrame)
                        desktopPane.remove(frame);
                }
                SwingUtilities.updateComponentTreeUI(desktopPane);
            }
        });
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
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
        buttons.add(submitButton);
        buttons.add(cancelButton);

        JPanel stalledAndButton = new JPanel(new GridBagLayout());
        stalledAndButton.setBackground(Color.white);
        GridBagConstraints stalledAndButtonConstraints = new GridBagConstraints();
        stalledAndButtonConstraints.gridx = 0;
        stalledAndButtonConstraints.gridy = 0;
        stalledAndButtonConstraints.weighty = 1;
        stalledAndButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        stalledAndButtonConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        stalledAndButton.add(stalledChoicePanel, stalledAndButtonConstraints);
        ++stalledAndButtonConstraints.gridy;
        stalledAndButtonConstraints.weighty = 1;
        stalledAndButton.add(buttons, stalledAndButtonConstraints);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(stalledAndButton, constraints);
        ++constraints.gridx;
        constraints.weighty = 0.2;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        panel.add(dateChoice, constraints);
        ++constraints.gridy;
        constraints.gridwidth = 1;
        constraints.weighty = 0.8;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(colorChoice, constraints);
        ++constraints.gridx;
        constraints.gridheight = 1;
        panel.add(amountDeposit, constraints);
    }

    public ButtonGroup fillStalledChoicePanel(JPanel stalledChoicePanel){
        JRadioButton stalled1 = new JRadioButton("Cale 1");
        stalled1.setActionCommand("Stalled1");
        stalled1.setBackground(Color.white);
        JRadioButton stalled2 = new JRadioButton("Cale 2");
        stalled2.setActionCommand("Stalled2");
        stalled2.setBackground(Color.white);
        JRadioButton stalled3 = new JRadioButton("Cale 3");
        stalled3.setActionCommand("Stalled3");
        stalled3.setBackground(Color.white);
        JRadioButton stalled4 = new JRadioButton("Cale 4");
        stalled4.setActionCommand("Stalled4");
        stalled4.setBackground(Color.white);
        JRadioButton stalled5 = new JRadioButton("Cale 5");
        stalled5.setActionCommand("Stalled5");
        stalled5.setBackground(Color.white);
        JRadioButton stalled6 = new JRadioButton("Cale 6");
        stalled6.setActionCommand("Stalled6");
        stalled6.setBackground(Color.white);

        switch (reservation.getContacts().get(0).getLastName()){
            case "1":
                stalled1.setSelected(true);
                break;
            case "2":
                stalled2.setSelected(true);
                break;
            case "3":
                stalled3.setSelected(true);
                break;
            case "4":
                stalled4.setSelected(true);
                break;
            case "5":
                stalled5.setSelected(true);
                break;
            case "6":
                stalled6.setSelected(true);
                break;
            default:
                break;
        }

        ButtonGroup stalledChoice = new ButtonGroup();
        stalledChoice.add(stalled1);
        stalledChoice.add(stalled2);
        stalledChoice.add(stalled3);
        stalledChoice.add(stalled4);
        stalledChoice.add(stalled5);
        stalledChoice.add(stalled6);

        stalledChoicePanel.add(stalled1);
        stalledChoicePanel.add(stalled2);
        stalledChoicePanel.add(stalled3);
        stalledChoicePanel.add(stalled4);
        stalledChoicePanel.add(stalled5);
        stalledChoicePanel.add(stalled6);
        return stalledChoice;
    }

    private Map<String, JDatePickerImpl> fillDateChoicePanel (JPanel dateChoice){
        UtilDateModel modelFrom = new UtilDateModel(reservation.getStartTime().toJavaCalendar().getTime());
        UtilDateModel modelTo = new UtilDateModel(reservation.getEndTime().toJavaCalendar().getTime());
        Properties properties = new Properties();
        JLabel fromLabel = new JLabel("Du");
        JFormattedTextField.AbstractFormatter format = new JFormattedTextField.AbstractFormatter() {
            private String datePattern = "dd/MM/yy";
            private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
            @Override
            public Object stringToValue(String text) throws ParseException {
                return dateFormatter.parseObject(text);
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                }

                return "";
            }
        };
        JDatePickerImpl from = new JDatePickerImpl(new JDatePanelImpl(modelFrom, properties), format);
        from.setBackground(Color.white);
        JLabel toLabel = new JLabel("au");
        toLabel.setHorizontalAlignment(JLabel.CENTER);
        JDatePickerImpl to = new JDatePickerImpl(new JDatePanelImpl(modelTo, properties), format);
        to.setBackground(Color.white);
        addListenerToDatePickers(from, to);
        HashMap<String, JDatePickerImpl> datePickers = new HashMap<>();
        datePickers.put("from", from);
        datePickers.put("to", to);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 0.2;
        dateChoice.add(fromLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 1;
        dateChoice.add(from, constraints);
        ++constraints.gridx;
        constraints.weightx = 0.2;
        dateChoice.add(toLabel, constraints);
        ++constraints.gridx;
        constraints.weightx = 1;
        dateChoice.add(to, constraints);

        return datePickers;
    }

    private void addListenerToDatePickers(JDatePickerImpl from, JDatePickerImpl to){
        from.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!to.getModel().isSelected()) {
                    to.getModel().setDate(from.getModel().getYear(), from.getModel().getMonth(), from.getModel().getDay());
                    to.getModel().setSelected(true);
                }
                DateTime fromTime = new DateTime (from.getModel().getYear(), from.getModel().getMonth()+1, from.getModel().getDay());
                DateTime toTime =  new DateTime(to.getModel().getYear(), to.getModel().getMonth()+1, to.getModel().getDay());

                if (fromTime.compareTo(toTime) == 1) {
                    to.getModel().setDate(from.getModel().getYear(), from.getModel().getMonth(), from.getModel().getDay());
                    toTime =  new DateTime(to.getModel().getYear(), to.getModel().getMonth()+1, to.getModel().getDay());
                }
                amountText.setValue(StalledController.calculateAmount(
                        fromTime, toTime));
            }
        });

        to.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!from.getModel().isSelected()) {
                    from.getModel().setDate(to.getModel().getYear(), to.getModel().getMonth(), to.getModel().getDay());
                    from.getModel().setSelected(true);
                }
                DateTime fromTime = new DateTime (from.getModel().getYear(), from.getModel().getMonth()+1, from.getModel().getDay());
                DateTime toTime =  new DateTime(to.getModel().getYear(), to.getModel().getMonth()+1, to.getModel().getDay());

                if (fromTime.compareTo(toTime) == 1) {
                    to.getModel().setDate(from.getModel().getYear(), from.getModel().getMonth(), from.getModel().getDay());
                    toTime =  new DateTime(to.getModel().getYear(), to.getModel().getMonth()+1, to.getModel().getDay());
                }
                amountText.setValue(StalledController.calculateAmount(
                        fromTime, toTime));
            }
        });
    }

    private void fillColorChoicePanel (JPanel colorChoice){
        JButton red = new JButton();
        JButton orange = new JButton();
        JButton yellow = new JButton();
        JButton green = new JButton();
        JButton cyan = new JButton();
        JButton blue = new JButton();
        JButton pink = new JButton();
        JButton purple = new JButton();
        JButton gray = new JButton();

        red.setBackground(Color.RED);
        orange.setBackground(Color.orange);
        yellow.setBackground(Color.yellow);
        green.setBackground(Color.green);
        cyan.setBackground(Color.cyan);
        blue.setBackground(Color.blue);
        pink.setBackground(Color.pink);
        purple.setBackground(Color.MAGENTA);
        gray.setBackground(Color.gray);

        selectedColor = null;
        switch (StalledController.colorToName(reservation.getStyle().getFillColor())){
            case "Red":
                selectedColor = red;
                break;
            case "Orange":
                selectedColor = orange;
                break;
            case "Yellow":
                selectedColor = yellow;
                break;
            case "Green":
                selectedColor = green;
                break;
            case "Cyan":
                selectedColor = cyan;
                break;
            case "Blue":
                selectedColor = blue;
                break;
            case "Pink":
                selectedColor = pink;
                break;
            case "Magenta":
                selectedColor = purple;
                break;
            case "Gray":
                selectedColor = gray;
                break;
            default:
                break;
        }
        if (selectedColor != null) {
            selectedColor.setBackground(selectedColor.getBackground().darker());
            selectedColor.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        }
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton actionedButton = (JButton) e.getSource();
                if (selectedColor != null) {
                    selectedColor.setBackground(selectedColor.getBackground().brighter());
                    selectedColor.setBorder(BorderFactory.createEmptyBorder());
                }
                selectedColor = actionedButton;
                actionedButton.setBackground(actionedButton.getBackground().darker());
                actionedButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            }
        };

        red.addActionListener(listener);
        orange.addActionListener(listener);
        yellow.addActionListener(listener);
        green.addActionListener(listener);
        cyan.addActionListener(listener);
        blue.addActionListener(listener);
        pink.addActionListener(listener);
        purple.addActionListener(listener);
        gray.addActionListener(listener);

        colorChoice.add(red);
        colorChoice.add(orange);
        colorChoice.add(yellow);
        colorChoice.add(green);
        colorChoice.add(cyan);
        colorChoice.add(blue);
        colorChoice.add(pink);
        colorChoice.add(purple);
        colorChoice.add(gray);
    }

    public Reservation getReservation() {
        return reservation;
    }
}
