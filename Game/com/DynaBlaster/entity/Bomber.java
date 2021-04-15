package com.DynaBlaster.entity;



import com.DynaBlaster.map.Level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * Klasa opsiujaca bombera - postac, ktora sterujemy
 */
public class Bomber extends Entity
{
    /**okresla kierunek w ktory zwrocony jest Bomber*/
    int dim=2;
    /**okresla czy Bomber jest widoczny na planszy*/
    boolean visible=true;
    /**okresla smiertlenosc Bombera*/
    boolean immortal=false;
    /**klatki animacji ruchu Bombera*/
    private BufferedImage[] sprites;
    public Bomber() throws IOException
    {
        image = ImageIO.read(getClass().getResource("/resources/images/bomber.png"));
        sprites=new BufferedImage[2];
        sprites[0]=ImageIO.read(getClass().getResource("/resources/images/bomberL.png"));
        sprites[1]=ImageIO.read(getClass().getResource("/resources/images/bomberR.png"));
    }
    public Bomber(Level l) throws IOException
    {
        image = ImageIO.read(getClass().getResource("/resources/images/bomber.png"));
        sprites=new BufferedImage[4];
        sprites[0]=ImageIO.read(getClass().getResource("/resources/images/bomberL.png"));
        sprites[1]=ImageIO.read(getClass().getResource("/resources/images/bomberR.png"));
        sprites[2]=ImageIO.read(getClass().getResource("/resources/images/bomberkill.png"));
        sprites[3]=ImageIO.read(getClass().getResource("/resources/images/bomberimmortal.png"));
        level=l;
    }
    /**
     * Metoda sluzaca do rysowania obiektu na ekran
     */
    public void draw(Graphics2D graph)
    {
        if(immortal==false)
        {
            if(dim==0)
                graph.drawImage(sprites[0],x,y, null);
            else if(dim==1)
                graph.drawImage(sprites[1],x,y, null);
            else
                graph.drawImage(image,x,y, null);
        }
        else
            graph.drawImage(sprites[3],x,y,null);

    }
    /**
 * Metoda sluzaca do rysowania obiektu na ekran po utracie zycia przez Bombera
 */
    public void draw2(Graphics2D graph)
    {
        if(immortal==true)
        {
            graph.drawImage(sprites[3],x,y,null);
        }
        else if(visible==true)
        {
            graph.drawImage(sprites[2],x,y,null);
            visible=false;
        }
        else
            visible=true;
    }

    /**
     * Metoda sluzaca do ustawienia pozycji obiektu
     */
    public void setPosition(int x,int y){this.x=x;this.y=y;}

    /**
     * Metoda obslugujaca zmiane pozycji Bombera na mapie
     */
    public void changePosition(String key)
    {
        super.changePosition(key);

    }

    /**
     * Metoda obslugujaca poruszanie sie Bombera
     */
    public void keyPressed(KeyEvent e)
    {
        int c=e.getKeyCode();
        if(c==e.VK_LEFT) {changePosition("left");dim=0;}
        if(c==e.VK_RIGHT) {changePosition("right");dim=1;}
        if(c==e.VK_UP) {changePosition("up");dim=2;}
        if(c==e.VK_DOWN) {changePosition("down");dim=2;}
    }

    /**
     * Metoda sprawdzajaca czy Bomber znajduje sie w portalu
     */
    public boolean isPortol()
    {
        if (level.isPortol((x+8)/16*16, (y+8)/16*16))
            return true;
        return false;
    }

    /**
     * Metoda dajaca Bomberowi niesmiertelnosc
     */
    public void setImmortality(boolean isActivated)
    {
        immortal=isActivated;
    }
}
