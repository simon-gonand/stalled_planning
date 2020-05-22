package com.Cale_Planning;

import com.Cale_Planning.Models.Adherent;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame mainWindow = new JFrame("Planning Cale");
        ImageIcon icon = new ImageIcon("src/main/resources/Main_Icon.png");
        mainWindow.setIconImage(icon.getImage());
        mainWindow.setVisible(true);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set fullscreen window
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
