package com.DynaBlaster.entity;

import com.DynaBlaster.map.Level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Klasa opsiujaca bomby - elementy sluzace do niszczenia blokow
 */
public class Bomb extends Entity {
    /**klatki animacji bomby*/
    BufferedImage[] explosion;
    /**czas odpowiedzialny za wyswietlanie odpowiednich klatek animacji*/
    int time;
    /**zasieg dzialania bomb*/
    int bombrange;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean leftDestroy=false;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean leftDestroy2=false;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean rightDestroy=false;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean rightDestroy2=false;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean upDestroy=false;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean upDestroy2=false;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean downDestroy=false;
    /**odpowiada za niszczenie blokow znajdujacych sie dookola bomby*/
    boolean downDestroy2=false;
    public Bomb() throws IOException
    {
        image = ImageIO.read(getClass().getResource("/resources/images/bomb.png"));
    }
    public Bomb(int x, int y, Level l, int range) throws IOException
    {
        bombrange=range;
        level = l;
        image = ImageIO.read(getClass().getResource("/resources/images/bomb.png"));
        explosion = new BufferedImage[4];
        explosion[0] = ImageIO.read(getClass().getResource("/resources/images/bomb2.png"));
        explosion[1] = ImageIO.read(getClass().getResource("/resources/images/bomb3.png"));
        explosion[2] = ImageIO.read(getClass().getResource("/resources/images/bomb4.png"));
        explosion[3] = ImageIO.read(getClass().getResource("/resources/images/fire.png"));
        this.x=x;
        this.y=y;
        destroyable();
    }

    /**
     * Metoda sprawdzajaca ktore bloki powinny byc zniszczone po wybuchu bomby
     */
    public void destroyable()
    {
        if(x>level.getResolution() && level.isDestroyable(x-2,y) && level.isDestroyable(x-2,y+15)) leftDestroy=true;;
        if(x<level.getResolution()*level.getLength() && level.isDestroyable(x+17,y) && level.isDestroyable(x+17,y+15))rightDestroy=true;
        if(y>level.getResolution() && level.isDestroyable(x,y-2) && level.isDestroyable(x+15,y-2))upDestroy=true;
        if(y<level.getResolution()*level.getWidth() && level.isDestroyable(x,y+17) && level.isDestroyable(x+15,y+17))downDestroy=true;
        if(bombrange==2)
        {
            if(leftDestroy==true && x-16>level.getResolution() && level.isDestroyable(x-18,y) && level.isDestroyable(x-18,y+15))leftDestroy2=true;
            if(rightDestroy==true && x+16<level.getResolution()*level.getLength() && level.isDestroyable(x+33,y) && level.isDestroyable(x+33,y+15))rightDestroy2=true;
            if(upDestroy==true && y-16>level.getResolution() && level.isDestroyable(x,y-18) && level.isDestroyable(x+15,y-18))upDestroy2=true;
            if(downDestroy==true && y+16<level.getResolution()*level.getWidth() && level.isDestroyable(x,y+33) && level.isDestroyable(x+15,y+33))downDestroy2=true;
        }
    }
    public boolean getLeftDestroyable(){return leftDestroy;}
    public boolean getRightDestroyable(){return rightDestroy;}
    public boolean getUpDestroyable(){return upDestroy;}
    public boolean getDownDestroyable(){return downDestroy;}
    public boolean getLeftDestroyable2(){return leftDestroy2;}
    public boolean getRightDestroyable2(){return rightDestroy2;}
    public boolean getUpDestroyable2(){return upDestroy2;}
    public boolean getDownDestroyable2(){return downDestroy2;}
    /**
     * Metoda sluzaca do rysowania obiektu na ekran
     */
    public void draw(Graphics2D graph)
    {
        if(time>120)
        {
            image = explosion[3];
             if(leftDestroy==true)
                 graph.drawImage(image,x-16,y, null);
             if(rightDestroy==true)
                 graph.drawImage(image,x+16,y, null);
             if(upDestroy==true)
                 graph.drawImage(image,x,y-16, null);
             if(downDestroy==true)
                 graph.drawImage(image,x,y+16, null);
             if(bombrange==2)
             {
                 if(leftDestroy2==true)
                     graph.drawImage(image,x-32,y, null);
                 if(rightDestroy2==true)
                     graph.drawImage(image,x+32,y, null);
                 if(upDestroy2==true)
                     graph.drawImage(image,x,y-32, null);
                 if(downDestroy2==true)
                     graph.drawImage(image,x,y+32, null);
             }

        }

        else if (time > 90)
            image = explosion[2];
        else if (time> 60)
            image = explosion[1];
        else if (time> 30)
            image = explosion[0];
        graph.drawImage(image,x,y, null);
    }
    /**
     * Metoda sluzaca do ustawienia pozycji obiektu
     */
    public void setPosition(int x,int y){this.x=x;this.y=y;}
    public void addTime(){time++;}
    public int getTime(){return time;}
}
