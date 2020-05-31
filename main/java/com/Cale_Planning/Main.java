package com.Cale_Planning;

import com.Cale_Planning.Models.Adherent;
import com.Cale_Planning.Models.Boat;
import com.Cale_Planning.View.AdherentView;
import com.Cale_Planning.View.BoatView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class Main {
    private static MSAccessBase database = new MSAccessBase("src/main/resources/Database.accdb");
    public static MSAccessBase getDatabase(){ return database; }

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


        AdherentView view = new AdherentView(new Adherent(100000));
        view.setBounds(screenSize.width/3, screenSize.height /4, 650, 525);

        BoatView viewBoat = new BoatView(new Boat(200000));
        viewBoat.setBounds(view.getX() + 20, view.getY() + 20, 650, 525);

        mainPane.add(view);
        mainPane.add(viewBoat);
        try {
            viewBoat.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
