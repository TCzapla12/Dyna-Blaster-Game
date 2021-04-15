package com.DynaBlaster.main;

import javax.swing.*;
import javax.swing.JTable;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasa odpowiadajaca za wyswietlanie list rankingowych
 */
public class Ranking implements ActionListener{
    private JFrame frame;
    private JPanel panel;
    private JButton buttonBack;
    private JLabel messageLabel;
    private JTable table;

    public Ranking(String ranking)
    {
        frame = new JFrame(ranking);
        panel = new JPanel();
        messageLabel = new JLabel("RANKING");
        messageLabel.setFont(new Font(messageLabel.getName(), Font.PLAIN, 80));
        buttonBack = new JButton("Back");
        buttonBack.setSize(100,50);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 400);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(messageLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill=GridBagConstraints.VERTICAL;
        String[] columnNames = {"Number", "Nick", "Score"};
        table = new JTable(List.getData(), columnNames);

        panel.add(table,c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(buttonBack,c);
        buttonBack.addActionListener(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(() -> frame.setVisible(false));
            }
        });
    }
    /**
     * Metoda odpowidajaca za zachowanie sie okna po wykonaniu interakcji
     */
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == buttonBack) {
            SwingUtilities.invokeLater(() -> frame.dispose());
        }
    }
}
