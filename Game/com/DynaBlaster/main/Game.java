package com.DynaBlaster.main;

import javax.swing.*;
import java.io.*;


/**
 * Glowna klasa - odpowiada za uruchamianie sie gry/programu
 */
public class Game {
    public static void main(String args[]) throws IOException
    {
        Menu menu = new Menu();
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}