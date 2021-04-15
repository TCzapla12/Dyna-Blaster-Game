package com.DynaBlaster.entity;

import com.DynaBlaster.map.Level;

import java.awt.image.BufferedImage;

/**
 * Klasa opisujace podstawowe byty ktore beda sie wyswietlac na makiecie
 */
public abstract class Entity {
    /**dane o budowie poziomu*/
    Level level;
    /**wspolrzedne okiektu */
    protected int x;
    /**wspolrzedne obiektu */
    protected int y;
    /**okresla kierunek poruszania sie obiektu*/
    protected boolean left;
    /**okresla kierunek poruszania sie obiektu*/
    protected boolean right;
    /**okresla kierunek poruszania sie obiektu*/
    protected boolean up;
    /**okresla kierunek poruszania sie obiektu*/
    protected boolean down;
    /** zapisuje teksture obiektu */
    protected BufferedImage image;

    /**
     * Metoda odpowiadajaca za system kolizji obiektow ruchomych z blokami z planszy
     */
    public void changePosition(String key)
    {
        if (key == "left" && x>level.getResolution() && level.isNBlocked(x,y+2) && level.isNBlocked(x,y+13)) x-=2;
        else if (key =="right"&&x<level.getResolution()*level.getLength() && level.isNBlocked(x+15,y+2) && level.isNBlocked(x+15,y+13))x+=2;
        else if(key =="up"&& y>level.getResolution() && level.isNBlocked(x+2,y) && level.isNBlocked(x+13,y))y-=2;
        else if(key =="down"&&y<level.getResolution()*level.getWidth() && level.isNBlocked(x+2,y+15) && level.isNBlocked(x+13,y+15))y+=2;
    }

    public int getX(){return x;}
    public int getY(){return y;}
}
