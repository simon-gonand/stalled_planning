package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Controller.BoatController;
import com.Cale_Planning.Controller.StalledController;
import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import com.Cale_Planning.Parser;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class AllAdherentsView extends JInternalFrame {
    private JDesktopPane mainPane;
    private JList<Adherent> adherentJList;
    public AllAdherentsView(JDesktopPane mainPane) throws PropertyVetoException {
        super();

        this.mainPane = mainPane;

        setTitle("Les Adhérents");
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new BorderLayout());

        fillAdherentsView();

        JPanel buttonsPanel = new JPanel(new GridLayout(1,4,125,0));
        addButtons(buttonsPanel);
        this.add(buttonsPanel, BorderLayout.PAGE_END);

        int i = this.mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = this.mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView || frame instanceof AllBoatsView) {
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

    private void fillAdherentsView(){
        DefaultListModel<Adherent> allAdherents = AdherentController.getAllAdherent();
        adherentJList = new JList<>(allAdherents);
        adherentJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    try {
                        new AdherentView(adherentJList.getModel().getElementAt(adherentJList.locationToIndex(e.getPoint())), mainPane);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(mainPane);
                }
            }
        });
        this.add(adherentJList, BorderLayout.CENTER);
    }

    private void addButtons(JPanel buttonsPanel){
        buttonsPanel.setBackground(Color.white);

        final JInternalFrame thisFrame = this;
        JButton close = new JButton(new ImageIcon("src/main/resources/cancel.png"));
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

        JButton addAdherent = new JButton(new ImageIcon("src/main/resources/add.png"));
        addAdherent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdherentView(mainPane, adherentJList);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(thisFrame);
            }
        });

        JButton deleteAdherent = new JButton(new ImageIcon("src/main/resources/delete.png"));
        deleteAdherent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Adherent adherent = adherentJList.getSelectedValue();
                if (adherent == null){
                    JOptionPane.showMessageDialog(thisFrame, "Veuillez sélectionner un adhérent à supprimer", "Adhérent non sélecitonné",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int answer = JOptionPane.showConfirmDialog(thisFrame, "Voulez-vous vraiment supprimer l'adhérent sélectionné ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (answer == 0){
                    DefaultListModel<Boat> boats = adherent.getBoats();

                    try {
                        ResultSet resultSet = Main.getDatabase().SQLSelect("SELECT * FROM Reservation");
                        while (resultSet.next()) {
                            if (adherent.getId() == resultSet.getInt("Adherent")) {
                                StalledController.deleteAppointmentOfDatabase(resultSet.getInt("ID"));
                            }
                        }
                    } catch (SQLException ex){
                        System.out.println("Reservation select error n° " + ex.getErrorCode() + "What goes wrong ?");
                        System.out.println(ex.getMessage());
                    }

                    for (int i = 0; i < boats.size(); ++i){
                        Boat boat = boats.get(i);
                        BoatController.deleteBoat(boat);
                    }

                    AdherentController.deleteAdherent(adherent);
                    DefaultListModel defaultListModel = (DefaultListModel) adherentJList.getModel();
                    defaultListModel.removeElement(adherent);
                    adherentJList.setModel(defaultListModel);
                    SwingUtilities.updateComponentTreeUI(thisFrame);
                } else
                    return;
            }
        });

        JButton importFile = new JButton(new ImageIcon("src/main/resources/upload-file.png"));
        importFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(thisFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    Parser parser = new Parser(file.getPath());
                    try {
                        parser.importAdherents(mainPane);
                    } catch (ParserConfigurationException ex) {
                        JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement");
                    } catch (SAXException ex) {
                        JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement");
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement dû au mauvais " +
                                "format d'une date");
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement dû à des valeurs nulles");
                    }
                }
            }
        });

        buttonsPanel.add(addAdherent);
        buttonsPanel.add(deleteAdherent);
        buttonsPanel.add(importFile);
        buttonsPanel.add(close);
    }
}
