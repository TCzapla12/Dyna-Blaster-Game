package com.DynaBlaster.main;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Klasa przechowujaca parametry poszczegolnych wynikow
 */
class Result
{
    Result(String nick, int score){this.nick=nick;this.score=score;}
    /**nazwa gracza*/
    String nick;
    /**wynik gracza*/
    int score;
    public int getScore(){return score;}
    public String getNick(){return nick;}

}

/**
 * Klasa przechowujaca wyniki
 */
public class List
{
    /**Lista wynikow*/
    static ArrayList<Result> results=new ArrayList<>();

    /**
     * Metoda dodajaca nowy rezultat do listy wynikow
     */
    public static void getScore(String nickname, int score)
    {

        if(results.size()<10)
        {
            results.add(new Result(nickname, score));
            Collections.sort(results, (a,b) -> a.score > b.score ? -1 : a.score == b.score ? 0 : 1);
        }
        else if(score>results.get(9).getScore())
        {
            results.remove(9);
            results.add(new Result(nickname, score));
            Collections.sort(results, (a,b) -> a.score > b.score ? -1 : a.score == b.score ? 0 : 1);
        }
    }
    public static int getSize()
    {
        return results.size();
    }
    public static String getString(int n)
    {
        return results.get(n).getNick()+"@"+results.get(n).getScore();
    }

    /**
     * Metoda zwracajaca wyniki jak obiekty (wykorzystywane w tabeli)
     */
    public static Object[][] getData()
    {
        Object[][] data = new Object[results.size()+1][3];
        data[0][0]="Number";data[0][1]="Nick";data[0][2]="Score";
        for(int i=1;i<results.size()+1;i++)
        {
            data[i][0]=i;
            data[i][1]=results.get(i-1).getNick();
            data[i][2]=results.get(i-1).getScore();
        }
        return data;
    }
}
