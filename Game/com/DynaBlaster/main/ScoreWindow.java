package com.DynaBlaster.main;

import com.DynaBlaster.game.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Klasa wyswietlajaca wynik gracza po ukonczeniu gry
 */

public class ScoreWindow implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel messageLabel;
    private JLabel messageLabel2;
    private JButton okButton;
    private JTextField nick;
    private ImageIcon stars;
    private JLabel label;
    /**okresla liczbe punktow zdobyta przez gracza*/
    public int points;

    public ScoreWindow(String message) {
        frame = new JFrame("Game Over");
        panel = new JPanel();
        nick = new JTextField(15);
        messageLabel = new JLabel(message);
        messageLabel.setFont(new Font(messageLabel.getName(), Font.PLAIN, 80));
        messageLabel2 = new JLabel("Your nickname:");
        messageLabel2.setFont(new Font(messageLabel2.getName(), Font.PLAIN, 30));
        okButton = new JButton("Ok");
        okButton.setSize(100,50);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(messageLabel, c);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        points = Integer.valueOf(message);
        if(points<=100000) {
            stars = new ImageIcon(getClass().getResource("/resources/images/gwiazdka.png"));
            label = new JLabel(stars);
        }
        else if(points<=150000)
        {stars = new ImageIcon(getClass().getResource("/resources/images/gwiazdka2.png"));
            label = new JLabel(stars);}
        else if(points>=150000)
        {stars = new ImageIcon(getClass().getResource("/resources/images/gwiazdka3.png"));
            label = new JLabel(stars);}
        panel.add(label,c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(messageLabel2, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel.add(nick,c);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(okButton, c);


        okButton.addActionListener(this);
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
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == okButton) {
            SwingUtilities.invokeLater(() -> frame.dispose());
            String x = nick.getText();
            List.getScore(x,points);
            try {
                Config.saveResults("resources/config/results.txt" ,x ,points);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
