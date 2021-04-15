package com.DynaBlaster.entity;

import com.DynaBlaster.map.Level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Klasa opisujaca przeciwnikow
 */
public abstract class Enemy extends Entity
{
    public Enemy(){}
    public Enemy(String path) throws IOException
    {
        File imagefile = new File(path);
        image = ImageIO.read(imagefile);
        left=false;
        right=true;
    }

    /**
     * Metoda odpowiadajaca za wyswietlanie przeciwnikow na ekranie
     */
    public void draw(Graphics2D graph)
    {
        graph.drawImage(image,x,y, null);
    }
    /**
     * Metoda sluzaca do ustawienia pozycji obiektu
     */
    public void setPosition(int x,int y){this.x=x;this.y=y;}
    /**
     * Metoda okreslajaca poruszanie sie obiektu klasy Enemy
     */
    public abstract void moving();
    public abstract int getPoints();
}
