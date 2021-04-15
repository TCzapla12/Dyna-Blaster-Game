package com.DynaBlaster.main;

import com.DynaBlaster.game.Config;
import com.DynaBlaster.game.Play;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Klasa odpowiadajaca za poprawne rysowanie okna gry
 */

public class GameWindow extends JPanel implements KeyListener, ActionListener
{
    public GameWindow()  {

        play = new Play();
        try {
            image= new BufferedImage(Config.getParameters("resources/config/level.txt",0)*16+32,Config.getParameters("resources/config/level.txt",1)*16+32,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g=(Graphics2D) image.getGraphics();

        try {
            play.startGame("resources/config/level.txt","resources/config/config.txt",g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g2= (Graphics) g;
        this.setVisible(true);
    }
    public GameWindow(String path)   {
        play = new Play();
        try {
            image= new BufferedImage(Config.getParameters("resources/config/level.txt",0)*16+32,Config.getParameters("resources/config/level.txt",1)*16+32,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g=(Graphics2D) image.getGraphics();
        try {
            play.startGame("resources/config/level.txt","resources/config/config.txt",g, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g2= (Graphics) g;
        this.setVisible(true);
    }
    private Graphics2D g;
    Play play;
    private BufferedImage image;

    /**
     * Metoda odpowiadajaca za wyswietlanie obrazu gry
     */
    public void paintComponent(Graphics tmp)
    {
        tmp.drawImage(image, 0,0,getWidth(),getHeight(),null );
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    /**
     * Metoda odpowiadajaca za sterowanie
     */
    public void keyPressed(KeyEvent e)
    {
        try {
            play.keyPressed(e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Metoda odpowiadajaca za sterowanie zdarzeniami
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {

        try {
            play.Collision();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        g=(Graphics2D) image.getGraphics();
        try {
            play.repaint(g);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       repaint();

    }

    /**
     * Metoda wstrzymujaca gre
     */
    public void pause()
    {
        play.pause();
    }

    /**
     * Metoda wznawiajaca gre
     */
    public void resume()
    {
        play.resume();
    }

    /**
     * Metoda zapisujaca gre do pliku
     */
    public void save(String path)
    {
        try {
            play.saveSave(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isDrawPause(){return play.isDrawPause();}
    public boolean isFinished()
    {
        return play.isFinished();
    }
}
