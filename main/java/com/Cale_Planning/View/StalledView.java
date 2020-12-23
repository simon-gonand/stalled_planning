package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Controller.StalledController;
import com.Cale_Planning.MSAccessBase;
import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import com.Cale_Planning.Models.Reservation;

import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.drawing.Colors;
import com.mindfusion.drawing.TextAlignment;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.usermodel.*;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;;
import java.beans.PropertyVetoException;
import java.io.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.Calendar;
import java.util.List;

public class StalledView extends JInternalFrame {
    private static JButton selectedColor;
    private Adherent selectedAdherent;
    private Boat selectedBoat;
    private JFormattedTextField amountText;
    private Contact cale1, cale2, cale3, cale4, cale5, cale6;
    private JDesktopPane mainPane;
    private com.mindfusion.scheduling.Calendar calendar = new com.mindfusion.scheduling.Calendar();
    private Map<String, JDatePickerImpl> datePickers;
    private MSAccessBase database;
    private JCheckBox isUpToDate;

    private JList adherentJList;
    private DefaultListModel defaultAdherentList;

    public StalledView (JDesktopPane mainPane) throws PropertyVetoException {
        super();

        this.database = Main.getDatabase();

        setTitle("Planning Cale");
        this.getContentPane().setBackground(Color.white);

        setLayout(new GridLayout(2,1));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screenSize.width / 6, 0, screenSize.width - (screenSize.width/6), screenSize.height - screenSize.height/13);

        setView();

        this.mainPane = mainPane;
        this.mainPane.add(this);
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
        fillHistoricPanel(historicPanel);

