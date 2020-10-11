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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class AllAdherentsView extends JInternalFrame {
    private JDesktopPane mainPane;
    private JList<Adherent> adherentJList;
    private DefaultListModel defaultAdherentList;
    public AllAdherentsView(JDesktopPane mainPane) throws PropertyVetoException {
        super();

        this.mainPane = mainPane;

        setTitle("Les Adhérents");
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new BorderLayout());

        dynamicSearchView();
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

    private void dynamicSearchView(){
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
        this.add(searchPanel, BorderLayout.NORTH);
    }

    private void fillAdherentsView(){
        defaultAdherentList = AdherentController.getAllAdherent();
        adherentJList = new JList<>(defaultAdherentList);
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
        adherentJList.setFont(new Font(adherentJList.getFont().getName(), Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(adherentJList);
        scrollPane.setBackground(Color.white);
        this.add(scrollPane, BorderLayout.CENTER);
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
                final JDialog d = new JDialog();
                JPanel p1 = new JPanel(new GridLayout(2,1));
                JLabel firstLine = new JLabel("Importation des adhérents en cours...");
                JLabel secondLine = new JLabel("Veuillez patienter");
                firstLine.setHorizontalAlignment(JLabel.CENTER);
                secondLine.setHorizontalAlignment(JLabel.CENTER);
                p1.add(firstLine);
                p1.add(secondLine);
                d.getContentPane().add(p1);
                d.setSize(300,200);
                d.setLocationRelativeTo(thisFrame);
                d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                d.setModal(true);
                Thread t = new Thread(){
                    public void run(){
                        JFileChooser fileChooser = new JFileChooser();
                        int result = fileChooser.showOpenDialog(thisFrame);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File file = fileChooser.getSelectedFile();
                            Parser parser = new Parser(file.getPath());
                            try {
                                parser.importAdherents(mainPane);
                            } catch (ParserConfigurationException ex) {
                                JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement");
                                d.dispose();
                                interrupt();
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement");
                                d.dispose();
                                interrupt();
                            } catch (SAXException ex) {
                                JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement");
                                d.dispose();
                                interrupt();
                            } catch (ParseException ex) {
                                JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement dû au mauvais " +
                                        "format d'une date");
                                d.dispose();
                                interrupt();
                            } catch (NullPointerException ex) {
                                JOptionPane.showMessageDialog(thisFrame, "Le fichier n'a pas été importé correctement dû à des valeurs nulles");
                                d.dispose();
                                interrupt();
                            }
                            JOptionPane.showMessageDialog(thisFrame, "Les adhérents ont été importés correctement", "Succès",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        d.dispose();
                        interrupt();
                    }
                };
                t.start();
                d.setVisible(true);
            }
        });

        buttonsPanel.add(addAdherent);
        buttonsPanel.add(deleteAdherent);
        buttonsPanel.add(importFile);
        buttonsPanel.add(close);
    }
}
