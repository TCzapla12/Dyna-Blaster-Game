package com.DynaBlaster.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa wyswietlajaca obraz gry
 */
public class Window extends JFrame  implements KeyListener, ActionListener {
    /**obraz planszy*/
    GameWindow gw;
    /**licznik do obslugi zdarzen*/
    Timer tm;
    public Window()  {
        tm= new Timer(20,this);
        tm.start();
        gw = new GameWindow();
        this.setSize(1215,915);
        this.getContentPane().add(gw);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

    }
    public Window(String path)  {
        tm= new Timer(20,this);
        tm.start();
        gw = new GameWindow(path);
        this.setSize(1215,915);
        this.getContentPane().add(gw);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metoda okreslajaca sterowanie gry
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
       int c=e.getKeyCode();
        if(gw.isFinished())
        {
            tm.stop();
            this.dispose();

        }
        else if(c==e.VK_ESCAPE&&!gw.isDrawPause())
        {
            gw.pause();
        }
        else if(gw.isDrawPause())
        {
            if(c==e.VK_R||c==e.VK_ESCAPE)gw.resume();
            else if(c==e.VK_S)
            {
                gw.save("resources/config/save.txt");
                tm.stop();
                this.dispose();
            }
            else if(c==e.VK_Q)
            {
                tm.stop();
                this.dispose();
            }

        }
        else
        gw.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Metoda odpowiadajaca za wykonywanie sie akcji
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        gw.actionPerformed(e);
    }
}


