package com.Cale_Planning.View;

import com.Cale_Planning.Controller.AdherentController;
import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class AllAdherentsView extends JInternalFrame {
    public AllAdherentsView(JDesktopPane mainPane) throws PropertyVetoException {
        super();

        setTitle("Les Adhérents");
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new GridLayout(1,1));

        fillAdherentsView(mainPane);

        int i = mainPane.getAllFrames().length -1;
        while (i >= 0) {
            JInternalFrame frame = mainPane.getAllFrames()[i];
            if (frame instanceof AdherentView || frame instanceof BoatView) {
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

    private void fillAdherentsView(JDesktopPane mainPane){
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

        this.add(adherentJList);
    }
}
