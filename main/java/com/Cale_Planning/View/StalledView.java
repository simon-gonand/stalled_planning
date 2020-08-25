package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.drawing.Colors;
import com.mindfusion.drawing.GradientBrush;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.text.*;
import java.util.*;
import java.util.Calendar;

public class StalledView extends JInternalFrame {
    private static JButton selectedColor;
    private Adherent selectedAdherent;
    private Contact cale1, cale2, cale3, cale4, cale5, cale6;
    private com.mindfusion.scheduling.Calendar calendar = new com.mindfusion.scheduling.Calendar();

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
                selectedAdherent = (Adherent) adherentJList.getModel().getElementAt(adherentJList.locationToIndex(e.getPoint()));
                boatJList.setModel(selectedAdherent.getBoats());
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
                    try {
                        fillBookingFormPanel(bookingFormPanel, boat);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
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

    private void fillBookingFormPanel(JPanel panel, Boat boat) throws ParseException {
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

    private void fillBookingPanel (JPanel panel) throws ParseException {
        JPanel stalledChoicePanel = new JPanel(new GridLayout(6,1));
        ButtonGroup stalledChoice = fillStalledChoicePanel(stalledChoicePanel);

        JPanel dateChoice = new JPanel(new GridBagLayout());
        Map<String, JDatePickerImpl> datePickers = fillDateChoicePanel(dateChoice);

        JPanel colorChoice = new JPanel(new GridLayout(3,3));
        colorChoice.setBorder(BorderFactory.createTitledBorder("Couleurs"));
        fillColorChoicePanel(colorChoice);

        JPanel amountDeposit = new JPanel (new GridLayout(2,1));
        JPanel amount = new JPanel (new GridLayout(2,1));
        JLabel amountLabel = new JLabel("Montant");
        NumberFormatter mask = new NumberFormatter();
        JFormattedTextField amountText = new JFormattedTextField(mask);

        JPanel deposit = new JPanel(new GridLayout(2,1));
        JLabel depositLabel = new JLabel("Caution");
        JTextField depositText = new JFormattedTextField(mask);

        amount.add(amountLabel);
        amount.add(amountText);
        deposit.add(depositLabel);
        deposit.add(depositText);

        amountDeposit.add(amount);
        amountDeposit.add(deposit);

        JPanel buttons = new JPanel (new GridLayout(2,1));
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                calendar.setDate(new DateTime(2020, DateTime.now().getMonth() + 2, 1));
//                calendar.setEndDate(calendar.getDate().addDays(31));
                switch (stalledChoice.getSelection().getActionCommand()){
                    case "Stalled1":
                        createAppointment(datePickers.get("from").getModel(), datePickers.get("to").getModel(), 0);
                        break;
                    case "Stalled2":
                        createAppointment(datePickers.get("from").getModel(), datePickers.get("to").getModel(), 1);
                        break;
                    case "Stalled3":
                        createAppointment(datePickers.get("from").getModel(), datePickers.get("to").getModel(), 2);
                        break;
                    case "Stalled4":
                        createAppointment(datePickers.get("from").getModel(), datePickers.get("to").getModel(), 3);
                        break;
                    case "Stalled5":
                        createAppointment(datePickers.get("from").getModel(), datePickers.get("to").getModel(), 4);
                        break;
                    case "Stalled6":
                        createAppointment(datePickers.get("from").getModel(), datePickers.get("to").getModel(), 5);
                        break;
                    default:
                        break;
                }
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositText.setText("");
                amountText.setText("");
                if (selectedColor != null) {
                    selectedColor.setBackground(selectedColor.getBackground().brighter());
                    selectedColor.setBorder(BorderFactory.createEmptyBorder());
                    selectedColor = null;
                }
                datePickers.get("to").getModel().setValue(null);
                datePickers.get("from").getModel().setValue(null);
                stalledChoice.clearSelection();
            }
        });
        buttons.add(submitButton);
        buttons.add(cancelButton);

        JPanel stalledAndButton = new JPanel(new GridBagLayout());
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

