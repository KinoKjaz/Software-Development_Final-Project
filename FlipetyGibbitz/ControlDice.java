/*
Auther: Damion Shakespear
Date Created: 4/26/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */

import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ControlDice {

    private static ObservableList<Dice> DiceList = FXCollections.observableArrayList();
    private static ObservableList<Dice> selectedDiceList = FXCollections.observableArrayList();
    private static HBox dieListHB = new HBox(20);
    private static HBox selectedDiceListHB = new HBox(20);
    private static  Player currentPlayer;

    public static void clearDiceLists(){
        DiceList.clear();
        selectedDiceList.clear();
        dieListHB.getChildren().clear();
        selectedDiceListHB.getChildren().clear();
    }
    
    public static void makeDieList() {
    
        DiceList.clear();
        dieListHB.getChildren().clear();
        selectedDiceList.clear();
        selectedDiceListHB.getChildren().clear();
        currentPlayer = playerCompBench.getPlayerTurn();
        Color dieColor = currentPlayer.getPlayerDieColor();
        Color pipColor = currentPlayer.getPlayerPipColor();
    
        rollDiceAnimation();
        for (int i = 0; i < 6; i++) {
            Dice dice = new Dice(i, dieColor, pipColor);
            
            DieLISTener(dice);
            DiceList.add(dice);
            dieListHB.getChildren().add(dice);
        }
    }
    public static ObservableList<Dice> getDiceArrayList(){
        return DiceList;
    }
    public static ObservableList<Dice> getSelectedDiceArrayList(){
        return selectedDiceList;
    }

    public static void remakeDieList(){

        selectedDiceList.clear();
        dieListHB.getChildren().clear();
        selectedDiceListHB.getChildren().clear();
        for (Dice dice : DiceList) {
            if (!dice.isScored()){
                dice.Reroll();
                dieListHB.getChildren().add(dice);
            }
            else {
                selectedDiceList.add(dice);
                selectedDiceListHB.getChildren().add(dice);
            }       
        }
    }
    public static void rollDiceAnimation() {
        Timeline rollDie = new Timeline();
        int startTime = 50;
        int maxTime = 500;
        int steps = 10;
    
        for (int i = 0; i < steps; i++) {
            int timeStep = startTime + ((maxTime - startTime) * i / steps);
            rollDie.getKeyFrames().add(
                new KeyFrame(Duration.millis(timeStep), event -> {
                    remakeDieList();
                })
            );
        }
        rollDie.setCycleCount(1);
        rollDie.play();
        FarklePlay.getDisplayScoreHeld(false);
    }

    private static void DieLISTener(Dice dice) {
        dice.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            currentPlayer = playerCompBench.getPlayerTurn();

            if (isSelected) {
                dieListHB.getChildren().remove(dice);
                selectedDiceList.add(dice);
                selectedDiceListHB.getChildren().add(dice);
            } else {
                selectedDiceListHB.getChildren().remove(dice);
                selectedDiceList.remove(dice);
                dieListHB.getChildren().add(dice);
            }
            FarklePlay.getDisplayScoreHeld(true);
        });
    }
    
    public static HBox getDiceList() { 
        for (Dice dice : DiceList) {
            if (dice.isScored()){
                dieListHB.getChildren().remove(dice);
            }
        }
        dieListHB.setAlignment(Pos.CENTER);
        dieListHB.setPrefHeight(130);
        return dieListHB;
    }
    
    public static HBox getSelectedDiceList() {
        for (Dice dice : DiceList) {
            if (dice.isScored()){
                selectedDiceListHB.getChildren().add(dice);
            }
        }
        selectedDiceListHB.setAlignment(Pos.CENTER);
        selectedDiceListHB.setTranslateY(20);
        selectedDiceListHB.setPrefHeight(130);
        return selectedDiceListHB;
    }
//Computer Die Updates
    public static void computerSelectDie() {
        for (Dice dice : DiceList) {

            if (dice.getDiePoints()){
                dice.setSelected(true);
            }
            else {
                dice.setSelected(false);
            }
        }
    }
}
