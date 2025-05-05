/*
Auther: Damion Shakespear
Date Created: 4/27/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class dieScoreDistribution {

    private static ObservableList<Player> Players;
    private static ObservableList<Dice> scoreSelectedDiceList;
    private static int[] Dcount = new int[6];
    private static StackPane scoreHeldSP = new StackPane();
    private static int winningScore;
    private static int scorePossible;
    private static int scorePossibleLB;
    private static Player currentPlayer;
    private static int[][] ScoreBoard = {
        {100, 200, 1000, 2000, 3000, 4000},
        {0, 0, 200, 400, 600, 800},
        {0, 0, 300, 600, 900, 1200},
        {0, 0, 400, 800, 1200, 1600},
        {50, 100, 500, 1000, 1500, 2000},
        {0, 0, 600, 1200, 1800, 2400}};
    private static int FarCheck;
    private static int FullRun;

    public static void setWinScore(int winScore){
        winningScore = winScore;
    }
    public static int getWinScore(){
        return winningScore;
    }

    public static StackPane getScoreboard() {
        Players = FXCollections.observableArrayList(playerCompBench.getPlayerList());
        VBox displayBoard = new VBox();

        for (Player player : Players) {
            Label nameScore = new Label();
            nameScore.textProperty().bind(Bindings.concat(player.getPlayerNameSSP(), ":   ", player.getScoreBankSIP().asString()));
            displayBoard.getChildren().add(nameScore);

            nameScore.setFont(Font.font("Comic Sans MS", 20));
            nameScore.setStyle(
            "-fx-text-fill: Black;" +
            "-fx-background-color: transparent; " + 
            "-fx-border-color: transparent;"
            );
        }
        displayBoard.setStyle("-fx-background-color: transparent;");

        Rectangle scoreBackground = new Rectangle(300, 450);
        scoreBackground.setFill(Color.BLANCHEDALMOND);
        scoreBackground.setStroke(Color.BROWN);
        scoreBackground.setArcWidth(10);
        scoreBackground.setArcHeight(10);

        ScrollPane scoreboardSP = new ScrollPane(displayBoard);
        scoreboardSP.setFitToWidth(true);
        scoreboardSP.setPannable(true);
        scoreboardSP.setStyle("-fx-background: BlanchedAlmond; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 20; -fx-border-color: Brown;");

        StackPane scoreboard = new StackPane(scoreBackground, scoreboardSP);
        scoreboard.setMaxHeight(450);
        scoreboard.setMaxWidth(300);
        scoreboard.setAlignment(Pos.CENTER);
        scoreboard.setTranslateX(-50);
        scoreboard.setTranslateY(0);
        scoreboard.setStyle("-fx-background-color: transparent;");

        return scoreboard;
    }
    public static StackPane displayScoreHeld(boolean display){
        currentPlayer = playerCompBench.getPlayerTurn();
        Rectangle scoreDisplay = new Rectangle(120, 50, Color.NAVAJOWHITE);
        scoreDisplay.setStroke(Color.DARKOLIVEGREEN);
        scoreDisplay.setStrokeWidth(3);
        scoreDisplay.setArcWidth(20);
        scoreDisplay.setArcHeight(20);
        scoreHeldSP.getChildren().clear();

        if (display){
            scorePossibleLB = scorePossible += currentPlayer.getScoreHold();
        }
        else {
            scorePossibleLB = 0;
        }
    
        Label scoreHeldLB = new Label("" + scorePossibleLB);
        scoreHeldLB.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 42));
        scoreHeldLB.setStyle(
        "-fx-text-fill: Black;" +
        "-fx-background-color: transparent; " + 
        "-fx-border-color: transparet;"
        );
        scoreHeldSP.getChildren().addAll(scoreDisplay, scoreHeldLB);        

        return scoreHeldSP;
    }
//Score Calculation
    public static void calculatePlayerDieScore(){

        currentPlayer = playerCompBench.getPlayerTurn();
            if (currentPlayer instanceof CompOpponent){
                scoreSelectedDiceList = FXCollections.observableArrayList(ControlDice.getDiceArrayList());
                for (Dice dice : scoreSelectedDiceList) {
                    if (dice.getDiePoints() && !dice.isScored()){
                            dice.removeDiePoints();
                    }
                }
            }
            else {
                scoreSelectedDiceList = FXCollections.observableArrayList(ControlDice.getSelectedDiceArrayList());
            }
            FarCheck = 0;
            FullRun = 0;

        for (int i = 0; i < Dcount.length; i++){
            Dcount[i] = 0;
        }

        for (Dice dice : scoreSelectedDiceList) {
            if(!dice.isScored()){
                switch (dice.getDieRoll()){
                    case 1: 
                        Dcount[0]++;
                        break;
                    case 2:
                        Dcount[1]++;
                        break;
                    case 3:
                        Dcount[2]++;
                        break;
                    case 4:
                        Dcount[3]++;
                        break;
                    case 5:
                        Dcount[4]++;
                        break;
                    case 6:
                        Dcount[5]++;
                }
            }
        }
        int sCheckCount = 0;//Check for Straight
        int sCheck = 0;//Check for Straight
        int pCheck = 0;//Check for 3 Pair
       
            for (int i = 0; i < Dcount.length; i++){
                if (Dcount[i] == 1){
                    sCheckCount++;
                }
                else if (Dcount[i] == 2){
                    pCheck++;
                }
            }
            if (sCheckCount == 6){
                for (Dice dice : scoreSelectedDiceList){
                    if (!dice.isScored()){
                    sCheck += (dice.getDieRoll());
                    }
                }
            }
            if (sCheck == 21){
                scorePossible = 3000;
                if (currentPlayer instanceof CompOpponent){
                    for (Dice dice : scoreSelectedDiceList){
                        dice.pointsScored();
                        FullRun = 6;
                        return;
                    }
                }
                    FullRoll();
                return;
            }
            if (pCheck == 3){
                scorePossible = 1500;
                if (currentPlayer instanceof CompOpponent){
                    for (Dice dice : scoreSelectedDiceList){
                        dice.pointsScored();
                        FullRun = 6;
                        return;
                    }
                }
                    FullRoll();
                return;
            }
            sCheckCount = 0;
            sCheck = 0;
            pCheck = 0;
        
        scorePossible = 0;
        for (int i = 0; i < Dcount.length; i++){
            if (Dcount[i] > 0){
            scorePossible += ScoreBoard[i][Dcount[i]-1];
            FarCheck += ScoreBoard[i][Dcount[i]-1];

                if (ScoreBoard[i][Dcount[i]-1] > 0){
                    FullRun += Dcount[i];
                    if (currentPlayer instanceof CompOpponent){
                        for (Dice dice : scoreSelectedDiceList){
                            if (dice.getDieRoll() == i + 1){
                                System.out.println("Checking dice with face: " + dice.getDieRoll());
                                dice.pointsScored();
                                System.out.println("Dice " + i + " flagged: " + dice.getDiePoints());
                            }
                        }
                    }
                }
            }
        }
    }
//Computer chooses to Bank or Roll
    public static void ComputerChoice(){

        if (FullRun == 6){
            FullRoll();
            return;
        }
        if (FullRun == 5){
            CheckTurn(true);//Return to Bank
        }
        else if ((scorePossible + currentPlayer.getScoreHold()) < 400 && FullRun < 4){
            CheckTurn(false);//Return to ReRoll
        }
        else {
            CheckTurn(true);//Return to Bank
        }
    }
//Checks the Outcome for each Turn.
    public static void CheckTurn(boolean Bank){
        currentPlayer = playerCompBench.getPlayerTurn();
    
            calculatePlayerDieScore();

        for (Dice dice : scoreSelectedDiceList) {
            if (currentPlayer instanceof CompOpponent){
                if (dice.getDiePoints()){
                    dice.Scored();
                }
            }
            else if (!(currentPlayer instanceof CompOpponent)) {
                dice.Scored();
            }
        }

        FullRun += currentPlayer.getFullRun();
        currentPlayer.holdFullRun(FullRun);

//Farkle Check
        if (FarCheck == 0){
            System.out.println("FlipetyGibbitz! Roll was no good, no points recieved.");
            FarklePlay.BannerPopUps(4);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                currentPlayer.clearScoreHold();
                currentPlayer.clearFullRun();
                playerCompBench.nextPlayerTurn();
                ControlDice.makeDieList();
                FarklePlay.getDisplayScoreHeld(false);
                FarklePlay.getPlayerBanner();
            });
            pause.play();
        }
//Run Check
        else if (FullRun == 6){
            System.out.println("All Dice scored, Keep Rolling!");
            FullRoll();
        }
        else {
            currentPlayer.scoreHold(scorePossible);
//Bank Event
            if (!Bank){
                System.out.println("Roll Again");
                FarklePlay.BannerPopUps(2);
                ControlDice.rollDiceAnimation();
                FarklePlay.getDisplayScoreHeld(true);
                if (currentPlayer instanceof CompOpponent){
                    playerCompBench.ComputerTurn();
                }
            }
//ReRoll Event
            else {
                System.out.println("Banked" + currentPlayer.getScoreHold());
                FarklePlay.BannerPopUps(1);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> {
                    currentPlayer.scoreBank();
                    currentPlayer.clearScoreHold();
                    currentPlayer.clearFullRun();
                    if (currentPlayer.getScoreBank() >= winningScore){
                        PlayerWonInstance();
                        return;
                    }
                    playerCompBench.nextPlayerTurn();
                    ControlDice.makeDieList();
                    FarklePlay.getDisplayScoreHeld(false);
                    FarklePlay.getPlayerBanner();
                });
                pause.play();
            }
        }
    }
//Full Run Event
    private static void FullRoll(){
        System.out.println("Full run GO" + currentPlayer.getScoreHold());
        currentPlayer.scoreHold(scorePossible);
        FarklePlay.BannerPopUps(3);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            currentPlayer.scoreBank();
            currentPlayer.clearScoreHold();
            currentPlayer.clearFullRun();
            if (currentPlayer.getScoreBank() >= winningScore){
                PlayerWonInstance();
                return;
            }
            ControlDice.makeDieList();
            FarklePlay.getDisplayScoreHeld(false);
            if (currentPlayer instanceof CompOpponent){
                playerCompBench.ComputerTurn();
            }
        });
        pause.play();
    }
    private static void PlayerWonInstance(){
        FarklePlay.BannerPopUps(5);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                FarklePlay.EndGameCondition();
        });
        pause.play();
    }
}