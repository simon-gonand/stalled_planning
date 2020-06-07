package com.Cale_Planning;

import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import com.Cale_Planning.View.AdherentView;
import com.Cale_Planning.View.BoatView;
import com.Cale_Planning.View.MainMenuView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class Main {
    private static MSAccessBase database = new MSAccessBase("src/main/resources/Database.accdb");
    public static MSAccessBase getDatabase(){ return database; }

    public static void main(String[] args){
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

        // Main menu frame
        MainMenuView mainMenu = new MainMenuView();
        mainPane.add(mainMenu);
    }
}
