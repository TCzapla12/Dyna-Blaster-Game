package com.DynaBlaster.game;

import com.DynaBlaster.entity.Bonus;
import com.DynaBlaster.entity.CovidEnemy;
import com.DynaBlaster.entity.GhostEnemy;
import com.DynaBlaster.entity.KocurekEnemy;
import com.DynaBlaster.main.Menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Klasa ktora wyznacza reguly gry, odpowiada za przeliczanie puntkow, liczbe zyc
 */
public class Stats implements Runnable
{
    /**calkowita aktualna liczba punktow*/
    private int score;
    /**maksymalna liczba zyc*/
    private int life_max;
    /**aktualna liczba zyc*/
    private int life;
    /**wspolczynniki punktowe dla poszczegolnych rodzajow punktow*/
    private int time_mult, life_mult;
    /**liczba punktow zdobyta w poziomie*/
    private int timePoints, lifePoints;
    /**tekstury serduszek - liczby zyc*/
    private BufferedImage lifeIs, lifeNot;
    /**tekstury ikon*/
    private BufferedImage bombIcon, lifeIcon;
    /**okresla czy zwieksony obszar ataku bomb jest wlaczony*/
    private boolean bomb=false;
    /**okresla czy niesmiertelnosc jest wlaczona*/
    private boolean infLife=false;
    /**odpowiada za licznik czasu*/
    private long seconds=0, levelStartTime=0;
    /**watek sluzacy do odmierzania czasu*/
    private Thread thread;
    /**okresla czy nalezy zatrzymac watek*/
    private boolean stopThread=false;
    /**okresla parametry potrzebne do zapisu gry*/
    private int lifeSave=3, pointsSave;

