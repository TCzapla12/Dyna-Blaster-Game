package com.DynaBlaster.map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

/**
 * Klasa generujaca konkretny poziom gry
 */
public class Level {
    /**liczba kolumn makiety*/
    int length;
    /**liczba wierszy makiety*/
    int width;
    /**rozdzielczosc tekstur*/
    int resolution;
    /**rodzaje blokow*/
    private Block[] blocks;
    /**macierz przeszkod w poziomie*/
    private int[][] matrix;
    private BufferedImage in;
    public Level(int x, int y, int z)
    {
        width=x;
        length=y;
        resolution=z;
    }

    public int getResolution(){return resolution;}
    public int getLength(){return length;}
    public int getWidth(){return width;}

    /**
     * Metoda sprawdzajaca czy przez blok mozna przenikac
     */
    public boolean isNBlocked(int a, int b)
    {
        return Block.isNBlocked(matrix[b/16-1][a/16-1]);
    }

    /**
     * Metoda sprawdzajaca czy blok jest zniszczalny
     */
    public boolean isDestroyable(int a, int b){return Block.isDestroyable(matrix[b/16-1][a/16-1]);}

    /**
     * Metoda sprawdzajaca czy blok jest portalem
     */
    public boolean isPortol(int a, int b){if(matrix[b/16-1][a/16-1]==4)return true; else return false;}
    /**
     * Metoda wczytujaca tekstury blokow do gry
     */
    public void loadTexture() throws IOException
    {
        blocks=new Block[5];
        in = ImageIO.read(getClass().getResource("/resources/images/background.png"));
        blocks[0]= new Block(Block.EMPTY, in);
        in = ImageIO.read(getClass().getResource("/resources/images/stone.png"));
        blocks[1]= new Block(Block.SOLID, in);
        in = ImageIO.read(getClass().getResource("/resources/images/wood.png"));
        blocks[2]= new Block(Block.UNSOLID, in);
        in = ImageIO.read(getClass().getResource("/resources/images/wood.png"));
        blocks[3]= new Block(Block.HIDDEN,in);
        in = ImageIO.read(getClass().getResource("/resources/images/portol.png"));
        blocks[4]= new Block(Block.SOLID,in);
    }

    /**
     * Metoda zmieniajaca rodzaj bloku na inny po zniszczeniu przez bombe
     */
    public void changeBlock(int a,int b)
    {
        if( matrix[b/16-1][a/16-1]==3||matrix[b/16-1][a/16-1]==4)
            matrix[b/16-1][a/16-1]=4;
        else
            matrix[b/16-1][a/16-1]=0;
    }
    /**
     * Metoda wczytujaca bloki do poziomu z pliku konfiguracyjnego
     */
    public void loadMatrix(int[][] x)
    {
        matrix=new int[width][length];
        matrix=Arrays.stream(x).map(int[]::clone).toArray(int[][]::new);
    }

    /**
     * Metoda rysujaca poziom gry
     */
    public void drawMap(Graphics2D graph) throws IOException
    {
        loadTexture();
        for(int i=0;i<length+2;i++)
        {
            graph.drawImage(blocks[1].getImage(),i*resolution,0, null);
            graph.drawImage(blocks[1].getImage(),i*resolution,(width+1)*resolution, null);
        }
        for(int i=0;i<width+2;i++)
        {
           graph.drawImage(blocks[1].getImage(),0,i*resolution, null);
           graph.drawImage(blocks[1].getImage(),(length+1)*resolution,i*resolution, null);
        }
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<width;j++)
            {
                int pom=matrix[j][i];
                if(pom<5)
                graph.drawImage(blocks[pom].getImage(),i*resolution+resolution,j*resolution+resolution, null);
                else graph.drawImage(blocks[0].getImage(),i*resolution+resolution,j*resolution+resolution, null);

            }
        }
    }
}
