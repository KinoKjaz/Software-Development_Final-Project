/*
Auther: Damion Shakespear
Date Created: 4/26/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */
import javafx.scene.text.Font;
 import javafx.scene.text.FontWeight;
 import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class SceneWarehouse {
    private static Player currentPlayer;

    private static Label GameRules = new Label("Rules of the Game\n\n" +
        "Single 1: 100 points\n" +
        "Single 5: 50 points\n" +
        "Three of a Kind: 100 times the face value of the dice,\n except for three 1's which are worth 1,000 points\n" +
        "Four of a kind = 2x (double) the 3-of-a-kind value\n" +
        "Five of a kind = Triple the 3-of-a-kind value\n" +
        "Six of a kind = Quadruple the 3-of-a-kind value\n" +
        "1-2-3-4-5-6: 3,000 points\n" +
        "Three Pairs: 1,500 points");

    public static Label getGameRules(int tX, int tY){
        GameRules.setTextAlignment(TextAlignment.CENTER);
        GameRules.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
        GameRules.setTranslateX(tX);
        GameRules.setTranslateY(tY);
        GameRules.setStyle(
        "-fx-text-fill: Black;" +
        "-fx-background-color: BlanchedAlmond; " + 
        "-fx-border-color: Brown;"
        );
        return GameRules;
    }
    public static StackPane getBackground(Stage stage) {
        Rectangle foreground = new Rectangle(1000, 500, Color.TAN);
    
        Rectangle background = new Rectangle();
        background.setFill(Color.BLACK);
    
        StackPane spBackground = new StackPane();
        spBackground.getChildren().clear();
        spBackground.getChildren().addAll(background, foreground);
    
        spBackground.prefWidthProperty().bind(stage.widthProperty());
        spBackground.prefHeightProperty().bind(stage.heightProperty());
        background.widthProperty().bind(spBackground.widthProperty());
        background.heightProperty().bind(spBackground.heightProperty());
    
        return spBackground;
    }
//Triangle ToggleButton
    public static StackPane getTriangleTB(int R1, int R2, int R3){
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(0.0, 50.0, 80.0, 10.0, 150.0, 50.0);
        triangle.setFill(Color.DARKOLIVEGREEN);

        Rotate rotate = new Rotate(R1, R2, R3);
        triangle.getTransforms().add(rotate);

        ToggleButton toggleTriangleTB = new ToggleButton();
        toggleTriangleTB.setGraphic(triangle);
        toggleTriangleTB.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        toggleTriangleTB.setAlignment(Pos.TOP_RIGHT);

        toggleTriangleTB.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                triangle.setFill(Color.MAROON);
                rotate.setAngle(rotate.getAngle() + 180);
            } else {
                triangle.setFill(Color.DARKOLIVEGREEN);
                rotate.setAngle(rotate.getAngle() - 180);
            }
        });

        StackPane sendTriangleTB = new StackPane();
        sendTriangleTB.getChildren().clear();
        sendTriangleTB.getChildren().addAll(toggleTriangleTB);
        return sendTriangleTB;
    }
    public static StackPane getExitBtn(){
        StackPane exitBtn = new StackPane();
        Rectangle exitDoorway = new Rectangle(28, 59);
        exitDoorway.setFill(Color.BLACK);
        exitDoorway.setStroke(Color.BLACK);
        exitDoorway.setStrokeWidth(2);
        exitDoorway.setTranslateX(3);
        exitDoorway.setTranslateY(-5);

        Polygon exitDoor = new Polygon();
        exitDoor.getPoints().addAll(
            -15.0, -15.0, 
            10.0, -5.0,
            10.0, 55.0, 
            -15.0, 45.0);
        exitDoor.setFill(Color.MAROON);
        exitDoor.setStroke(Color.TRANSPARENT);
        exitDoor.setStrokeWidth(2);

        exitBtn.getChildren().addAll(exitDoorway, exitDoor);
        return exitBtn;
    }