    /**
     * Metoda aktywujaca watek odmierzania czasu
     */
    public void init() {
        thread = new Thread(this);
        thread.start();
    }
    @Override
    /**
     * Metoda zliczajaca czas
     */
    public void run()
    {
        while(!stopThread)
        {
            try {
                Thread.sleep(1000);
                seconds++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Metoda odpowiada za wczytywanie podstawowych wartosci zasad gry z pliku
     */
    public Stats(String path) throws IOException
    {
        lifeIs = ImageIO.read(getClass().getResource("/resources/images/lifeIs.png"));
        lifeNot = ImageIO.read(getClass().getResource("/resources/images/lifeNot.png"));
        bombIcon =  ImageIO.read(getClass().getResource("/resources/images/bombicon.png"));
        lifeIcon = ImageIO.read(getClass().getResource("/resources/images/lifeInf.png"));
        if(Menu.isOnline()==true)
        {
            String tmp = Menu.getoConfig();
            String[] element = tmp.split("#");
            CovidEnemy.setPoints(Integer.parseInt(element[0]));
            KocurekEnemy.setPoints(Integer.parseInt(element[1]));
            GhostEnemy.setPoints(Integer.parseInt(element[2]));
            Bonus.setPoints(Integer.parseInt(element[3]),0);
            Bonus.setPoints(Integer.parseInt(element[4]), 1);
            Bonus.setPoints(Integer.parseInt(element[5]), 2);
            life_max=Integer.parseInt(element[6]);
            life=life_max;
            time_mult=Integer.parseInt(element[7]);
            life_mult=Integer.parseInt(element[8]);
        }
        else
        {
            InputStream is=new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            CovidEnemy.setPoints(Integer.parseInt(br.readLine()));
            KocurekEnemy.setPoints(Integer.parseInt(br.readLine()));
            GhostEnemy.setPoints(Integer.parseInt(br.readLine()));
            Bonus.setPoints(Integer.parseInt(br.readLine()),0);
            Bonus.setPoints(Integer.parseInt(br.readLine()), 1);
            Bonus.setPoints(Integer.parseInt(br.readLine()), 2);
            String line = br.readLine();
            String[] element = line.split(" ");
            life_max=Integer.parseInt(element[0]);
            life=life_max;
            line = br.readLine();
            element = line.split(" ");
            time_mult=Integer.parseInt(element[0]);
            line = br.readLine();
            element = line.split(" ");
            life_mult=Integer.parseInt(element[0]);
        }


    }

    /**
     * Metoda liczaca sume punktow zdobytych w poziomie
     */
    public void setScore() {score=score+timePoints+lifePoints;}

    /**
     * Metoda dodajaca punkty
     */
    public void addScore(int x) {score=score+x;}
    /**
     * Metoda zmieniajaca parametry przy przejsciu do nastepnego poziomu
     */
    public void nextLevel()
    {
        setTimePoints();
        setLifePoints();
        setScore();
        if(life<life_max)
            life++;
        levelStartTime=seconds;
        lifeSave=life;
        pointsSave=score;

    }
    public int getLifeSave(){return lifeSave;}
    public int getPointsSave(){return pointsSave;}
    public long getTimeSave(){return levelStartTime;}

    /**
     * Metoda zatrzymujaca watek odmierzania czasu
     */
    public void setStopThread(boolean isActivated)
    {
       stopThread=isActivated;
       if(isActivated==false&&!thread.isAlive())init();
    }
    /**
     * Metoda odpowiadajaca za rysowanie statystyk na ekran
     */
    public void draw(Graphics2D graph, boolean isdraw, boolean isfinish)
    {
        String tmp=String.format("%02d", seconds%60);
        graph.setColor(Color.black);
        graph.fillRect(50, 2, 180, 12);
        graph.setColor(Color.green);
        graph.drawString( "Time: " + seconds / 60 + "." + tmp + "   Score: " + score, 52, 12);
        if(!infLife)
        {
            if (life == 3) {
                graph.drawImage(lifeIs, 200, 4, null);
                graph.drawImage(lifeIs, 210, 4, null);
                graph.drawImage(lifeIs, 220, 4, null);
            } else if (life == 2) {
                graph.drawImage(lifeIs, 200, 4, null);
                graph.drawImage(lifeIs, 210, 4, null);
                graph.drawImage(lifeNot, 220, 4, null);
            } else if (life == 1) {
                graph.drawImage(lifeIs, 200, 4, null);
                graph.drawImage(lifeNot, 210, 4, null);
                graph.drawImage(lifeNot, 220, 4, null);
            } else if (life == 0) {
                graph.drawImage(lifeNot, 200, 4, null);
                graph.drawImage(lifeNot, 210, 4, null);
                graph.drawImage(lifeNot, 220, 4, null);
            }
        }
        else
        {
            graph.drawImage(lifeIcon, 200, 4, null);
            graph.drawImage(lifeIcon, 210, 4, null);
            graph.drawImage(lifeIcon, 220, 4, null);
        }
        if (bomb)
            graph.drawImage(bombIcon, 225,14,null);
        if(isfinish)
        {
            graph.setColor(Color.black);
            graph.fillRect(70, 50, 100, 80);
            graph.setColor(Color.green);
            graph.drawString( "GAME OVER", 86, 80);
            graph.setColor(Color.red);
            graph.drawString( "Press any key", 82, 110);
        }
        else if (isdraw)
        {
            graph.setColor(Color.black);
            graph.fillRect(70, 50, 100, 80);
            graph.setColor(Color.green);
            graph.drawString( "PAUSE", 100, 60);
            graph.setColor(Color.red);
            graph.drawString( "Resume : R", 80, 80);
            graph.drawString( "Save & Exit : S", 80, 95);
            graph.drawString( "Exit : Q", 80, 110);
        }
    }

    /**
     * Metoda odpowiadajaca za zmiejszenie liczby zyc Bomberowi w przypadku otrzymania obrazen
     */
    public void kill()
    {
        if(life>0)life--;
    }
    public int getLife(){return life;}
    /**
     * Metoda odpowiadajaca za zwiekszenie liczby zyc po zebraniu bonusu zdrowia
     */
    public void addLife(){if(life<3)life++;}

    /**
     * Metoda restartujaca postep gry
     */
    public void reset()
    {
        lifeSave=3;
        levelStartTime=0;
        seconds=0;
        score=0;
        life=3;
        pointsSave=0;
        bomb=false;infLife=false;stopThread=false;

    }

    /**
     * Metoda okreslajaca aktywnosc zwiekszonego zasiegu bomb
     */
    public void bombBonus(boolean isActivated)
    {
        bomb=isActivated;
    }
    /**
     * Metoda okreslajaca aktywnosc efektu niesmiertelnosci
     */
    public void lifeBonus(boolean isActivated)
    {
        infLife=isActivated;
    }
    public boolean getImmortality()
    {
        return infLife;
    }

    /**
     * Metoda liczaca punkty za czas
     */
    public void setTimePoints()
    {
        if(seconds-levelStartTime<=180)
        timePoints= (int) (180-(seconds-levelStartTime))*time_mult;
        else
            timePoints=0;
    }

    /**
     * Metoda liczaca punkty za ilosc pozostalych zyc
     */
    public void setLifePoints()
    {
        if(life<3)
            lifePoints=life_mult*life;
        else
            lifePoints=life_mult*5;
    }
    public int getScore(){return score;}
    public void setLife(int lifes){life=lifes;}
    public void setPoints(int points){score=points;}
    public void setTime(int t){seconds=t; levelStartTime=t;}

}
