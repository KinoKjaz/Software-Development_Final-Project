/*
Auther: Damion Shakespear
Date Created: 4/26/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FarklePlay {

    private static Stage playStage;
    VBox dieUI = new VBox();
    private static StackPane gameLayout = new StackPane();
    private static StackPane PlayBanner = new StackPane();
    private static StackPane exitMenuSP = new StackPane();
    private static Player currentPlayer;
    private static StackPane blockInteraction = new StackPane();
    private static Button btnBankScore = new Button("Bank");
    private static Button btnDieReroll = new Button("Re-Roll");
    
    public FarklePlay(Stage startStage) {
        playStage = startStage;
    }
    public static StackPane getPlayerBanner(){
        PlayBanner.getChildren().clear();
        PlayBanner.getChildren().add(SceneWarehouse.BannerPlayerTurn());
        return PlayBanner;
    }
    public static StackPane getDisplayScoreHeld(boolean display){
        dieScoreDistribution.calculatePlayerDieScore();
        return dieScoreDistribution.displayScoreHeld(display);
    }

    public void showGameScene() {

        StackPane spBackground = SceneWarehouse.getBackground(playStage);

        Rectangle holdZone = new Rectangle(830, 180, Color.NAVAJOWHITE);
        holdZone.setStroke(Color.DARKOLIVEGREEN);
        holdZone.setStrokeWidth(5);
        holdZone.setArcWidth(20);
        holdZone.setArcHeight(20);
        StackPane holdZoneSP = new StackPane(holdZone);
        holdZoneSP.setAlignment(Pos.CENTER);
        holdZoneSP.setTranslateY(90);

        ControlDice.makeDieList();
        dieUI.getChildren().addAll(ControlDice.getDiceList(), ControlDice.getSelectedDiceList());
        RollBank();
        dieUI.setAlignment(Pos.CENTER);
        dieUI.setTranslateY(60);
        dieUI.setSpacing(30);
        dieUI.setMinHeight(400);
        dieUI.setPrefHeight(500);

        getPlayerBanner();
        gameLayout.getChildren().addAll(spBackground, holdZoneSP, PlayBanner, dieUI);
        Scene gameRun = new Scene(gameLayout, 1000, 500);
        playStage.setTitle("FlipetyGibbitz");
        playStage.setScene(gameRun);
        playStage.setMinHeight(500);
        playStage.setMinWidth(1000);
        ScoreBoard();

        ControlDice.getDiceList().setPickOnBounds(false);
        ControlDice.getSelectedDiceList().setPickOnBounds(false);
    
//Button Section
        quitGame();//Calls for the Exit Button
    }
//Toggle Scoreboard
   private void ScoreBoard(){
        StackPane trianglePane = SceneWarehouse.getTriangleTB(180, 75, 30);
        StackPane Scoreboard = new StackPane(dieScoreDistribution.getScoreboard());
        ToggleButton TriangleTB = (ToggleButton) trianglePane.getChildren().get(0);
        VBox scoreboardTriangleTB = new VBox(TriangleTB);
        scoreboardTriangleTB.setAlignment(Pos.CENTER);
        scoreboardTriangleTB.setTranslateX(400);
        scoreboardTriangleTB.setTranslateY(-230);
        scoreboardTriangleTB.setMaxHeight(500);
        scoreboardTriangleTB.setMaxWidth(300);
        gameLayout.getChildren().add(3, scoreboardTriangleTB);

        HBox GameRules = new HBox(GameRules());

        TriangleTB.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                scoreboardTriangleTB.getChildren().add(Scoreboard);
                scoreboardTriangleTB.setTranslateX(400);
                scoreboardTriangleTB.setTranslateY(0);
                gameLayout.getChildren().remove(scoreboardTriangleTB);
                gameLayout.getChildren().addAll(GameRules, scoreboardTriangleTB);
                gameLayout.requestFocus();
            } else {
                scoreboardTriangleTB.getChildren().remove(Scoreboard);
                scoreboardTriangleTB.setTranslateX(400);
                scoreboardTriangleTB.setTranslateY(-230);
                gameLayout.getChildren().removeAll(GameRules, scoreboardTriangleTB);
                gameLayout.getChildren().add(3, scoreboardTriangleTB);
                gameLayout.requestFocus();
            }
        });
    }
//Toggle Game Rules
    public HBox GameRules(){
        StackPane trianglePaneGR = SceneWarehouse.getTriangleTB(-90, 75, 30);
        Label gameRules = SceneWarehouse.getGameRules(55, 0);
        gameRules.setTranslateX(55);
        gameRules.setTranslateY(0);
        ToggleButton TriangleGRTB = (ToggleButton) trianglePaneGR.getChildren().get(0);

        HBox gameRulesTriangleTB = new HBox(TriangleGRTB);
        gameRulesTriangleTB.setAlignment(Pos.CENTER);
        gameRulesTriangleTB.setTranslateX(605);
        gameRulesTriangleTB.setTranslateY(0);
        gameRulesTriangleTB.setMaxHeight(400);
        gameRulesTriangleTB.setMaxWidth(900);


        TriangleGRTB.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                gameRulesTriangleTB.getChildren().add(0, gameRules);
                gameRulesTriangleTB.setTranslateX(55);
                gameRulesTriangleTB.setTranslateY(0);
                gameLayout.requestFocus();
            } else {
                gameRulesTriangleTB.getChildren().remove(gameRules);
                gameRulesTriangleTB.setTranslateX(605);
                gameRulesTriangleTB.setTranslateY(0);
                gameLayout.requestFocus();
            }
        });
        return gameRulesTriangleTB;
    }
//End Game show Final Score and Exit/Restart Buttons
    public static void EndGameCondition(){
        currentPlayer = playerCompBench.getPlayerTurn();
        Rectangle popEndMenuBackground = new Rectangle(300, 450, Color.BLANCHEDALMOND);
        popEndMenuBackground.setStroke(Color.BROWN);
        Label endQuestion = new Label(currentPlayer.getPlayerName() + "\nHas won the Game. ");
        endQuestion.setStyle(
            "-fx-font-family: 'Comic Sans MS';" +
            "-fx-font-size: 25;" +
            "-fx-text-fill: Black;" +
            "-fx-background-color: transparent; " + 
            "-fx-border-color: transparent;");
            endQuestion.setTextAlignment(TextAlignment.CENTER);
        Button exitConfirm = new Button("Exit");
        exitConfirm.setStyle("-fx-background-color: Maroon; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 25;");
        exitConfirm.setPrefSize(160, 40);

        VBox chooseOptions = new VBox(10, endQuestion, exitConfirm);
        chooseOptions.setAlignment(Pos.CENTER);
        StackPane holdBackground = new StackPane(popEndMenuBackground, chooseOptions);
        holdBackground.setTranslateX(-30);
        HBox displayEnd = new HBox(holdBackground, dieScoreDistribution.getScoreboard());
        displayEnd.setAlignment(Pos.CENTER);
        StackPane endPop = new StackPane(displayEnd);
        endPop.setAlignment(Pos.CENTER);
        gameLayout.getChildren().add(endPop);

        exitConfirm.setOnAction(e -> {
            playStage.close();
        });
    }

//Call and Manage PopUps
    public static void BannerPopUps(int callPopUp){
        StackPane BannerTime = new StackPane();
        BannerTime.setMouseTransparent(true);
        BannerTime.getChildren().clear();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        BannerTime.getChildren().clear();
        gameLayout.getChildren().remove(BannerTime);
        }));
        BannerTime.getChildren().add(SceneWarehouse.bannerPopUpBuild(callPopUp));
        gameLayout.getChildren().add(BannerTime);
                timeline.setCycleCount(1);
                timeline.play();
    }
//Player Choice Input Output
    public void RollBank() {
        btnBankScore.setPrefSize(200, 60);
        btnBankScore.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 30;");
        btnDieReroll.setPrefSize(200, 60);
        btnDieReroll.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 30;");

        HBox RollBankHB = new HBox(80, btnDieReroll, getDisplayScoreHeld(true), btnBankScore);
        RollBankHB.setAlignment(Pos.CENTER);
        RollBankHB.setTranslateY(-30);
        RollBankHB.setPrefHeight(110);
        dieUI.getChildren().add(RollBankHB);

        btnBankScore.setOnAction(e -> {
            dieScoreDistribution.CheckTurn(true);
        });
        btnDieReroll.setOnAction(e -> {
            dieScoreDistribution.CheckTurn(false);
        });
    }
    public static void ComputerTurnUI(boolean lock){
        btnBankScore.setDisable(lock);
        btnDieReroll.setDisable(lock);
        blockInteraction.setMaxHeight(300);
        blockInteraction.setAlignment(Pos.CENTER);
        if (!gameLayout.getChildren().contains(blockInteraction)) {
            gameLayout.getChildren().add(blockInteraction);
        }
        if (lock){
            blockInteraction.setMouseTransparent(false);
        }
        else {
            blockInteraction.setMouseTransparent(true);
        }
    }
    
//Exit to menu Seaction
    public void quitGame(){
        StackPane exitDoorBtn = new StackPane();
        exitDoorBtn.getChildren().clear();
        exitDoorBtn.getChildren().add(SceneWarehouse.getExitBtn());
        exitDoorBtn.setAlignment(Pos.CENTER);
        exitDoorBtn.setMaxHeight(100);
        exitDoorBtn.setMaxWidth(30);
        exitDoorBtn.setTranslateY(-200);
        exitDoorBtn.setTranslateX(-450);

        gameLayout.getChildren().add(exitDoorBtn);

        exitDoorBtn.setOnMouseClicked(event -> {
            exitPopUp();
        });
    }
    public void exitPopUp(){

        Rectangle popMenuBackground = new Rectangle(300, 250, Color.BLANCHEDALMOND);
        popMenuBackground.setStroke(Color.BROWN);
        Label Question = new Label("Do you want to Exit\nthe Game?");
        Question.setStyle(
            "-fx-font-family: 'Comic Sans MS';" +
            "-fx-font-size: 25;" +
            "-fx-text-fill: Black;" +
            "-fx-background-color: transparent; " + 
            "-fx-border-color: transparent;");
       
        Button btnConfirm = new Button("Confirm");
        btnConfirm.setStyle("-fx-background-color: DarkOliveGreen; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 25;");
        Button btnCancel = new Button("Cancel");
        btnCancel.setStyle("-fx-background-color: Maroon; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 25;");

        VBox combineVB = new VBox(10, Question, btnConfirm, btnCancel);
        combineVB.setAlignment(Pos.CENTER);
        exitMenuSP.getChildren().addAll(popMenuBackground, combineVB);
        exitMenuSP.setPrefSize(470, 200);

        gameLayout.getChildren().add(exitMenuSP); 

        btnConfirm.setOnAction(e -> {
               playStage.close();
        });
        btnCancel.setOnAction(e -> {
            gameLayout.getChildren().remove(exitMenuSP);
        });
    }
}
