package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Controller.BoatController;
import com.Cale_Planning.Controller.StalledController;
import com.Cale_Planning.Main;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AllBoatsView extends JInternalFrame {
    private JList<Boat> boatJList;
    private DefaultListModel defaultBoatList;
    private JDesktopPane mainPane;
    public AllBoatsView(JDesktopPane mainPane) throws PropertyVetoException {
        super();

        this.mainPane = mainPane;
        setTitle("Les Bateaux");
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new BorderLayout());

        dynamicSearchView();
        fillBoatsView(mainPane);
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        addButtons(buttonsPanel);
        this.add(buttonsPanel, BorderLayout.PAGE_END);

        int i = mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView || frame instanceof AllAdherentsView) {
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

    private void dynamicSearchView() {
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
                    boatJList.setModel(defaultBoatList);
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    for (int i = 0; i < defaultBoatList.getSize(); i++) {
                        if (defaultBoatList.getElementAt(i).toString().toLowerCase().indexOf(enteredText.toLowerCase()) != -1) {
                            model.addElement(defaultBoatList.getElementAt(i));
                        }
                    }
                    boatJList.setModel(model);
                }
                else {
                    for (int i = 0; i < boatJList.getModel().getSize(); i++) {
                        if (boatJList.getModel().getElementAt(i).toString().toLowerCase().indexOf(enteredText.toLowerCase()) != -1) {
                            model.addElement(boatJList.getModel().getElementAt(i));
                        }
                    }
                    boatJList.setModel(model);
                }
            }
        });
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchText, BorderLayout.CENTER);
        this.add(searchPanel, BorderLayout.NORTH);
    }

    private void fillBoatsView(JDesktopPane mainPane) {
        defaultBoatList = BoatController.getAllBoat();
        boatJList = new JList<>(defaultBoatList);
        boatJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    try {
                        new BoatView(boatJList.getModel().getElementAt(boatJList.locationToIndex(e.getPoint())), mainPane);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                    SwingUtilities.updateComponentTreeUI(mainPane);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(boatJList);
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

        JButton addBoat = new JButton(new ImageIcon("src/main/resources/add.png"));
        addBoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new BoatView(mainPane, boatJList);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(thisFrame);
            }
        });

        JButton deleteBoat = new JButton(new ImageIcon("src/main/resources/delete.png"));
        deleteBoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boat boat = boatJList.getSelectedValue();
                if (boat == null){
                    JOptionPane.showMessageDialog(thisFrame, "Veuillez sélectionner un bateau à supprimer", "Bateau non sélecitonné",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int answer = JOptionPane.showConfirmDialog(thisFrame, "Voulez-vous vraiment supprimer le bateau sélectionné ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (answer == 0){
                    try {
                        ResultSet resultSet = Main.getDatabase().SQLSelect("SELECT * FROM Reservation");
                        while (resultSet.next()) {
                            if (boat.getId() == resultSet.getInt("Bateau")) {
                                StalledController.deleteAppointmentOfDatabase(resultSet.getInt("ID"));
                            }
                        }
                    } catch (SQLException ex){
                        System.out.println("Reservation select error n° " + ex.getErrorCode() + "What goes wrong ?");
                        System.out.println(ex.getMessage());
                    }
                    BoatController.deleteBoat(boat);
                    DefaultListModel defaultListModel = (DefaultListModel) boatJList.getModel();
                    defaultListModel.removeElement(boat);
                    boatJList.setModel(defaultListModel);
                    SwingUtilities.updateComponentTreeUI(thisFrame);
                } else
                    return;
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.LINE_START;

        buttonsPanel.add(addBoat, constraints);
        ++constraints.gridx;
        constraints.anchor = GridBagConstraints.CENTER;
        buttonsPanel.add(deleteBoat, constraints);
        ++constraints.gridx;
        constraints.anchor = GridBagConstraints.LINE_END;
        buttonsPanel.add(close, constraints);
    }
}
