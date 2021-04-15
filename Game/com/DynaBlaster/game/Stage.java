package com.DynaBlaster.game;

import com.DynaBlaster.entity.*;
import com.DynaBlaster.map.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasa odpowiada za poleczenie obiektow ruchomych z nieruchomymi na mapie - kompletny opis danego poziomu
 */
public class Stage
{
    /**zawiera dane o budowie poziomu*/
    private Level level;
    private Bomber bomber;
    private ArrayList<Bomb> bombs;
    private ArrayList<Bonus> bonuses;
    private ArrayList<Enemy> enemies;
    /**odpowiada za czas trwania bomby*/
    private Timer timer;
    /**odpowiada za czas przez ktory Bomber jest nietykalny*/
    private Timer life_timer;
    private int lifeTime;
    /**parametry mapy*/
    private int mapWidth, mapLength, mapResolution;
    /**parametry mapy*/
    private int[][] mapMatrix;
    /**zasieg bomb*/
    private int bombrange=1;
    /**okresla czy Bomerowi nalezy zmiejszyc liczbe zyc*/
    private boolean takeLife=false;
    /**zebrane pukty za zabijanie potworow*/
    private int enemyPoints=0;
    /**okresla czy gracz jest niesmiertleny*/
    private boolean infLife=false;

    /**
     * Metoda odpowidajaca za sterowanie
     */
    public void keyPressed(KeyEvent e)
    {
        int c=e.getKeyCode();
        if(c==e.VK_SPACE)
        {
            Bomb a;
            try {
                if(!timer.isRunning()) {
                    timer.restart();
                    a = new Bomb((bomber.getX()+8)/16*16, (bomber.getY()+8)/16*16, level, bombrange);
                    bombs.add(a);

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        else
        bomber.keyPressed(e);
    }
    public Stage(int[][] matrix, int width, int length, int resolution) throws IOException
    {
        mapLength=length;mapResolution=resolution;mapWidth=width;
        mapMatrix=Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
        timer = new Timer(20,null);
        life_timer=new Timer(20,null);
        level = new Level(width,length,resolution);
        level.loadMatrix(matrix);
        bomber = new Bomber(level);
        bombs = new ArrayList<>();
        bonuses = new ArrayList<>();
        enemies = new ArrayList<>();
        for(int i=0;i<width;i++) {
            for (int j = 0; j < length; j++) {
                if (matrix[i][j] == 11)
                {bomber.setPosition(j * resolution + resolution, i * resolution + resolution);}
                else if (matrix[i][j] == 5) {
                    CovidEnemy a;
                    a = new CovidEnemy(j * resolution + resolution, i * resolution + resolution, level);
                    enemies.add(a);
                } else if (matrix[i][j] == 6) {
                    Bonus a;
                    a = new Bonus(j * resolution + resolution, i * resolution + resolution, 0);
                    bonuses.add(a);
                } else if (mapMatrix[i][j] == 7) {
                    KocurekEnemy a;
                    a = new KocurekEnemy(j * resolution + resolution, i * resolution + resolution, level);
                    enemies.add(a);
                } else if (mapMatrix[i][j] == 8) {
                    GhostEnemy a;
                    a = new GhostEnemy(j * resolution + resolution, i * resolution + resolution, level);
                    enemies.add(a);}
                else if (matrix[i][j] == 9) {
                    Bonus a;
                    a = new Bonus(j * resolution + resolution, i * resolution + resolution, 1);
                    bonuses.add(a);}
                    else if (matrix[i][j] == 10) {
                        Bonus a;
                        a = new Bonus(j * resolution + resolution, i * resolution + resolution, 2);
                        bonuses.add(a);}
            }
        }

    }

    /**
     * Funkcja odpowiada za reset stanu mapy w przypadku smierci Bombera
     */
    public void reset() throws IOException {
        enemyPoints=0;
        takeLife=false;
        infLife=false;
        bombrange=1;
        timer = new Timer(20, null);
        life_timer = new Timer(20, null);
        level = new Level(mapWidth,mapLength,mapResolution);
        level.loadMatrix(mapMatrix);
        bomber = new Bomber(level);
        bombs = new ArrayList<>();
        bonuses = new ArrayList<>();
        enemies = new ArrayList<>();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapLength; j++) {
                if (mapMatrix[i][j] == 11) {
                    bomber.setPosition(j * mapResolution + mapResolution, i * mapResolution + mapResolution);
                } else if (mapMatrix[i][j] == 5) {
                    CovidEnemy a;
                    a = new CovidEnemy(j * mapResolution + mapResolution, i * mapResolution + mapResolution, level);
                    enemies.add(a);
                } else if (mapMatrix[i][j] == 6) {
                    Bonus a;
                    a = new Bonus(j * mapResolution + mapResolution, i * mapResolution + mapResolution, 0);
                    bonuses.add(a);
                }
                else if (mapMatrix[i][j] == 7) {
                    KocurekEnemy a;
                    a = new KocurekEnemy(j * mapResolution + mapResolution, i * mapResolution + mapResolution, level);
                    enemies.add(a);
                }
                else if (mapMatrix[i][j] == 8) {
                    GhostEnemy a;
                    a = new GhostEnemy(j * mapResolution + mapResolution, i * mapResolution + mapResolution, level);
                    enemies.add(a);
                }
                else if (mapMatrix[i][j] == 9) {
                    Bonus a;
                    a = new Bonus(j * mapResolution + mapResolution, i * mapResolution + mapResolution, 1);
                    bonuses.add(a);}
                else if (mapMatrix[i][j] == 10) {
                    Bonus a;
                    a = new Bonus(j * mapResolution + mapResolution, i * mapResolution + mapResolution, 2);
                    bonuses.add(a);}
            }

        }
    }

    /**
     * Metoda okreslajaca zasieg bomb
     */
    public void setBombrange(int range){bombrange=range;}

    /**
     * Metoda odpowiadajaca za rysowanie planszy
     */
    public void drawMap(Graphics2D graph) throws IOException
    {
        level.drawMap(graph);
        waitExplode(graph);
        for (int i = 0; i < bonuses.size(); i++) {
            bonuses.get(i).draw(graph);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(graph);
        }

        if(!life_timer.isRunning())
            bomber.draw(graph);
        else
            bomber.draw2(graph);
    }

    /**
     * Metoda okreslajaca czy bomba powinna juz wybuchnac
     */
    public void waitExplode(Graphics2D graph)
    {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).addTime();
            bombs.get(i).draw(graph);
            if (bombs.get(i).getTime() > 120) {
                explosion(bombs.get(0).getX(), bombs.get(0).getY());
                if (bombs.get(i).getLeftDestroyable()) {
                    level.changeBlock(bombs.get(0).getX() - 16, bombs.get(0).getY());
                    explosion(bombs.get(0).getX() - 16, bombs.get(0).getY());
                    if (bombrange == 2 && bombs.get(i).getLeftDestroyable2()) {
                        level.changeBlock(bombs.get(0).getX() - 32, bombs.get(0).getY());
                        explosion(bombs.get(0).getX() - 32, bombs.get(0).getY());
                    }
                }

                if (bombs.get(i).getRightDestroyable()) {
                    level.changeBlock(bombs.get(0).getX() + 16, bombs.get(0).getY());
                    explosion(bombs.get(0).getX() + 16, bombs.get(0).getY());
                    if (bombrange == 2 && bombs.get(i).getRightDestroyable2()) {
                        level.changeBlock(bombs.get(0).getX() + 32, bombs.get(0).getY());
                        explosion(bombs.get(0).getX() + 32, bombs.get(0).getY());
                    }
                }

                if (bombs.get(i).getUpDestroyable()) {
                    level.changeBlock(bombs.get(0).getX(), bombs.get(0).getY() - 16);
                    explosion(bombs.get(0).getX(), bombs.get(0).getY() - 16);
                    if (bombrange == 2 && bombs.get(i).getUpDestroyable2()) {
                        level.changeBlock(bombs.get(0).getX(), bombs.get(0).getY() - 32);
                        explosion(bombs.get(0).getX(), bombs.get(0).getY() - 32);
                    }
                }

                if (bombs.get(i).getDownDestroyable()) {
                    level.changeBlock(bombs.get(0).getX(), bombs.get(0).getY() + 16);
                    explosion(bombs.get(0).getX(), bombs.get(0).getY() + 16);
                    if (bombrange == 2 && bombs.get(i).getDownDestroyable2()) {
                        level.changeBlock(bombs.get(0).getX(), bombs.get(0).getY() + 32);
                        explosion(bombs.get(0).getX(), bombs.get(0).getY() + 32);
                    }
                }


            }
            if (bombs.get(i).getTime() > 150) {
                timer.stop();
                bombs.clear();
            }
        }
    }

    /**
     * Metoda odpowiadajaca za poruszanie sie przeciwnikow
     */
    public void moving()
    {
        for(int i=0;i<enemies.size();i++)
        {
            enemies.get(i).moving();
        }
    }

    /**
     * Metoda odpowiada za kolizje Bombera z przeciwnikami
     */
    public boolean enemyCollision()
    {
        if(life_timer.isRunning())
        {
            lifeTime++;
            if(lifeTime>150) {
                life_timer.stop();
                lifeTime=0;
            }
        }
        else if(!life_timer.isRunning())
        {
            for (int i = 0; i < enemies.size(); i++) {
                if (Math.abs(enemies.get(i).getX() - bomber.getX()) <= 8 && Math.abs(enemies.get(i).getY() - bomber.getY()) <= 8)
                {
                    life_timer.start();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metoda odpowiadajaca za obrazenia wywolane w skutek eksplozji bomby
     */
    public boolean explosion(int x, int y)
    {
        for(int i=0;i<enemies.size();i++)
        {
            if (Math.abs(x - enemies.get(i).getX()) <= 8 && Math.abs(y - enemies.get(i).getY()) <= 8)
            {
                enemyPoints=enemyPoints+enemies.get(i).getPoints();
                enemies.remove(i);
            }

        }
        if(life_timer.isRunning())return false;
        else if(!life_timer.isRunning()&&!infLife)
        {
                if (Math.abs(x - bomber.getX()) <= 8 && Math.abs(y - bomber.getY()) <= 8)
                {
                    takeLife=true;
                    life_timer.start();
                    return true;
                }

        }
        return false;


    }

    /**
     * Metoda odpowiadajaca za zakonczenie poziomu
     */
    public boolean endLevel()
    {
        if(bomber.isPortol())
            return true;
        return false;
    }

    /**
     * Metoda sprawdzajaca czy mozna podniesc bonus
     */
   public int isBonus()
   {
       for (int i = 0; i < bonuses.size(); i++)
       {
           if (Math.abs(bonuses.get(i).getX() - bomber.getX()) <= 8 && Math.abs(bonuses.get(i).getY() - bomber.getY()) <= 8)
           {
               int tmp=bonuses.get(i).getType();
                bonuses.remove(i);
                return tmp+1;
           }
       }
       return 0;
   }

    /**
     * Metoda ustawiajaca niesmiertelnosc Bombera
     */
   public void isImmortal(boolean isActivated)
   {
        bomber.setImmortality(isActivated);
        infLife=isActivated;
   }

    /**
     * Metoda okreslajaca czy nalezy zadac obrazenia Bomberowi
     */
   public void setTakeLife(boolean is)
   {
       takeLife=is;
   }
   public boolean getTakeLife()
   {
       return takeLife;
   }

    /**
     * Metoda odpowiadajaca za uzyskiwanie punktow za zabijanie potworow
     */
   public int getEnemyPoints()
   {
       int abc=enemyPoints;
       enemyPoints=0;
        return abc;
   }
}
