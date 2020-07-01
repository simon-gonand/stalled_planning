package com.Cale_Planning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

public class MonthPanel extends JPanel {
    int month;
    int year;
    protected String[] monthNames = { "Janvier", "Février", "Mars", "Avril",
            "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre",
            "Decembre" };

    protected String[] dayNames = { "L", "Ma", "Me", "J", "V", "S", "D" };
    private ArrayList<JPanel> firstStalled;
    private ArrayList<JPanel> secondStalled;
    private ArrayList<JPanel> thirdStalled;
    private ArrayList<JPanel> fourthStalled;
    private ArrayList<JPanel> fifthStalled;
    private ArrayList<JPanel> sixthStalled;

    public MonthPanel(int month, int year) {
        this.month = month;
        this.year = year;

        firstStalled = new ArrayList<>();
        secondStalled = new ArrayList<>();
        thirdStalled = new ArrayList<>();
        fourthStalled = new ArrayList<>();
        fifthStalled = new ArrayList<>();
        sixthStalled = new ArrayList<>();

        JPanel monthPanel = new JPanel(true);
        monthPanel.setLayout(new BorderLayout());
        monthPanel.add(createTitleGUI(), BorderLayout.NORTH);
        monthPanel.add(createDaysGUI(), BorderLayout.SOUTH);
        this.add(monthPanel);
    }
    protected JPanel createTitleGUI() {
        JPanel titlePanel = new JPanel(true);
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(monthNames[month] + " " + year);
        label.setForeground(SystemColor.activeCaption);
        titlePanel.add(label, BorderLayout.CENTER);
        return titlePanel;
    }

    protected JPanel createDaysGUI() {
        JPanel dayPanel = new JPanel(true);
        dayPanel.setLayout(new GridLayout(0, 31));

        Calendar today = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar iterator = (Calendar) calendar.clone();
        iterator.add(Calendar.DAY_OF_MONTH,
                -(iterator.get(Calendar.DAY_OF_WEEK) - 1));

        Calendar maximum = (Calendar) calendar.clone();
        maximum.add(Calendar.MONTH, +1);

        int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < 31; i++) {
            JPanel dPanel = new JPanel(true);
            dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (dayOfTheWeek > 6)
                dayOfTheWeek = 0;
            JLabel dLabel = new JLabel(dayNames[dayOfTheWeek]);
            ++dayOfTheWeek;
            dPanel.add(dLabel);
            dayPanel.add(dPanel);
        }

        int count = 0;

        while (iterator.getTimeInMillis() < maximum.getTimeInMillis()) {
            int lMonth = iterator.get(Calendar.MONTH);
            int lYear = iterator.get(Calendar.YEAR);

            JPanel dPanel = new JPanel(true);
            dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel dayLabel = new JLabel();

            if ((lMonth == month) && (lYear == year)) {
                int lDay = iterator.get(Calendar.DAY_OF_MONTH);
                dayLabel.setText(Integer.toString(lDay));
                dPanel.add(dayLabel);
                dayPanel.add(dPanel);
            }

            iterator.add(Calendar.DAY_OF_YEAR, +1);
            count++;
        }

        for (int i = count; i < 7 * 31 + (count - 31); i++) {
            JPanel dPanel = new JPanel(true);
            dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dPanel.add(new JLabel());

            if (i > count && i < 2 * 31 + (count - 31))
                firstStalled.add(dPanel);
            else if (i > 2 * 31 + (count - 31) && i > 3 * 31 + (count - 31))
                secondStalled.add(dPanel);
            else if (i > 3 * 31 + (count - 31) && i > 4 * 31 + (count - 31))
                thirdStalled.add(dPanel);
            else if (i > 4 * 31 + (count - 31) && i > 5 * 31 + (count - 31))
                fourthStalled.add(dPanel);
            else if (i > 5 * 31 + (count - 31) && i > 6 * 31 + (count - 31))
                fifthStalled.add(dPanel);
            else if (i > 6 * 31 + (count - 31))
                sixthStalled.add(dPanel);

            dayPanel.add(dPanel);
        }
        return dayPanel;
    }
}