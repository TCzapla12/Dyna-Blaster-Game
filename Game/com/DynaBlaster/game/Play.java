package com.DynaBlaster.game;

import com.DynaBlaster.entity.*;
import com.DynaBlaster.main.List;
import com.DynaBlaster.main.Menu;
import com.DynaBlaster.main.ScoreWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Klasa odpowiadajaca za wczytywanie gry, tutaj wykonywane sa glowne operacje
 */
public class Play implements Config
{
    /**okresla ile zajmuje tekstura obrazu*/
    private final int RESOLUTION = 16;
    /**okresla aktualnie przechodzona plansze*/
    private int activeStage=0;
    /**okresla liczbe kolumn makiety*/
    private int length;
    /**okresla liczbe wierszy makiety*/
    private int width;
    /**odpowiada za rozdzielczosc makiety*/
    private int resolution;
    /**okresla liczbe plansz*/
    private int stages;
    /**zawiera dane makiety z pliku konfiguracyjnego*/
    private int matrix[][];
    /**zawiera dane o poziomach*/
    private ArrayList<Stage> stage;
    /**odpowiada za statystyki*/
    private Stats stats;
    /**odpowiada za sterowanie czasem trwania efektu niesmiertelnosci*/
    private Timer timer;
    /**okresla stan zatrzymania gry*/
    private boolean stop=false;
    /**okresla czy wlaczona jest pauza*/
    private boolean drawPause=false;
    /**okresla czy gra zostala ukonczona*/
    private boolean isFinished=false;
    public boolean isFinished(){return isFinished;}

    /**
     * Zadanie odpowiadajace za trwanie efektu niesmiertelnosci
     */
    class RemindTask extends TimerTask {
        public void run() {
            stats.lifeBonus(false);
            stage.get(activeStage).isImmortal(false);
            timer.cancel();
        }
    }
    public boolean isDrawPause(){return drawPause;}

    /**
     * Metoda zatrzymujaca gre
     */
    public void stop() {
        stats.setStopThread(true);
        stop=true;
    }

    /**
     * Metoda wznawiajaca gre
     */
    public void start() {
        stats.setStopThread(false);
        stop=false;
    }
    /**
     * Metoda odpowiadajaca za pauze
     */
    public void pause()
    {
        stop();
        drawPause=true;
    }
    /**
     * Metoda odpowiadajaca za wznowienie gry po pauzie
     */
    public void resume()
    {
        start();
        drawPause=false;
    }
    /**
     * Metoda odpowidajaca za sterowanie
     */
    public void keyPressed(KeyEvent e) throws IOException
    {
        stage.get(activeStage).keyPressed(e);
        event();
    }

    /**
     * Metoda odpowiadajaca za zdarzenia na mapie gry
     */
    public void event() throws IOException {
        if(stage.get(activeStage).endLevel())
        {
            if(activeStage<stages-1)
            {
                stop();
                JOptionPane.showMessageDialog(null, "Level "+ (activeStage+1) +" completed" + "\n");
                activeStage++;
                stats.bombBonus(false);
                stats.lifeBonus(false);
                stats.nextLevel();
                start();
            }
            else
            {
                stop();
                JOptionPane.showMessageDialog(null, "Level "+ (activeStage+1) +" completed" + "\n");
                stats.nextLevel();
                isFinished=true;
                new ScoreWindow(Integer.toString(stats.getScore()));
            }
        }

        int tmp=stage.get(activeStage).isBonus();
        if(tmp==0);
        else if(tmp==1)
        {
            stats.addLife();
            stats.addScore(Bonus.getPoints(0));
        }
        else if(tmp==2)
        {
            stage.get(activeStage).setBombrange(2);
            stats.bombBonus(true);
            stats.addScore(Bonus.getPoints(1));
        }
        else if(tmp==3)
        {
            timer=new Timer();
            timer.schedule(new RemindTask(), 10*1000);
            stats.lifeBonus(true);
            stage.get(activeStage).isImmortal(true);
            stats.addScore(Bonus.getPoints(2));
        }
    }


