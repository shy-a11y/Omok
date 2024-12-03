package client.ui;

import client.listener.ThemeListener;

import javax.swing.*;
import java.awt.*;

public class ThemePanel extends JPanel {

    public ThemePanel() {
        // Basic setup for the ThemePanel
        this.setLayout(new BorderLayout());

        // Create a label and some buttons for the theme selection
        JLabel label = new JLabel("테마를 선택하세요", JLabel.CENTER);
        JButton darkThemeButton = new JButton("기본 테마");
        JButton lightThemeButton = new JButton("커스텀 테마");

        // Add action listeners to the buttons (use ThemeListener)
        darkThemeButton.addActionListener(new ThemeListener());
        lightThemeButton.addActionListener(new ThemeListener());

        // Add components to the panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(darkThemeButton);
        buttonPanel.add(lightThemeButton);

        this.add(label, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
    }
}
