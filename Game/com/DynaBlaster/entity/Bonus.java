package com.DynaBlaster.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
/**
 * Klasa opisujaca mozliwe do zdobycia przedmioty podczas przygody
 */
public class Bonus extends Entity{
    /**okresla typ bonusu*/
    private int type;
    /**okresla liczbe punktow za poszczegolny typ bonusu*/
    private static int[] points = new int[3];
    public Bonus(String path) throws IOException
    {
        File imagefile = new File(path);
        image = ImageIO.read(imagefile);
    }
    public Bonus(int x, int y, int type) throws IOException
    {
        this.x=x;
        this.y=y;
        this.type=type;
        if(type==0)
        image = ImageIO.read(getClass().getResource("/resources/images/health.png"));
        else if(type==1)
            image = ImageIO.read(getClass().getResource("/resources/images/range.png"));
        else
            image = ImageIO.read(getClass().getResource("/resources/images/infinite.png"));
    }

    /**
     * Metoda sluzaca do rysowania obiektu na ekran
     */
    public void draw(Graphics2D graph)
    {
        graph.drawImage(image,x,y, null);
    }
    /**
     * Metoda sluzaca do ustawienia pozycji obiektu
     */
    public void setPosition(int x,int y){this.x=x;this.y=y;}
    public int getType(){return type;}
    public static int getPoints(int i)
    {
        return points[i];
    }
    public static void setPoints(int newPoints, int i)
    {
        points[i]=newPoints;
    }
}
