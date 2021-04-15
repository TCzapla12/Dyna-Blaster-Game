package com.DynaBlaster.main;

import com.DynaBlaster.game.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * Klasa odpowiadajaca za wyglad menu oraz jego funkcjonalnosc
 */

public class Menu extends JFrame implements ActionListener
{
    Socket serverSocket;
    public static void setOnline(boolean set){online=set;}
    public static boolean isOnline(){return online;}
    public static String getoConfig(){return oConfig;}
    public static String getoLevel(){return oLevel;}
    public static String getoResults(){return oResults;}
    public static String getoSave(){return oSave;}
	public static void setSave(String set){oSave=set;}
    static boolean online=false;
    static String oConfig, oLevel, oResults, oSave;
    JButton buttonStart, buttonContinue, buttonHelp, buttonRanking, buttonExit;
    Window window;
    GridBagConstraints gbc = new GridBagConstraints();

    public Menu() throws IOException {
        setTitle("Dyna Blaster - Menu");
        setSize(800, 600);
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(0, 0, 70, 0);
        gbc.gridy = 1;
        BufferedImage myPicture = ImageIO.read(getClass().getResource("/resources/images/dyniaplaster.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        add(picLabel, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.gridy = 2;
        buttonStart = new JButton("NEW GAME");
        add(buttonStart, gbc);
        buttonStart.addActionListener(this);
        gbc.gridy = 3;
        buttonContinue = new JButton("CONTINUE");
        add(buttonContinue, gbc);
        buttonContinue.addActionListener(this);
        gbc.gridy = 4;
        buttonHelp = new JButton("HELP");
        add(buttonHelp, gbc);
        buttonHelp.addActionListener(this);
        gbc.gridy = 5;
        buttonRanking = new JButton("RANKING");
        add(buttonRanking, gbc);
        buttonRanking.addActionListener(this);
        gbc.gridy = 6;
        buttonExit = new JButton("EXIT");
        add(buttonExit, gbc);
        buttonExit.addActionListener(this);
        try {
            serverSocket = new Socket("localhost", 12129);
            online=true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO CONNECTION");online=false;
        }
        if(online==true)
            downloadData(serverSocket);
        Config.loadResults("resources/config/results.txt");


    }

    public void downloadData(Socket serverSocket) throws IOException {
            OutputStream os = serverSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_CONFIG");
            InputStream is = serverSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            oConfig = br.readLine();
            pw.println("GET_LEVELS");
            is = serverSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            oLevel = br.readLine();
            pw.println("GET_RESULTS");
            is = serverSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            oResults = br.readLine();
            pw.println("GET_SAVE");
            is = serverSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            oSave = br.readLine();
            serverSocket.close();

    }



    /**
     * Funkcja zarzadzqajaca zdarzeniami
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == buttonStart)
        {
            window = new Window();
        }
            else if(source == buttonContinue)
        {
            window = new Window("resources/config/save.txt");
        }
        else if(source == buttonHelp)
        {
            JOptionPane.showMessageDialog(null,"             GAME INSTRUCTION\n" +
                    "Main target of the game is to complete\nall levels by destroying wooden crates.\n" +
                    "There is portal to the next level, hidden\nbehind one of the crates\n\nUse arrow keys to move and Space to\n" +
                    "set the bomb.\n\nPoints are awarded for:\n -time\n -killing monsters\n -finding bonuses");
        }
        else if(source == buttonRanking)
        {
            new Ranking("Ranking");
        }
            else if(source == buttonExit)
        {
            dispose();
        }
    }


}