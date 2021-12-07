package brickdestroyer.midgamescreen;

import java.io.*;
import java.util.Scanner;

/**
 * New Class - This represents the score of the user and the high scores saved
 */
public class HighScore {
    private int[][] score_list;
    private boolean newScore;
    private int position;
    private int level;
    private int currentScore;

    /**
     * Initialises the score_list array
     */
    public HighScore(){
        readScore();
        newScore=false;
    }

    /**
     * Checks if the score has beat any high score
     * @param value The score
     * @param wallLevel The level of the game
     */
    public void checkScore(long value, int wallLevel){
        newScore=false;
        this.level=wallLevel-1;
        int score=(int)(value/1000000);
        currentScore=score;
        for (int x=0; x<5; x++){
            if (score<score_list[level][x]){
                position=x;
                if(x!=4){
                    System.arraycopy(score_list[level], x, score_list[level], x + 1, 4 - x);
                }
                score_list[level][x]=score;
                newScore=true;
                break;
            }
        }
    }

    /**
     * Reads the high score text file and saves it to an array
     */
    private void readScore(){
        score_list = new int[5][5];
        int x=0;
        int y=0;
        try {
            File file = new File("score.txt");
            Scanner read = new Scanner(file);
            while (read.hasNextInt()) {
                score_list[x][y] = read.nextInt();
                if(y==4){
                    y=0;
                    x++;
                }
                else {
                    y++;
                }
            }
            read.close();
        } catch (Exception e) {
            System.out.println("There was an error");
        }
    }

    /**
     * Writes the new high score in the text file
     */
    public void writeScore(){
        if(newScore) {
            try {
                PrintWriter write = new PrintWriter(new FileWriter("score.txt"));
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        write.println(score_list[x][y]);
                    }
                }
                write.close();
            } catch (Exception e) {
                System.out.println("There was an error");
            }
        }
    }

    /**
     * Gets the score at the index
     * @param index The position of the score in that level
     * @return The string of the score to be displayed
     */
    public String displayScore(int index){
        int score = score_list[level][index];
        String score_string=formatScore(score);

        if (newScore && position==index){
            score_string += "     NEW HIGHSCORE!";
        }

        return score_string;
    }

    /**
     * Converts the score of the player to a more readable format
     * @return The string of the score of the player
     */
    public String displayPlayerScore(){
        int score = currentScore;
        return formatScore(score);
    }

    /**
     * Converts the score to a more readable format
     * @param score The score in milliseconds
     * @return The string to be displayed
     */
    public String formatScore(int score){
        int min = score/60000;
        int sec = (score%60000)/1000;
        int ms = (score%60000)%1000;
        String score_string;
        if (min<10){
            score_string = "0" + min + ":";
        }
        else{
            score_string = min + ":";
        }
        if (sec<10){
            score_string += "0" + sec + ":";
        }
        else{
            score_string += sec + ":";
        }
        if (ms<10){
            score_string += "0" + "0" + ms;
        }
        else if (ms<100){
            score_string += "0" + ms;
        }
        else{
            score_string += ms;
        }
        return score_string;
    }

}
