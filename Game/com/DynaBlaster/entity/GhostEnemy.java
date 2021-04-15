package com.DynaBlaster.entity;

import com.DynaBlaster.map.Level;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;
/**
 * Jeden z przeciwnikow dostepnych w grze; moze przenikac obiekty
 */
public class GhostEnemy extends Enemy{
    /**okresla liczbe punktow za zabicie potwora*/
    private static int points;
    Random generator = new Random();
    public GhostEnemy(int x, int y, Level l) throws IOException
    {
        image = ImageIO.read(getClass().getResource("/resources/images/guard.png"));
        this.x=x;
        this.y=y;
        left=false;
        right=false;
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
            if (y<level.getResolution()*level.getWidth())y+=1;
            else
            {
                generate();
            }
        }
        else if(up==true)
        {
            if (y>level.getResolution()) y-=1;
            else
            {
                generate();
            }
        }
        else if(left==true)
        {
            if (x>level.getResolution()) x-=1;
            else
            {
                generate();
            }
        }
        else if(right==true)
        {
            if (x<level.getResolution()*level.getLength()) x+=1;
            else
            {
                generate();
            }
        }


    }
    /**
     *  Metoda odpowiadajaca za losowosc w poruszaniu sie potwora
     */
    public void generate()
    {
        int tmp=generator.nextInt(4);
        if(tmp==0)
        {
            down=true;up=false;left=false;right=false;
        }
        if(tmp==1)
        {
            down=false;up=true;left=false;right=false;
        }
        if(tmp==2)
        {
            down=false;up=false;left=true;right=false;
        }
        if(tmp==3)
        {
            down=false;up=false;left=false;right=true;
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
