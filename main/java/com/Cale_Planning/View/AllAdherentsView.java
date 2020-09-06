package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class AllAdherentsView extends JInternalFrame {
    private JDesktopPane mainPane;
    public AllAdherentsView(JDesktopPane mainPane) throws PropertyVetoException {
        super();

        this.mainPane = mainPane;

        setTitle("Les Adhérents");
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new BorderLayout());

        fillAdherentsView();

        JPanel buttonsPanel = new JPanel(new BorderLayout());
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
        Adherent[] allAdherents = AdherentController.getAllAdherent();
        JList<Adherent> adherentJList = new JList<>(allAdherents);
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
        buttonsPanel.add(close, BorderLayout.EAST);

        JButton addAdherent = new JButton(new ImageIcon("src/main/resources/addAdherent.png"));
        addAdherent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdherentView(mainPane);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(mainPane);
            }
        });
        buttonsPanel.add(addAdherent, BorderLayout.WEST);
    }
}
