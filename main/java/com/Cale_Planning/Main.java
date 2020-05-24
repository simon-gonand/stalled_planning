package com.Cale_Planning;

import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.View.AdherentView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame mainWindow = new JFrame("Planning Cale");
        ImageIcon icon = new ImageIcon("src/main/resources/Main_Icon.png");
        mainWindow.setIconImage(icon.getImage());
        mainWindow.setVisible(true);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set fullscreen window
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JDesktopPane mainPane = new JDesktopPane(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(239,239,239));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        mainWindow.setContentPane(mainPane);
        MSAccessBase database = new MSAccessBase("src/main/resources/Database.accdb");
        AdherentView view = new AdherentView(new Adherent(100000, database));
        view.setBounds(screenSize.width/3, screenSize.height /4, 550, 425);
        mainPane.add(view);
    }
}
