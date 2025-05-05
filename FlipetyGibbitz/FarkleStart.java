/*
Auther: Damion Shakespear
Date Created: 4/24/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FarkleStart extends Application {

    String namePlayer;
    Color dieColor = Color.WHITE;
    Color pipColor = Color.BLACK;
    private Shape pickDieTB = null;
    private Shape pickPipTB = null;
    private List<Color> chooseColors = List.of(Color.BLUE, Color.GREEN, Color.BLACK, Color.ORANGE, Color.PURPLE, Color.WHITE, Color.YELLOW, Color.BROWN, Color.PINK, Color.RED);
    private List<Shape> DieWithPip = new ArrayList<>();
    private TextField nameField;
    private Button btnNextPlayer;
    private Button btnBackPlayer;
    private Button btnStart;
    private HBox dieColorBox = new HBox(5);
    private HBox pipColorBox = new HBox(5);
    private static StackPane Menu = new StackPane();
    private static StackPane setupMenuSP = new StackPane();
    private int sendInput;

    Dice Test = new Dice(dieColor, pipColor);
    private static Stage startStage;

    public FarkleStart(){}
    public Stage getStartStage() {
        return startStage;
    }

//Main Menu
    @Override
    public void start(Stage primaryStage) {
        startStage = primaryStage;

    Label WelcomeBanner = new Label("Welcome to the Game of FlipetyGibbitz!");
    WelcomeBanner.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 42));
    WelcomeBanner.setStyle(
    "-fx-text-fill: Purple;" +
    "-fx-background-color: transparent; " + 
    "-fx-border-color: transparent;"
    );
    Label WelcomeShadow = new Label("Welcome to the Game of FlipetyGibbitz!");
    WelcomeShadow.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 42));
    WelcomeShadow.setStyle(
    "-fx-text-fill: NavajoWhite;" +
    "-fx-background-color: transparent; " + 
    "-fx-border-color: transparent;"
    );

    nameField = new TextField("Enter Name Here");
    nameField.setFont(Font.font("Comic Sans MS", 20));
    nameField.setPrefWidth(100);

    btnStart = new Button("START GAME");
    btnStart.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 25;");
    btnStart.setPrefSize(200, 60);
    btnStart.setDisable(true);

    btnNextPlayer = new Button("Next Player");
    btnNextPlayer.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 15;");
    btnNextPlayer.setPrefSize(120, 40);
    btnNextPlayer.setDisable(true);

    btnBackPlayer = new Button("Back");
    btnBackPlayer.setPrefSize(120, 40);
    btnBackPlayer.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 15;");
    btnBackPlayer.setDisable(true);

    HBox btnPlayerHB = new HBox(80, btnBackPlayer, btnNextPlayer);
    btnPlayerHB.setAlignment(Pos.CENTER);

    VBox WelcomeShadowVB = new VBox(WelcomeShadow);
    WelcomeShadowVB.setAlignment(Pos.CENTER);
    WelcomeShadowVB.setTranslateX(-73);
    WelcomeShadowVB.setTranslateY(-208);

    VBox WelcomeBannerVB = new VBox(WelcomeBanner);
    WelcomeBannerVB.setAlignment(Pos.CENTER);
    WelcomeBannerVB.setTranslateX(-70);
    WelcomeBannerVB.setTranslateY(-205);

    Label GameRules = SceneWarehouse.getGameRules(0,0);
    VBox GameRulesVB = new VBox(20, GameRules, btnStart);
    GameRulesVB.setAlignment(Pos.CENTER);
    GameRulesVB.setTranslateX(210);
    GameRulesVB.setTranslateY(30);

    createTB();
    StackPane diePane = Test.getDiePane();
    VBox CreationVB = new VBox(20, nameField, diePane, dieColorBox, pipColorBox, btnPlayerHB);
    CreationVB.setAlignment(Pos.CENTER);
    CreationVB.setMaxWidth(200);
    CreationVB.setTranslateX(-290);
    CreationVB.setTranslateY(40);

    StackPane spBackground = SceneWarehouse.getBackground(startStage);

//Complete Menu
    Menu.getChildren().addAll(spBackground, WelcomeShadowVB, WelcomeBannerVB, GameRulesVB, CreationVB);

    Scene scene = new Scene(Menu, 1000, 500);
    startStage.setTitle("The Game of FlipetyGibbitz");
    startStage.setScene(scene);
    startStage.show();
    startStage.setMinHeight(500);
    startStage.setMinWidth(1000);
    Menu.requestFocus();

//Button Events
    nameField.textProperty().addListener((observable, oldValue, newValue) -> updateBtns());
    playerCompBench.getPlayerList().addListener((ListChangeListener<Player>) change -> updateBtns());

    btnNextPlayer.setOnAction(e -> {
        namePlayer = nameField.getText();
        playerCompBench.makePlayer(namePlayer, dieColor, pipColor);
        nameField.setText("Enter Name Here");
        dieColor = Color.WHITE;
        pipColor = Color.BLACK;
        Test.reDieColor(dieColor);
        Test.rePipColor(pipColor);
    });
    btnBackPlayer.setOnAction(e -> {
        namePlayer = playerCompBench.getLastPlayerName();
        dieColor = playerCompBench.getLastDieColor();
        pipColor = playerCompBench.getLastPipColor();
        nameField.setText(namePlayer);
        Test.reDieColor(dieColor);
        Test.rePipColor(pipColor);
        playerCompBench.remakePlayer();
    });
    btnStart.setOnAction(e -> {
        namePlayer = nameField.getText();
        if (!namePlayer.equals("Enter Name Here")){
            playerCompBench.makePlayer(namePlayer, dieColor, pipColor);
        }
        switchGamePopUp(false);
    });
}
    private void updateBtns() {
        boolean nameValid = !nameField.getText().equals("Enter Name Here");
        boolean playersExist = !playerCompBench.getPlayerList().isEmpty();

        btnNextPlayer.setDisable(!nameValid);
        btnStart.setDisable(!(nameValid || playersExist));
        btnBackPlayer.setDisable(!playersExist);
    }
//Update PopUp Menu and Switch game scene to play
    public void switchGamePopUp(boolean check){

        Menu.getChildren().remove(setupMenuSP);
        setupMenuSP.getChildren().clear();

        Rectangle popMenuBackground = new Rectangle(400, 200, Color.BLANCHEDALMOND);
        popMenuBackground.setStroke(Color.BROWN);
        Label Question = new Label("");
        Question.setStyle(
            "-fx-font-family: 'Comic Sans MS';" +
            "-fx-font-size: 25;" +
            "-fx-text-fill: Black;" +
            "-fx-background-color: transparent; " + 
            "-fx-border-color: transparent;");
        Question.setText(" ");
        TextField getInput = new TextField("0");
        getInput.setFont(Font.font("Comic Sans MS", 20));
        getInput.setPrefWidth(50);
        getInput.setMinWidth(50);
        getInput.setMaxWidth(100);
        getInput.setText("0");
        Button btnConfirm = new Button("Confirm");
        btnConfirm.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS'; -fx-font-size: 25;");

        if (!check){
            Question.setText("How many Computer Opponents\ndo you want?");
        }
        else{
            Question.setText("What amount do you want\nto play to?");
            getInput.setText("10000");
        }
        VBox combineVB = new VBox(10, Question, getInput, btnConfirm);
        combineVB.setAlignment(Pos.CENTER);
        setupMenuSP.getChildren().addAll(popMenuBackground, combineVB);
        setupMenuSP.setPrefSize(400, 200);

        Menu.getChildren().add(setupMenuSP); 

        btnConfirm.setOnAction(e -> {
            if(!check){
                try {
                    sendInput = Integer.parseInt(getInput.getText());
                    playerCompBench.addComputerList(sendInput);
                } catch (NumberFormatException event) {
                    getInput.setText("0");
                }
                Menu.getChildren().remove(setupMenuSP);
                switchGamePopUp(true);
            }
            else {
                try {
                    sendInput = Integer.parseInt(getInput.getText());
                    dieScoreDistribution.setWinScore(sendInput);
                } catch (NumberFormatException event) {
                    getInput.setText("10000");
                }
                playerCompBench.resetIterator();
                FarklePlay Play = new FarklePlay(startStage);
                Play.showGameScene();
            }
        });
    }
//Dice Creation Options
    private void createTB() {
        dieColorBox.getChildren().clear();
        pipColorBox.getChildren().clear();
        DieWithPip.clear();
    
        for (Color color : chooseColors) {
            Shape DiceTB = createDieTB(color);
            dieColorBox.getChildren().add(DiceTB);
    
            Shape PipsTB = createPipTB(color);
            pipColorBox.getChildren().add(PipsTB);
        }
    }

    private void controlTB(Shape shape) {
        if (shape instanceof Rectangle) {
            if (pickDieTB != null) {
                pickDieTB.setStroke(Color.BLACK);
            }
            pickDieTB = shape;
            pickDieTB.setStroke(Color.YELLOW);
            dieColor = (Color) pickDieTB.getFill();
            Test.reDieColor(dieColor);
        } else if (shape instanceof Circle) {
            if (pickPipTB != null) {
                pickPipTB.setStroke(Color.BLACK);
            }
            pickPipTB = shape;
            pickPipTB.setStroke(Color.YELLOW);
            pipColor = (Color) pickPipTB.getFill();
            Test.rePipColor(pipColor);
        }
    }
    
    private Rectangle createDieTB(Color color) {
        Rectangle dieTB = new Rectangle(30, 30, color);
        dieTB.setStroke(Color.BLACK);
        dieTB.setStrokeWidth(2);
    
        dieTB.setOnMouseClicked(event -> controlTB(dieTB));
    
        return dieTB;
    }
    
    private Circle createPipTB(Color color) {
        Circle pipTB = new Circle(15, color);
        pipTB.setStroke(Color.BLACK);
        pipTB.setStrokeWidth(2);
    
        pipTB.setOnMouseClicked(event -> controlTB(pipTB));
    
        return pipTB;
    }
    public static void main(String[] args) {
        launch(args);
    }
}