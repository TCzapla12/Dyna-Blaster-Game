package com.DynaBlaster.game;

import com.DynaBlaster.main.List;
import com.DynaBlaster.main.Menu;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Interfejs do wczytywania plikow konfiguracyjnych
 */
public interface Config
{
    /**
     * Metoda wczytujaca schemat poziomu do gry
     */
    void loadLevel(String path) throws IOException;
    /**
     * Metoda wczytujaca zasady gry (liczba zyc, wspolczynniki punktowe...)
     */
    void loadStats(String path) throws IOException;
    /**
     * Metoda wczytujaca zapis gry
     */
    void loadSave(String path) throws IOException;
    /**
     * Metoda zapisujaca gre
     */
    void saveSave(String path) throws IOException;
    /**
     * Metoda wczytujaca liste najlepszych wynikow
     */
    static void loadResults(String path) throws IOException
    {
        if(Menu.isOnline()==true)
        {
            String tmp = Menu.getoResults();
            String[] element = tmp.split("#");
            for(int i=0;i<element.length;i++)
            {
                String[] element2 = element[i].split("@");
                List.getScore(element2[0], Integer.valueOf(element2[1]));
            }
        }
        else
        {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            for(String line=br.readLine();line != null; line=br.readLine())
            {
                String[] element = line.split("@");
                List.getScore(element[0], Integer.valueOf(element[1]));
            }
        }

    }
    /**
     * Metoda zapisujaca liste najlepszych wynikow
     */
    static void saveResults(String path, String x, int points) throws IOException
    {
        if(Menu.isOnline()==true)
        {
            try {
                Socket serverSocket = new Socket("localhost", 12129);
                OutputStream os = serverSocket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("GAME_SCORE:"+x+"@"+points);
                serverSocket.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "NO CONNECTION");Menu.setOnline(false);}

        }
        if(Menu.isOnline()==false)
        {
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            for(int i=0;i<List.getSize();i++)
            {
                String element = List.getString(i)+"\n";
                br.write(element);
            }
            br.close();
        }

    }

    /**
     * Metoda pobierajaca podstawowe parametry potrzebne do rysowania gry
     */

    static int getParameters(String path, int i) throws IOException {
        int out[] = new int[2];
        if (Menu.isOnline()== true)
        {
            String tmp = Menu.getoLevel();
            String[] element = tmp.split("#");
            String[] element2 = element[1].split(" ");
            out[0] = Integer.parseInt(element2[0]);
            out[1] = Integer.parseInt(element2[1]);
        }
        else
        {

            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            line = br.readLine();
            String[] element = line.split(" ");
            out[0] = Integer.parseInt(element[0]);
            out[1] = Integer.parseInt(element[1]);
            is.close();
        }

        return out[i];
    }
}