        upPanel.add(bookingPlanningPanel);
        upPanel.add(historicPanel);
    }

    private void fillBookingPlanningPanel(JPanel panel){
        panel.setLayout(new GridBagLayout());
        JPanel adherentAndBoatChoice = new JPanel(new GridBagLayout());
        adherentAndBoatChoice.setBackground(Color.white);

        JPanel searchPanel = dynamicSearchView();
        defaultAdherentList = AdherentController.getAllAdherent();
        adherentJList = new JList<>(defaultAdherentList);
        adherentJList.setFont(new Font(adherentJList.getFont().getName(), Font.BOLD, 15));
        JScrollPane adherentScrollPane = new JScrollPane(adherentJList);
        adherentScrollPane.setBackground(Color.white);
        JList<Boat> boatJList = new JList<>();
        boatJList.setFont(new Font(adherentJList.getFont().getName(), Font.BOLD, 15));
        JScrollPane boatScrollPane = new JScrollPane(boatJList);
        boatScrollPane.setBackground(Color.white);

        JLabel adherentLabel = new JLabel("Créer une réservation, veuillez sélectionner un adhérent");
        adherentLabel.setFont(new Font(adherentLabel.getFont().getName(), Font.BOLD, 16));
        adherentLabel.setBackground(Color.white);
        adherentLabel.setOpaque(true);
        JLabel boatLabel = new JLabel("Veuillez sélectionner un bateau");
        boatLabel.setFont(new Font(boatLabel.getFont().getName(), Font.BOLD, 16));
        boatLabel.setBackground(Color.white);
        boatLabel.setOpaque(true);
        boatLabel.setVisible(false);

        final JInternalFrame thisFrame = this;
        adherentJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedAdherent = (Adherent) adherentJList.getModel().getElementAt(adherentJList.locationToIndex(e.getPoint()));
                boatLabel.setVisible(true);
                boatJList.setModel(selectedAdherent.getBoats());
                SwingUtilities.updateComponentTreeUI(thisFrame);
            }
        });

        JPanel bookingFormPanel = new JPanel();
        bookingFormPanel.setBackground(Color.white);
        boatJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    selectedBoat = (Boat) boatJList.getModel().getElementAt(boatJList.locationToIndex(e.getPoint()));
                    adherentAndBoatChoice.remove(searchPanel);
                    adherentAndBoatChoice.remove(adherentJList);
                    adherentAndBoatChoice.remove(adherentScrollPane);
                    adherentAndBoatChoice.remove(boatJList);
                    adherentAndBoatChoice.remove(boatScrollPane);
                    adherentAndBoatChoice.remove(adherentLabel);
                    adherentAndBoatChoice.remove(boatLabel);
                    JPanel buttons = new JPanel(new GridBagLayout());
                    buttons.setBackground(Color.white);
                    JButton newReservationButton = new JButton("Nouvelle Réservation");
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
                    JButton printButton = new JButton(new ImageIcon("src/main/resources/printer.png"));
                    printButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Set dates and adherent information into the supported document
                            try {
                                XWPFDocument document = new XWPFDocument(new FileInputStream("src/main/resources/stalledDoc.docx"));
                                for (XWPFParagraph p : document.getParagraphs()) {
                                    for (int i = 0; i < p.getRuns().size(); ++i) {
                                        XWPFRun run = p.getRuns().get(i);
                                        String text = run.getText(0);
                                        if (run.getText(0) == null)
                                            continue;
                                        if (datePickers.get("from").getModel().getValue() == null ||
                                                datePickers.get("to").getModel().getValue() == null) {
                                            JOptionPane.showMessageDialog(thisFrame, "Vous n'avez pas saisi de dates", "Erreur",
                                                    JOptionPane.ERROR_MESSAGE);
                                             return;
                                        }
                                        if (text.contains("Du ")){
                                            DateModel from = datePickers.get("from").getModel();
                                            int month = from.getMonth() + 1;
                                            String sFrom = from.getDay() + "/" + month + "/" + from.getYear();
                                            text = text.replace("Du ",
                                                    sFrom);
                                            run.setText(text);
                                        }

                                        if (text.contains("au") && text.length() <= 3){
                                            XWPFRun nextRun = p.getRuns().get(i + 1);
                                            if (nextRun.getText(0).equals(" ")) {
                                                DateModel to = datePickers.get("to").getModel();
                                                int month = to.getMonth() + 1;
                                                String sTo = to.getDay() + "/" + month + "/" + to.getYear();
                                                text = text.replace("au",
                                                        sTo);
                                                run.setText(text);
                                            }
                                        }

                                        if (text.contains("Date") && !text.contains("Date et")) {
                                            XWPFRun nextRun = p.getRuns().get(i + 1);
                                            String nextText = nextRun.text();
                                            if (nextText.contains(":")) {
                                                DateTime date = DateTime.today();
                                                String sDate = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
                                                nextText = nextText.replace(":", sDate);
                                                nextRun.setText(nextText);
                                            }
                                        }
                                    }

                                    // Get text in text boxes by using method describes in this url:
                                    // https://stackoverflow.com/questions/46802369/replace-text-in-text-box-of-docx-by-using-apache-poi
                                    XmlCursor cursor = p.getCTP().newCursor();
                                    cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r");

                                    List<XmlObject> ctrsintxtbx = new ArrayList<XmlObject>();

                                    while(cursor.hasNextSelection()) {
                                        cursor.toNextSelection();
                                        XmlObject obj = cursor.getObject();
                                        ctrsintxtbx.add(obj);
                                    }
                                    for (XmlObject obj : ctrsintxtbx) {
                                        CTR ctr = CTR.Factory.parse(obj.xmlText());
                                        XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody)p);
                                        String text = bufferrun.getText(0);
                                        if (text != null && text.contains("Prénom")) {
                                            text = text.replace("Prénom :", "Prénom : " + selectedAdherent.getName());
                                            bufferrun.setText(text, 0);
                                        }
                                        if (text != null && text.contains("Nom")) {
                                            text = text.replace("Nom :", "Nom : " + selectedAdherent.getSurname());
                                            bufferrun.setText(text, 0);
                                        }
                                        if (text != null && text.contains("Bateau")) {
                                            text = text.replace("Bateau :", "Bateau : " + selectedBoat.getName());
                                            bufferrun.setText(text, 0);
                                        }
                                        if (text != null && text.contains("Tel")) {
                                            String phoneNumber = "";
                                                if (selectedAdherent.getMobile() != null)
                                                    phoneNumber = selectedAdherent.getMobile();
                                                else if (selectedAdherent.getPhone() != null)
                                                    phoneNumber = selectedAdherent.getPhone();
                                            text = text.replace("Tel :", "Tel : " + phoneNumber);
                                            bufferrun.setText(text, 0);
                                        }
                                        obj.set(bufferrun.getCTR());
                                    }
                                }
                                document.write(new FileOutputStream("src/main/resources/newDoc.docx"));
                                document.close();
                            } catch (IOException | XmlException ex) {
                                ex.printStackTrace();
                            }

                            // Print the document
                            // First convert the document into a PDF

                        }
                    });

                    GridBagConstraints buttonsConstraints = new GridBagConstraints();
                    buttonsConstraints.gridx = 0;
                    buttonsConstraints.gridy = 0;
                    buttonsConstraints.gridwidth = 1;
                    buttonsConstraints.weightx = 1;
                    buttonsConstraints.weighty = 1;
                    buttonsConstraints.ipady = 20;
                    buttonsConstraints.anchor = GridBagConstraints.CENTER;
                    buttonsConstraints.fill = GridBagConstraints.HORIZONTAL;
                    buttons.add(newReservationButton, buttonsConstraints);
                    ++buttonsConstraints.gridy;
                    buttonsConstraints.ipady = 0;
                    buttonsConstraints.fill = GridBagConstraints.NONE;
                    buttons.add(printButton, buttonsConstraints);

                    GridBagConstraints constraints = new GridBagConstraints();
                    constraints.gridx = 0;
                    constraints.gridy = 0;
                    constraints.gridwidth = 1;
                    constraints.fill = GridBagConstraints.HORIZONTAL;
                    constraints.ipady = 20;
                    constraints.weightx = 0.2;
                    adherentAndBoatChoice.add(buttons, constraints);
                    try {
                        fillBookingFormPanel(bookingFormPanel);
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



        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weightx = 1;
        constraints.weighty = 0.1;
        adherentAndBoatChoice.add(adherentLabel, constraints);
        ++constraints.gridy;
        adherentAndBoatChoice.add(searchPanel, constraints);
        ++constraints.gridy;
        constraints.weighty = 1;
        adherentAndBoatChoice.add(adherentScrollPane, constraints);
        ++constraints.gridy;
        constraints.weighty = 0.1;
        adherentAndBoatChoice.add(boatLabel, constraints);
        ++constraints.gridy;
        constraints.weighty = 1;
        adherentAndBoatChoice.add(boatScrollPane, constraints);

        constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(adherentAndBoatChoice, constraints);
    }

    private JPanel dynamicSearchView(){
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Rechercher");
        searchLabel.setFont(new Font(searchLabel.getFont().getName(), Font.BOLD, 15));
        JTextField searchText = new JTextField();

        searchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                DefaultListModel model = new DefaultListModel();
                String enteredText = searchText.getText();
                if (enteredText.equals(""))
                    adherentJList.setModel(defaultAdherentList);
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    for (int i = 0; i < defaultAdherentList.getSize(); i++) {
                        if (defaultAdherentList.getElementAt(i).toString().toLowerCase().indexOf(enteredText.toLowerCase()) != -1) {
                            model.addElement(defaultAdherentList.getElementAt(i));
                        }
                    }
                    adherentJList.setModel(model);
                }
                else {
                    for (int i = 0; i < adherentJList.getModel().getSize(); i++) {
                        if (adherentJList.getModel().getElementAt(i).toString().toLowerCase().indexOf(enteredText.toLowerCase()) != -1) {
                            model.addElement(adherentJList.getModel().getElementAt(i));
                        }
                    }
                    adherentJList.setModel(model);
                }
            }
        });

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchText, BorderLayout.CENTER);
        return searchPanel;
    }

    private void fillBookingFormPanel(JPanel panel) throws ParseException {
        panel.setLayout(new GridBagLayout());
        JLabel adherentName = new JLabel(selectedBoat.getOwner().getSurname() + " " + selectedBoat.getOwner().getName());
        adherentName.setFont(new Font(adherentName.getFont().getName(), Font.BOLD, 30));
        adherentName.setBackground(Color.white);

        JPanel boatInfo = new JPanel(new GridLayout(2,1));
        boatInfo.setBackground(Color.white);
        JLabel boatName = new JLabel(selectedBoat.getName());
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
        JLabel length = new JLabel(String.valueOf(selectedBoat.getLength()));
        length.setHorizontalAlignment(JLabel.CENTER);
        JLabel width = new JLabel(String.valueOf(selectedBoat.getWidth()));
        width.setHorizontalAlignment(JLabel.CENTER);
        JLabel draught = new JLabel(String.valueOf(selectedBoat.getDraught()));
        draught.setHorizontalAlignment(JLabel.CENTER);
        JLabel weight = new JLabel(String.valueOf(selectedBoat.getWeight()));
        weight.setHorizontalAlignment(JLabel.CENTER);
        JLabel category = new JLabel(selectedBoat.getCategory().toString());
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

        isUpToDate = new JCheckBox("Cotisation à jour");
        isUpToDate.setBackground(Color.white);

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
        ++constraints.gridy;
        panel.add(isUpToDate, constraints);
    }

    private void fillBookingPanel (JPanel panel) throws ParseException {
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

        JPanel deposit = new JPanel(new GridLayout(2,1));
        deposit.setBackground(Color.white);
        JLabel depositLabel = new JLabel("Caution");
        JFormattedTextField depositText = new JFormattedTextField(mask);
        depositText.setValue(150.00);

        amount.add(amountLabel);
        amount.add(amountText);
        deposit.add(depositLabel);
        deposit.add(depositText);

        amountDeposit.add(amount);
        amountDeposit.add(deposit);

        JPanel buttons = new JPanel (new GridLayout(2,1));
        buttons.setBackground(Color.white);
        JButton submitButton = new JButton("Submit");
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
               DateTime startDate = new DateTime(modelStartDate.getYear(), modelStartDate.getMonth() + 1, modelStartDate.getDay());
               if (startDate.compareTo(DateTime.today()) == -1){
                   JOptionPane.showMessageDialog(thisFrame, "Vous ne pouvez pas saisir une date antérieure à la date d'aujourd'hui",
                           "Erreur", JOptionPane.ERROR_MESSAGE);
                   return;
               }
               DateTime endDate = new DateTime(modelEndDate.getYear(), modelEndDate.getMonth() + 1, modelEndDate.getDay());
               if (endDate.compareTo(startDate) == 0){
                   JOptionPane.showMessageDialog(thisFrame, "La date de fin de réservation est la même que la date de début", "Erreur",
                           JOptionPane.ERROR_MESSAGE);
                   return;
               }

               if (selectedColor == null){
                   JOptionPane.showMessageDialog(thisFrame,"Vous n'avez pas sélectionnez de couleur","Erreur",
                           JOptionPane.ERROR_MESSAGE);
                   return;
               }
               Color color = selectedColor.getBackground().brighter();
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
                    if (reservations.get(i).getContacts().get(0).getLastName().equals(String.valueOf(cale))){
                        if (endDate.compareTo(reservations.get(i).getStartTime()) != 0) {
                            JOptionPane.showMessageDialog(thisFrame, "Une réservation a déjà lieu ce jour-ci sur la cale n° " + cale, "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                }
                int id = StalledController.addAppointmentToDatabase(selectedAdherent, startDate, endDate, cale, color,
                        Float.valueOf(amountText.getValue().toString()), Float.valueOf(depositText.getValue().toString()), selectedBoat,
                        isUpToDate.isSelected());
                StalledController.createAppointment(calendar, startDate, endDate, cale, selectedAdherent, color, id,
                        Float.valueOf(amountText.getValue().toString()), Float.valueOf(depositText.getValue().toString()), selectedBoat,
                        isUpToDate.isSelected());

                JOptionPane.showMessageDialog(thisFrame, "La réservation a bien été enregistrée", "Réservation réussie",
                        JOptionPane.INFORMATION_MESSAGE);
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

    private ButtonGroup fillStalledChoicePanel(JPanel stalledChoicePanel){
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

    private void fillHistoricPanel(JPanel historicPanel){
        historicPanel.setLayout(new BorderLayout());
        JPanel monthChoicePanel = new JPanel(new BorderLayout());
        JLabel monthChoiceTitle = new JLabel("Choix du mois");
        monthChoiceTitle.setFont(new Font(monthChoiceTitle.getFont().getName(), Font.BOLD, 15));
        monthChoiceTitle.setHorizontalAlignment(JLabel.CENTER);
        monthChoicePanel.add(monthChoiceTitle, BorderLayout.CENTER);

        JPanel closePanel = new JPanel(null);
        final JInternalFrame thisFrame = this;
        JButton close = new JButton(new ImageIcon("src/main/resources/cancel.png"));
        close.setBounds(thisFrame.getWidth() / 2 - 35,0, 30,30);
        close.setOpaque(true);
        close.addActionListener(new ActionListener() {
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

        closePanel.add(close);
        historicPanel.add(closePanel);


        JButton previousMonth = new JButton();
        JButton nextMonth = new JButton();
        previousMonth.setIcon(new ImageIcon("src/main/resources/leftArrow.png"));
        nextMonth.setIcon(new ImageIcon("src/main/resources/rightArrow.png"));
        previousMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setDate(new DateTime(calendar.getDate().getYear(), calendar.getDate().getMonth() - 1, 1));
                calendar.setEndDate(calendar.getDate().addDays(31));
            }
        });
        nextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setDate(new DateTime(calendar.getDate().getYear(), calendar.getDate().getMonth() + 1, 1));
                calendar.setEndDate(calendar.getDate().addDays(31));
            }
        });

        monthChoicePanel.add(previousMonth, BorderLayout.WEST);
        monthChoicePanel.add(nextMonth, BorderLayout.EAST);
        historicPanel.add(monthChoicePanel, BorderLayout.SOUTH);
    }

    private void fillDownPanel(JPanel downPanel){
        downPanel.setLayout(new GridBagLayout());

        calendar.beginInit();
        calendar.setTheme(ThemeType.Silver);
        calendar.setDate(DateTime.op_Subtraction(DateTime.now(), Duration.fromDays(DateTime.today().getDay() - 1)));
        calendar.setCurrentView(CalendarView.ResourceView);

        calendar.getResourceViewSettings().setAllowResizeRowHeaders(false);
        calendar.getResourceViewSettings().setRowHeaderSize(75);
        calendar.getResourceViewSettings().setViewStyle(ResourceViewStyle.Lanes);
        calendar.getResourceViewSettings().setVisibleRows(6);
        calendar.getResourceViewSettings().setSnapUnit(TimeUnit.Day);
        calendar.getResourceViewSettings().setMinResourceLength(200);
        calendar.getResourceViewSettings().setLaneSize(50);
        calendar.getResourceViewSettings().getStyle().setHeaderFont(new Font("Verdana", Font.BOLD, 13));
        calendar.getResourceViewSettings().getStyle().setHeaderTextAlignment(EnumSet.of(TextAlignment.MiddleCenter));
        calendar.getResourceViewSettings().setExpandableRows(false);

        calendar.getResourceViewSettings().getBottomTimelineSettings().setSize(30);
        calendar.getResourceViewSettings().getBottomTimelineSettings().getStyle().setHeaderFont(new Font("Verdana", Font.BOLD, 13));

        calendar.getItemSettings().getStyle().setHeaderFont(new Font(Font.DIALOG, Font.PLAIN, 15));

        calendar.setGroupType(GroupType.GroupByContacts);
        calendar.setAllowInplaceCreate(true);
        calendar.setAllowInplaceEdit(false);
        calendar.setAllowDrag(false);
        calendar.setEnableDragCreate(false);
        calendar.setAllowMoveUnselectedItems(false);
        calendar.setDragItemsOnDisabledAreas(false);

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

        JInternalFrame thisFrame = this;
        calendar.addCalendarListener(new CalendarAdapter()
        {
            @Override
            public void itemClick(ItemMouseEvent e) {
                // display the form if item is double-clicked
                if (e.getClicks() != 2)
                    return;

                calendar.resetDrag();

                JInternalFrame frame = new JInternalFrame(e.getItem().getHeaderText());
                Reservation reservation = (Reservation) e.getItem();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setBounds(screenSize.width / 3, screenSize.height / 4, 500, 325);

                frame.setLayout(new BorderLayout());
                JLabel title = new JLabel(reservation.getBoat().getName());
                title.setFont(new Font(title.getFont().getName(), Font.BOLD, 40));
                title.setHorizontalAlignment(JLabel.CENTER);
                frame.add(title, BorderLayout.NORTH);

                JPanel amountAndDeposit = new JPanel(new GridLayout(3, 2));
                JLabel amountTitle = new JLabel("Montant");
                amountTitle.setFont(new Font(amountTitle.getFont().getName(), Font.BOLD, 25));
                amountTitle.setHorizontalAlignment(JLabel.CENTER);
                JLabel depositTitle = new JLabel("Caution");
                depositTitle.setFont(new Font(depositTitle.getFont().getName(), Font.BOLD, 25));
                depositTitle.setHorizontalAlignment(JLabel.CENTER);

                JLabel amountLabel = new JLabel(String.valueOf(reservation.getAmount()));
                amountLabel.setFont(new Font(amountLabel.getFont().getName(), Font.PLAIN, 20));
                amountLabel.setHorizontalAlignment(JLabel.CENTER);
                JLabel depositLabel = new JLabel(String.valueOf(reservation.getDeposit()));
                depositLabel.setFont(new Font(depositLabel.getFont().getName(), Font.PLAIN, 20));
                depositLabel.setHorizontalAlignment(JLabel.CENTER);

                JCheckBox UpToDateCheck = new JCheckBox("Cotisation à jour");
                UpToDateCheck.setSelected(reservation.isUpToDate());
                UpToDateCheck.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reservation.setUpToDate(UpToDateCheck.isSelected());
                        StalledController.setUpToDate(reservation);
                        calendar.getSchedule().getItems().remove(reservation);
                        StalledController.createAppointment(calendar, reservation);
                    }
                });

                amountAndDeposit.add(amountTitle);
                amountAndDeposit.add(depositTitle);
                amountAndDeposit.add(amountLabel);
                amountAndDeposit.add(depositLabel);
                amountAndDeposit.add(UpToDateCheck);

                frame.add(amountAndDeposit, BorderLayout.CENTER);

                JPanel buttonsPanel = new JPanel(new BorderLayout());

                JButton delete = new JButton(new ImageIcon("src/main/resources/delete.png"));
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int answer = JOptionPane.showConfirmDialog(thisFrame, "Voulez-vous vraiment supprimer le bateau sélectionné ?",
                                "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (answer == 0) {
                            StalledController.deleteAppointmentOfDatabase(reservation);
                            calendar.getSchedule().getItems().remove(reservation);
                            calendar.repaint();
                            JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, frame);
                            for (JInternalFrame fr : desktopPane.getAllFrames()) {
                                if (frame == fr)
                                    desktopPane.remove(fr);
                            }
                            SwingUtilities.updateComponentTreeUI(desktopPane);
                        } else {return;}
                    }
                });

                JButton cancel = new JButton(new ImageIcon("src/main/resources/cancel.png"));
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDesktopPane desktopPane = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, frame);
                        for (JInternalFrame fr : desktopPane.getAllFrames()) {
                            if (frame == fr)
                                desktopPane.remove(fr);
                        }
                        SwingUtilities.updateComponentTreeUI(desktopPane);
                    }
                });

                buttonsPanel.add(delete, BorderLayout.WEST);
                buttonsPanel.add(cancel, BorderLayout.EAST);
                frame.add(buttonsPanel, BorderLayout.SOUTH);

                frame.setVisible(true);
                mainPane.add(frame);
                try {
                    frame.setSelected(true);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            }
        });
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.ipady = 10;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        downPanel.add(calendar, constraints);

        fillCalendar();
    }

    private void fillCalendar(){
        try {
            ResultSet attributes = database.SQLSelect("SELECT * FROM Reservation");
            while (attributes.next()){
                StalledController.createAppointment(calendar, new DateTime(attributes.getDate("DateDebut")),
                        new DateTime(attributes.getDate("DateFin")),
                        attributes.getInt("Cale"),
                        new Adherent(attributes.getInt("Adherent")),
                        Colors.fromName(attributes.getString("Couleur")),
                        attributes.getInt("ID"),
                        attributes.getInt("Montant"),
                        attributes.getInt("Caution"),
                        new Boat(attributes.getInt("Bateau")),
                        attributes.getBoolean("CotisationAJour"));
                SwingUtilities.updateComponentTreeUI(this);
            }
        } catch (SQLException e){
            System.out.println("SQL Select exception n° " + e.getErrorCode() + " What goes wrong ?");
            System.out.println(e.getMessage());
        }
    }
}