    private ButtonGroup fillStalledChoicePanel(JPanel stalledChoicePanel){
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
        toLabel.setHorizontalAlignment(JLabel.CENTER);
        JDatePickerImpl to = new JDatePickerImpl(new JDatePanelImpl(modelTo, properties), format);
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

    private void fillDownPanel(JPanel downPanel){
        downPanel.setLayout(new BorderLayout());

        calendar.beginInit();
        calendar.setTheme(ThemeType.Silver);
        calendar.setDate(DateTime.op_Subtraction(DateTime.now(), Duration.fromDays(DateTime.today().getDay() - 1)));
        calendar.setCurrentView(CalendarView.ResourceView);

        calendar.getResourceViewSettings().setAllowResizeRowHeaders(false);
        calendar.getResourceViewSettings().setRowHeaderSize(75);
        calendar.getResourceViewSettings().setViewStyle(ResourceViewStyle.Lanes);
        calendar.getResourceViewSettings().setVisibleRows(6);
        calendar.getResourceViewSettings().setSnapUnit(TimeUnit.Day);

        calendar.setGroupType(GroupType.GroupByContacts);
        calendar.setAllowInplaceCreate(true);

        cale1 = new Contact();
        cale1.setFirstName("Cale");
        cale1.setLastName("1");
        calendar.getContacts().add(cale1);

        cale2 = new Contact();
        cale2.setFirstName("Cale");
        cale2.setLastName("2");
        calendar.getContacts().add(cale2);

        cale3 = new Contact();
        cale3.setFirstName("Cale");
        cale3.setLastName("3");
        calendar.getContacts().add(cale3);

        cale4 = new Contact();
        cale4.setFirstName("Cale");
        cale4.setLastName("4");
        calendar.getContacts().add(cale4);

        cale5 = new Contact();
        cale5.setFirstName("Cale");
        cale5.setLastName("5");
        calendar.getContacts().add(cale5);

        cale6 = new Contact();
        cale6.setFirstName("Cale");
        cale6.setLastName("6");
        calendar.getContacts().add(cale6);

        calendar.endInit();

        downPanel.add(calendar, BorderLayout.CENTER);

        Appointment app = new Appointment();
        app.setHeaderText("Legras Yvon");
        app.setStartTime(new DateTime(2020, 8, 15));
        app.setEndTime(new DateTime(2020, 8, 17));
        app.getContacts().add(cale1);
        calendar.getSchedule().getItems().add(app);

        Appointment app2 = new Appointment();
        app2.setHeaderText("Petrus Louis");
        app2.setStartTime(new DateTime(2020, 8, 19));
        app2.setEndTime(new DateTime(2020, 8, 21));
        app2.getContacts().add(cale3);
        Style style = app2.getStyle();
        style.setLineColor(Colors.Goldenrod);
        style.setFillColor(new Color(250, 200, 100));
        style.setBrush(new GradientBrush(Colors.White, Colors.PaleGoldenrod, 90));
        style.setHeaderTextColor(Colors.DarkGoldenrod);

        calendar.getSchedule().getItems().add(app2);
    }

    private void createAppointment (DateModel startDate, DateModel endDate, int cale){
        Appointment appointment = new Appointment();
        appointment.setHeaderText(selectedAdherent.getSurname() + " " + selectedAdherent.getName());
        appointment.setStartTime(new DateTime(startDate.getYear(), startDate.getMonth()+1, startDate.getDay()));
        appointment.setEndTime(new DateTime(endDate.getYear(), endDate.getMonth()+1, endDate.getDay()));
        appointment.getContacts().add(calendar.getContacts().get(cale));
        Style style = appointment.getStyle();
        style.setLineColor(selectedColor.getBackground());
        style.setFillColor(selectedColor.getBackground());
        style.setBrush(new GradientBrush(Colors.White, Colors.PaleGoldenrod, 90));
        style.setHeaderTextColor(selectedColor.getBackground());

        calendar.getSchedule().getItems().add(appointment);
        calendar.repaint();
        SwingUtilities.updateComponentTreeUI(this);
    }

}