//Player Banner Section
    public static StackPane BannerPlayerTurn() {
        currentPlayer = playerCompBench.getPlayerTurn();
        StackPane bannerPT = new StackPane();
        bannerPT.getChildren().clear();
    
        Label bannerNameShadow = BannerLabel(currentPlayer.getPlayerName() + "'s Turn!","NavajoWhite", "transparent", "transparent", 42);
        Label bannerName = BannerLabel(currentPlayer.getPlayerName() + "'s Turn!","Purple", "transparent", "transparent", 42);
    
        VBox bannerNameShadowVB = BannerVB(bannerNameShadow, -3, -208, 0);
        VBox bannerNameVB = BannerVB(bannerName, 0, -205, 0);
    
        bannerPT.getChildren().addAll(bannerNameShadowVB, bannerNameVB);
        return bannerPT;
    }

    public static StackPane bannerPopUpBuild(int callPopUp){
        currentPlayer = playerCompBench.getPlayerTurn();
        Rectangle bannerBackground = BannerRectangle(0, 0, null, null);
        Label bannerBuildShadow = BannerLabel(" ", "Tan", "NavajoWhite", "DarkOliveGreen", 42);
        Label bannerBuild = BannerLabel(" ", "Purple", "transparent", "transparent", 42);
        bannerBuildShadow.setText(" ");
        bannerBuild.setText(" ");
        StackPane banner = new StackPane();
        banner.getChildren().clear();
        banner.setMouseTransparent(true);

        switch (callPopUp){
            case 1: //Show Bank & Score Banked
                bannerBuildShadow.setText(currentPlayer.getScoreHold() + " Banked ");
                bannerBuild.setText(currentPlayer.getScoreHold() + " Banked ");
                break;
            case 2: //Show ReRoll
                bannerBuildShadow.setText(" Roll Again! ");
                bannerBuild.setText(" Roll Again! ");
                break;
            case 3: //Show FullRun! & Score Made
                bannerBuildShadow.setText(" All Dice Scored to " + currentPlayer.getScoreHold() + ", Keep Rolling! ");
                bannerBuild.setText(" All Dice Scored to " + currentPlayer.getScoreHold() + ", Keep Rolling! ");
                break;
            case 4: //Show Farkled
                bannerBuildShadow.setText(" Ohhh FlipetyGibbitz! ");
                bannerBuild.setText(" Ohhh FlipetyGibbitz! ");
                break;
            case 5: //show Winner
                bannerBuildShadow.setText(" " + currentPlayer.getPlayerName() + " has reached " + dieScoreDistribution.getWinScore() + " ");
                bannerBuild.setText(" " + currentPlayer.getPlayerName() + " has reached " + dieScoreDistribution.getWinScore() + " ");
        }
        VBox bannerBuildShadowVB = BannerVB(bannerBuildShadow, -3, -3, 0);
        VBox bannerBuildVB = BannerVB(bannerBuild, 0, 0, 0);

        banner.getChildren().addAll(bannerBackground, bannerBuildShadowVB, bannerBuildVB);
        return banner;
    }
//Create Label and VBox for PopUps
    private static Label BannerLabel(String text, String txtColor, String bgColor, String borColor, int size) {
        Label poplabel = new Label(text);
        poplabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, size));
        poplabel.setStyle("-fx-text-fill: " + txtColor + ";" + 
                  "-fx-background-color: " + bgColor + ";" + 
                  "-fx-border-color: " + borColor + ";" + 
                  "-fx-border-radius: 10px;" +
                  "-fx-background-radius: 10px;" +
                  "-fx-border-width: 3px;");
        return poplabel;
    }
    private static VBox BannerVB(Label label, int translateX, int translateY, int space) {
        VBox popVB = new VBox(label);
        popVB.setAlignment(Pos.CENTER);
        popVB.setSpacing(space);
        popVB.setTranslateX(translateX);
        popVB.setTranslateY(translateY);
        return popVB;
    }
    private static Rectangle BannerRectangle(int length, int hight, Color fillColor, Color strokeColor){
        Rectangle popRectangle = new Rectangle(length, hight, fillColor);
        popRectangle.setStroke(strokeColor);
        popRectangle.setStrokeWidth(3);
        popRectangle.setArcWidth(20);
        popRectangle.setArcHeight(20);
        return popRectangle;
    }
}