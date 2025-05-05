/*
Auther: Damion Shakespear
Date Created: 4/24/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ToggleButton;

public class Dice extends ToggleButton {

    private int dieCount;
    private Color dieColor;
    private Color pipColor;
    private boolean dieScored;
    private StackPane dieMade;
    private boolean DieHasPoints;
    
    public Dice(){
        dieCount = (int) (Math.random() * 6 + 1);
        dieColor = Color.WHITE;
        pipColor = Color.BLACK;
        setDice();
        setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 15;");
        setGraphic(dieMade);
        setListener();

        setFocusTraversable(true);
        setDisable(false);

    }
    public Dice(Color dieColor, Color pipColor) {
        this.dieColor = dieColor;
        this.pipColor = pipColor;
        this.dieCount = (int) (Math.random() * 6 + 1);
        setDice();
        setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 15;");
        setGraphic(dieMade);
        setListener();

        setFocusTraversable(true);
        setDisable(false);
    }

    public Dice(int i, Color dieColor, Color pipColor) {
        this.dieColor = dieColor;
        this.pipColor = pipColor;
        this.dieCount = (int) (Math.random() * 6 + 1);
        setDice();
        setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 15;");
        setGraphic(dieMade);
        setListener();

        setFocusTraversable(true);
        setDisable(false);
    }

//Dice control
    public void Reroll(){
        dieCount = (int) (Math.random() * 6 + 1);
        updateDice();
    }
    public int getDieRoll(){
        return dieCount;
    }
    public void Scored() {
        dieScored = true;
        setDisable(true);
    }
    public void unScoreed(){
        dieScored = false;
        setDisable(false);
    }
    public boolean isScored(){
        return dieScored;
    }
    public void pointsScored(){
        DieHasPoints = true;
    }
    public void removeDiePoints(){
        DieHasPoints = false;
    }
    public boolean getDiePoints(){
        return DieHasPoints;
    }
    
    private void setListener() {
        selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                setStyle("-fx-font-size: 20; -fx-padding: 0; -fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 15;");
            } else {
                setStyle("-fx-font-size: 20; -fx-padding: 0; -fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 15;");
                dieMade.setStyle("");
            }
        });
    }

//Dice Creation
    public void reDieColor(Color dieColor){
        this.dieColor = dieColor;
        updateDice();
    }
    public void rePipColor(Color pipColor){
        this.pipColor = pipColor;
        updateDice();
    }

    private void setDice(){
        dieMade =  dieDesign();
    }
    private void updateDice() {
        dieMade.getChildren().clear();
        dieMade.getChildren().add(dieDesign());
        setGraphic(dieMade);
    }
    public StackPane getDiePane() {
        return dieMade;
    }

    private StackPane dieDesign() {
        Rectangle dieCube = new Rectangle(100, 100);
        dieCube.setFill(dieColor);
        dieCube.setStroke(Color.BLACK);
        dieCube.setArcWidth(20);
        dieCube.setArcHeight(20);

        Group dieForm = new Group(dieCube);
        dieForm.getChildren().addAll(numPip());

        StackPane dieUpdated = new StackPane();
        dieUpdated.getChildren().add(dieForm);

        dieUpdated.setPrefSize(100, 100);

        return dieUpdated;
    }

    private Node[] numPip() {
        Node[] pips = new Node[0];
    
        switch (dieCount) {
            case 1:
                pips = new Node[]{
                    new Circle(50, 50, 10, pipColor)
                };
                break;
            case 2:
                pips = new Node[]{
                    new Circle(30, 30, 10, pipColor),
                    new Circle(70, 70, 10, pipColor)
                };
                break;
            case 3:
                pips = new Node[]{
                    new Circle(25, 25, 10, pipColor),
                    new Circle(50, 50, 10, pipColor),
                    new Circle(75, 75, 10, pipColor)
                };
                break;
            case 4:
                pips = new Node[]{
                    new Circle(25, 25, 10, pipColor),
                    new Circle(75, 25, 10, pipColor),
                    new Circle(25, 75, 10, pipColor),
                    new Circle(75, 75, 10, pipColor)
                };
                break;
            case 5:
                pips = new Node[]{
                    new Circle(25, 25, 10, pipColor),
                    new Circle(75, 25, 10, pipColor),
                    new Circle(50, 50, 10, pipColor),
                    new Circle(25, 75, 10, pipColor),
                    new Circle(75, 75, 10, pipColor)
                };
                break;
            case 6:
                pips = new Node[]{
                    new Circle(25, 25, 10, pipColor),
                    new Circle(75, 25, 10, pipColor),
                    new Circle(25, 50, 10, pipColor),
                    new Circle(75, 50, 10, pipColor),
                    new Circle(25, 75, 10, pipColor),
                    new Circle(75, 75, 10, pipColor)
                };
                break;
        }
        return pips;
    }
}