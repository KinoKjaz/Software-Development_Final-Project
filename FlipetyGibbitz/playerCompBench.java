/*
Auther: Damion Shakespear
Date Created: 4/26/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class playerCompBench {

    private static Color dieColor;
    private static Color pipColor;

    private static List<Color> chooseColors = List.of(Color.BLUE, Color.GREEN, Color.BLACK, Color.ORANGE, Color.PURPLE, Color.WHITE, Color.YELLOW, Color.BROWN, Color.PINK, Color.RED);
    private static ObservableList<Player> Players = FXCollections.observableArrayList();
    private static ListIterator<Player> trackPlayTurn;
    private static Player currentPlayer;
    

    public static ObservableList<Player> getPlayerList(){
        return Players;
    }
    public static void makePlayer(String namePlayer, Color dieColor, Color pipColor){
        Players.add(new Player(namePlayer, dieColor, pipColor));
    }
    public static void clearPlayers(){
        Players.clear();
    }

//Return and Remake last Player
    public static String getLastPlayerName(){
       return Players.get(Players.size() - 1).getPlayerName();
    }
    public static Color getLastDieColor(){
        return Players.get(Players.size() - 1).getPlayerDieColor();
     }
     public static Color getLastPipColor(){
        return Players.get(Players.size() - 1).getPlayerPipColor();
     }
    public static void remakePlayer(){

        if (!Players.isEmpty()) {
            Player lastPlayer = Players.get(Players.size() - 1);
            lastPlayer.playerDelete();
            Players.remove(Players.size() - 1);
        } else {
            System.out.println("No players to remove.");
        }
    }
//Make Computer List
    public static void addComputerList(int compCount){

        for (int i = 0; i < compCount; i++) {
            Random random = new Random();
            int ranDieColor = random.nextInt(chooseColors.size());
            int ranPipColor;
                do {
                    ranPipColor = random.nextInt(chooseColors.size());
                } 
                while (ranPipColor == ranDieColor);

            dieColor = chooseColors.get(ranDieColor);
            pipColor = chooseColors.get(ranPipColor);
            
            Players.add(new CompOpponent(i + 1, dieColor, pipColor));      
        }
    }
//Turn Control
    public static Player getPlayerTurn(){
        return currentPlayer;
    }
    public static void nextPlayerTurn(){

        if (trackPlayTurn == null || !trackPlayTurn.hasNext()) {
                trackPlayTurn = Players.listIterator();
        }
        currentPlayer = trackPlayTurn.next();
        System.out.println("Current Turn: " + currentPlayer.getPlayerName());

        if (currentPlayer instanceof CompOpponent){
            FarklePlay.ComputerTurnUI(true);
            ComputerTurn();
        }
        else {
            FarklePlay.ComputerTurnUI(false);
        }
    }
    public static void resetIterator(){
        trackPlayTurn = Players.listIterator();
        currentPlayer = trackPlayTurn.next();
    }
//Computer Turn Events
    public static void ComputerTurn(){
        Timeline CompThink = new Timeline(
            new KeyFrame(Duration.millis(1010), event -> {
                FarklePlay.getDisplayScoreHeld(true);
            }),
            new KeyFrame(Duration.millis(1020), event -> {
                ControlDice.computerSelectDie();
            }),
            new KeyFrame(Duration.millis(2500), event -> {
                dieScoreDistribution.ComputerChoice();
            })
        );
        CompThink.setCycleCount(1);
        CompThink.play();
    }
}