    /**
     * Metoda wczytujaca parametry poziomu z pliku wejsciowego do gry przy uzyciu tablicy matrix
     */
    public void loadLevel(String path) throws IOException
    {
        stage=new ArrayList<Stage>();
        resolution = RESOLUTION;
        if(Menu.isOnline()==true)
        {
            String tmp = Menu.getoLevel();
            String[] element = tmp.split("#");
            stages=Integer.parseInt(element[0]);
            String[] element2 = element[1].split(" ");
            length=Integer.parseInt(element2[0]);
            width=Integer.parseInt(element2[1]);
            matrix = new int[width][length];
			
            for(int k=0;k<stages;k++)
            {
                for (int i = 0; i < width; i++)
                {
                    String line[] =  element[2+i+k*width].split(" ");
                    for (int j = 0; j < length; j++)
                    {
                        matrix[i][j] = Integer.parseInt(line[j]);
                    }
                }
                Stage a;
                a=new Stage(matrix, width, length, resolution);
                stage.add(a);
            }
        }
        else
        {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            String[] element = line.split(" ");
            stages = Integer.parseInt(element[0]);
            line = br.readLine();
            element = line.split(" ");
            length = Integer.parseInt(element[0]);
            width = Integer.parseInt(element[1]);

            matrix = new int[width][length];
            for(int k=0;k<stages;k++)
            {
                for (int i = 0; i < width; i++)
                {
                    line = br.readLine();
                    element = line.split(" ");
                    for (int j = 0; j < length; j++)
                    {
                        matrix[i][j] = Integer.parseInt(element[j]);
                    }
                }
                Stage a;
                a=new Stage(matrix, width, length, resolution);
                stage.add(a);
            }
            is.close();
        }


    }
    /**
     * Metoda wczytujaca zasady gry (liczba zyc, wspolczynniki punktowe...)
     */
    public void loadStats(String path) throws IOException
    {
        stats = new Stats(path);
    }
    /**
     * Metoda rysujaca mape gry oraz obiekty z klasy Entity na ekran monitora
     */
    public void startGame(String path, String path2, Graphics2D graph) throws IOException
    {
        loadLevel(path);
        loadStats(path2);
        stats.init();

        stage.get(activeStage).drawMap(graph);
        stats.draw(graph, drawPause, isFinished);
    }
    /**
     * Metoda rysujaca mape gry oraz obiekty z klasy Entity na ekran monitora (dla wczytanej gry)
     */
    public void startGame(String path, String path2, Graphics2D graph, String path3) throws IOException
    {
        loadLevel(path);
        loadStats(path2);
        loadSave(path3);
        stats.init();

        stage.get(activeStage).drawMap(graph);
        stats.draw(graph, drawPause, isFinished);
    }

    /**
     * Metoda wczytujaca gre z pliku
     */
    public void loadSave(String path) throws IOException {
        if(Menu.isOnline()==true)
        {
            String tmp = Menu.getoSave();
            String[] element = tmp.split("#");
            activeStage = Integer.parseInt(element[0]);
            stats.setLife(Integer.parseInt(element[1]));
            stats.setPoints(Integer.parseInt(element[2]));
            stats.setTime(Integer.parseInt(element[3]));
        }
        else
        {
            InputStream is = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            String[] element = line.split(" ");
            activeStage = Integer.parseInt(element[0]);
            line = br.readLine();
            element = line.split(" ");
            stats.setLife(Integer.parseInt(element[0]));
            line = br.readLine();
            element = line.split(" ");
            stats.setPoints(Integer.parseInt(element[0]));
            line = br.readLine();
            element = line.split(" ");
            stats.setTime(Integer.parseInt(element[0]));
        }

    }
    /**
     * Metoda zapisujaca gre do pliku
     */
    public void saveSave(String path) throws IOException
    {
        if(Menu.isOnline()==true)
        {
            try {
                Socket serverSocket = new Socket("localhost", 12129);
                OutputStream os = serverSocket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("GAME_SAVE:"+activeStage+"#"+stats.getLifeSave()+"#"+stats.getPointsSave()+"#"+stats.getTimeSave());
                serverSocket.close();
				Menu.setSave(activeStage+"#"+stats.getLifeSave()+"#"+stats.getPointsSave()+"#"+stats.getTimeSave());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "NO CONNECTION");
                Menu.setOnline(false);}
        }
        if(com.DynaBlaster.main.Menu.isOnline()==false)
        {
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            br.write(activeStage+"\n");
            br.write(stats.getLifeSave()+"\n");
            br.write(stats.getPointsSave()+"\n");
            br.write(stats.getTimeSave()+"\n");
            br.close();
        }

    }
    /**
     * Metoda odpowiadajaca za rysowanie mapy i obiektow
     */
    public void repaint(Graphics2D graph) throws IOException
    {
        if(!stop)stage.get(activeStage).moving();
        stage.get(activeStage).drawMap(graph);
        stats.draw(graph, drawPause, isFinished);
    }

    /**
     * Metoda odpowiedzialna za sprawdzanie kolizji dla obiektow
     */
    public void Collision() throws IOException
    {
        stats.addScore(stage.get(activeStage).getEnemyPoints());
        if(!stats.getImmortality())
        {
            if(stage.get(activeStage).enemyCollision()||stage.get(activeStage).getTakeLife())
            {

                stage.get(activeStage).setTakeLife(false);
                if(stats.getLife()<=1)
                {
                    for(int i=0;i<stages;i++)
                    {
                        stage.get(i).reset();
                    }
                    activeStage=0;
                    stats.reset();

                }
                else
                    stats.kill();
            }
        }
    }
}
