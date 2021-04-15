package com.DynaBlaster.entity;

import com.DynaBlaster.map.Level;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Jeden z przeciwnikow dostepnych w grze
 */
public class CovidEnemy extends Enemy
{
    /** okresla liczbe punktow za zabicie potwora*/
    private static int points;
    public CovidEnemy(int x, int y, Level l) throws IOException
    {
        image = ImageIO.read(getClass().getResource("/resources/images/covid.png"));
        this.x=x;
        this.y=y;
        left=false;
        right=true;
        level=l;
    }
    /**
     *  Metoda odpowiadajaca za przemieszczanie sie
     */
    public void moving()
    {
        if(right==true)
        {
            if (x<level.getResolution()*level.getLength() && level.isNBlocked(x+16,y) && level.isNBlocked(x+16,y+15))x+=1;
            else
            {
                right=false;left=true;
            }
        }
        else if(left==true)
            if (x>level.getResolution() && level.isNBlocked(x-1,y) && level.isNBlocked(x-1,y+15)) x-=1;
            else
            {
                right=true;left=false;
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
