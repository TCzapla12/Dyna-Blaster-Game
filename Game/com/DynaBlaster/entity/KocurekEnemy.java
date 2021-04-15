package com.DynaBlaster.entity;

import com.DynaBlaster.map.Level;

import javax.imageio.ImageIO;
import java.io.IOException;
/**
 * Jeden z przeciwnikow dostepnych w grze
 */
public class KocurekEnemy extends Enemy
{
    /**okresla liczbe punktow za zabicie potwora*/
    private static int points;
    public KocurekEnemy(int x, int y, Level l) throws IOException
    {
        image = ImageIO.read(getClass().getResource("/resources/images/kocurek.png"));
        this.x=x;
        this.y=y;
        up=false;
        down=true;
        level=l;
    }
    /**
     *  Metoda odpowiadajaca za przemieszczanie sie
     */
    public void moving()
    {
        if(down==true)
        {
            if (y<level.getResolution()*level.getWidth() && level.isNBlocked(x,y+16) && level.isNBlocked(x+15,y+16))y+=1;
            else
            {
                down=false;up=true;
            }
        }
        else if(up==true)
            if (y>level.getResolution() && level.isNBlocked(x,y-1) && level.isNBlocked(x+15,y-1)) y-=1;
            else
            {
                down=true;up=false;
            }
    }
    public int getPoints()
    {
        return points;
    }
    public static void setPoints(int newPoints)
    {
        points=newPoints;
    }
}
