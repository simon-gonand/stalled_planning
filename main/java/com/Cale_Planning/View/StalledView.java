package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class StalledView extends JInternalFrame {
    public StalledView (JDesktopPane mainPane) throws PropertyVetoException {
        super();

        setTitle("Planning Cale");
        this.getContentPane().setBackground(Color.white);

        setLayout(new GridLayout(2,1));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screenSize.width / 6, 0, screenSize.width - (screenSize.width/6), screenSize.height - screenSize.height/13);

        setView();

        mainPane.add(this);
        this.setSelected(true);
        this.setVisible(true);
        this.setResizable(true);
    }

    private void setView (){
        JPanel upPanel = new JPanel();
        JPanel downPanel = new JPanel();

        fillUpPanel(upPanel);
        fillDownPanel(downPanel);

        this.add(upPanel);
        this.add(downPanel);
    }

    private void fillUpPanel(JPanel upPanel){
        upPanel.setLayout(new GridLayout(1, 2));

        JPanel bookingPlanningPanel = new JPanel();
        JPanel historicPanel = new JPanel();

        fillBookingPlanningPanel(bookingPlanningPanel);

        upPanel.add(bookingPlanningPanel);
        upPanel.add(historicPanel);
    }

    private void fillBookingPlanningPanel(JPanel panel){
        panel.setLayout(new GridBagLayout());
        JPanel adherentAndBoatChoice = new JPanel(new GridLayout(2,1));

        Adherent[] allAdherents = AdherentController.getAllAdherent();
        JList<Adherent> adherentJList = new JList<>(allAdherents);
        JList<Boat> boatJList = new JList<>();
        final JInternalFrame thisFrame = this;
        adherentJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Adherent adherent = (Adherent) adherentJList.getModel().getElementAt(adherentJList.locationToIndex(e.getPoint()));
                boatJList.setModel(adherent.getBoats());
                SwingUtilities.updateComponentTreeUI(thisFrame);
            }
        });

        JPanel bookingFormPanel = new JPanel();
        boatJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    Boat boat = (Boat) boatJList.getModel().getElementAt(boatJList.locationToIndex(e.getPoint()));
                    adherentAndBoatChoice.remove(adherentJList);
                    adherentAndBoatChoice.remove(boatJList);
                    JButton newReservationButton = new JButton("Nouvelle Réservation");
                    adherentAndBoatChoice.setLayout(new GridBagLayout());
                    newReservationButton.setBounds(adherentAndBoatChoice.getWidth(),0, adherentAndBoatChoice.getWidth(), 20);
                    newReservationButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            panel.remove(bookingFormPanel);
                            panel.remove(adherentAndBoatChoice);
                            fillBookingPlanningPanel(panel);
                            SwingUtilities.updateComponentTreeUI(thisFrame);
                        }
                    });

                    GridBagConstraints constraints = new GridBagConstraints();
                    constraints.gridx = 0;
                    constraints.gridwidth = 1;
                    constraints.ipady = 20;
                    constraints.weightx = 0.2;
                    adherentAndBoatChoice.add(newReservationButton, constraints);
                    fillBookingFormPanel(bookingFormPanel, boat);
                    ++constraints.gridx;
                    constraints.weightx = 0.8;
                    constraints.ipady = 0;
                    adherentAndBoatChoice.add(bookingFormPanel, constraints);

                    SwingUtilities.updateComponentTreeUI(thisFrame);
                }
            }
        });

        adherentAndBoatChoice.add(adherentJList);
        adherentAndBoatChoice.add(boatJList);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(adherentAndBoatChoice, constraints);
    }

    private void fillBookingFormPanel(JPanel panel, Boat boat){
        panel.setLayout(new GridBagLayout());
        JLabel adherentName = new JLabel(boat.getOwner().getSurname() + " " + boat.getOwner().getName());
        adherentName.setFont(new Font(adherentName.getFont().getName(), Font.BOLD, 30));

        JPanel boatInfo = new JPanel(new GridLayout(2,1));
        JLabel boatName = new JLabel(boat.getName());
        boatName.setFont(new Font(boatName.getFont().getName(), Font.BOLD, 20));
        boatInfo.add(boatName);
        JPanel info = new JPanel(new GridLayout(2, 5, 10,3));
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
        JLabel length = new JLabel(String.valueOf(boat.getLength()));
        length.setHorizontalAlignment(JLabel.CENTER);
        JLabel width = new JLabel(String.valueOf(boat.getWidth()));
        width.setHorizontalAlignment(JLabel.CENTER);
        JLabel draught = new JLabel(String.valueOf(boat.getDraught()));
        draught.setHorizontalAlignment(JLabel.CENTER);
        JLabel weight = new JLabel(String.valueOf(boat.getWeight()));
        weight.setHorizontalAlignment(JLabel.CENTER);
        JLabel category = new JLabel(boat.getCategory().toString());
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
        booking.setBorder(BorderFactory.createTitledBorder("Réservation"));
        fillBookingPanel(booking);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        panel.add(adherentName, constraints);
        ++constraints.gridy;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(boatInfo, constraints);
        ++constraints.gridy;
        panel.add(booking, constraints);
    }

    private void fillBookingPanel (JPanel panel){
        JPanel stalledChoicePanel = new JPanel(new GridLayout(10,1));
        ButtonGroup stalledChoice = new ButtonGroup();
        fillStalledChoicePanel(stalledChoicePanel, stalledChoice);

        JPanel dateChoice = new JPanel(new GridBagLayout());
        UtilDateModel modelFrom = new UtilDateModel();
        UtilDateModel modelTo = new UtilDateModel();
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
        JLabel toLabel = new JLabel("au");
        JDatePickerImpl to = new JDatePickerImpl(new JDatePanelImpl(modelTo, properties), format);
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

        JPanel colorChoice = new JPanel(new GridLayout(3,3));
        colorChoice.setBorder(BorderFactory.createTitledBorder("Couleurs"));
        JPanel amount = new JPanel (new GridLayout(1,2));
        JPanel deposit = new JPanel (new GridLayout(1,2));
        JPanel buttons = new JPanel (new GridBagLayout());

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(stalledChoicePanel, constraints);
        ++constraints.gridx;
        constraints.weighty = 0.5;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        panel.add(dateChoice, constraints);
        ++constraints.gridy;
        constraints.gridwidth = 1;
        constraints.weighty = 1;
        panel.add(colorChoice, constraints);
        ++constraints.gridx;
        panel.add(amount, constraints);
        constraints.gridx = 0;
        ++constraints.gridy;
        panel.add(deposit, constraints);
        ++constraints.gridy;
        panel.add(buttons, constraints);
    }

    private void fillStalledChoicePanel(JPanel stalledChoicePanel, ButtonGroup stalledChoice){
        JRadioButton stalled1 = new JRadioButton("Cale 1");
        stalled1.setActionCommand("Stalled1");
        JRadioButton stalled2 = new JRadioButton("Cale 2");
        stalled2.setActionCommand("Stalled2");
        JRadioButton stalled3 = new JRadioButton("Cale 3");
        stalled3.setActionCommand("Stalled3");
        JRadioButton stalled4 = new JRadioButton("Cale 4");
        stalled4.setActionCommand("Stalled4");
        JRadioButton stalled5 = new JRadioButton("Cale 5");
        stalled5.setActionCommand("Stalled5");
        JRadioButton stalled6 = new JRadioButton("Cale 6");
        stalled6.setActionCommand("Stalled6");
        JRadioButton stalled7 = new JRadioButton("Cale 7");
        stalled7.setActionCommand("Stalled7");
        JRadioButton stalled8 = new JRadioButton("Cale 8");
        stalled8.setActionCommand("Stalled8");
        JRadioButton stalled9 = new JRadioButton("Cale 9");
        stalled9.setActionCommand("Stalled9");
        JRadioButton stalled10 = new JRadioButton("Cale 10");
        stalled10.setActionCommand("Stalled10");

        stalledChoice.add(stalled1);
        stalledChoice.add(stalled2);
        stalledChoice.add(stalled3);
        stalledChoice.add(stalled4);
        stalledChoice.add(stalled5);
        stalledChoice.add(stalled6);
        stalledChoice.add(stalled7);
        stalledChoice.add(stalled8);
        stalledChoice.add(stalled9);
        stalledChoice.add(stalled10);

        stalledChoicePanel.add(stalled1);
        stalledChoicePanel.add(stalled2);
        stalledChoicePanel.add(stalled3);
        stalledChoicePanel.add(stalled4);
        stalledChoicePanel.add(stalled5);
        stalledChoicePanel.add(stalled6);
        stalledChoicePanel.add(stalled7);
        stalledChoicePanel.add(stalled8);
        stalledChoicePanel.add(stalled9);
        stalledChoicePanel.add(stalled10);
    }

    private void fillDownPanel(JPanel downPanel){

    }
}
