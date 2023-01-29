package com.Cale_Planning;

import com.Cale_Planning.View.IMainFrame;
import com.Cale_Planning.View.MainMenuView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    private static MSAccessBase database = new MSAccessBase("main/resources/Database.accdb");
    public static MSAccessBase getDatabase(){ return database; }
    public static ArrayList<JInternalFrame> windowManagment = new ArrayList<JInternalFrame>();

    public static void RefreshAllFrames(){
        for (JInternalFrame frame: windowManagment) {
            for(Class i : frame.getClass().getInterfaces()){
                if(i.equals(IMainFrame.class))
                    ((IMainFrame)frame).RefreshFrame();
            }
        }
    }

    public static void main(String[] args){
        JFrame mainWindow = new JFrame("Planning Cale");
        ImageIcon icon = new ImageIcon("main/resources/Main_Icon.png");
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
