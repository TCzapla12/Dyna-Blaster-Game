package com.DynaBlaster.map;

import java.awt.image.BufferedImage;

/**
 * Klasa odpowiadajaca za wszystkie bloki generowane na makiete
 */
public class Block
{
    /**rodzaj bloku, odpowiada za jego wytrzymalosc*/
    private int type;
    /**odpowiada za teksture obiektu*/
    private BufferedImage image;
    /**rodzaj bloku*/
    public static final int EMPTY=0;
    /**rodzaj bloku*/
    public static final int SOLID=1;
    /**rodzaj bloku*/
    public static final int UNSOLID=2;
    /**rodzaj bloku*/
    public static final int HIDDEN=3;
    /**rodzaj bloku*/
    public static final int PORTOL=4;

    public Block(int x, BufferedImage y)
    {
        this.type=x;
        this.image=y;
    }
    public BufferedImage getImage() {return image;}
    public int getType()
    {
        return type;
    }

    /**
     * Metoda okreslajaca czy przez blok mozna sie przemieszczac
     */
    public static boolean isNBlocked(int x)
    {
        if(x==SOLID || x==UNSOLID || x==HIDDEN) return false; else return true;
    }

    /**
     * Metoda okreslajaca czy blok jest zniszczalny
     */
    public static boolean isDestroyable(int x){if(x!=SOLID && x!=PORTOL)return true; else return false;}

}
