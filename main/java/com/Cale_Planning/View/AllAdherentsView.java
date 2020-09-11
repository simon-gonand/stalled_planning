package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Controller.BoatController;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.List;

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

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
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

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.LINE_START;

        buttonsPanel.add(addAdherent, constraints);
        ++constraints.gridx;
        constraints.anchor = GridBagConstraints.CENTER;
        buttonsPanel.add(deleteAdherent, constraints);
        ++constraints.gridx;
        constraints.anchor = GridBagConstraints.LINE_END;
        buttonsPanel.add(close, constraints);
    }
}
