/*
Auther: Damion Shakespear
Date Created: 4/25/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class Player {

    private String namePlayer;
    private final SimpleIntegerProperty scoreKeptSIP = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty scoreHeldSIP = new SimpleIntegerProperty(0);
    private final SimpleStringProperty namePlayerSSP;
    private int scoreKept;
    private int scoreHeld;
    private int FullRun;
    private Color dieColor;
    private Color pipColor;

    public Player(){
        this.namePlayerSSP = new SimpleStringProperty(namePlayer);
    }

    public Player(String namePlayer){
        this.namePlayer = namePlayer;
        this.namePlayerSSP = new SimpleStringProperty(namePlayer);
    }
    public Player(String namePlayer, Color dieColor, Color pipColor){
        this.namePlayer = namePlayer;
        this.namePlayerSSP = new SimpleStringProperty(namePlayer);
        this.dieColor = dieColor;
        this.pipColor = pipColor;
    }

    public void playerRename(String namePlayer){
        this.namePlayer = namePlayer;
        namePlayerSSP.set(namePlayer);
    }
    public String getPlayerName(){
        return namePlayer;
    }
    public SimpleStringProperty getPlayerNameSSP(){
        return namePlayerSSP;
    }

//Score Control
    public void scoreBank() {
        scoreKeptSIP.set(scoreKeptSIP.get() + scoreHeldSIP.get());
        scoreKept += scoreHeld;
    }
    public SimpleIntegerProperty getScoreBankSIP() {
        return scoreKeptSIP;
    }
    public int getScoreBank(){
        return scoreKept;
    }
    public void scoreHold(int hold) {
        scoreHeldSIP.set(scoreHeldSIP.get() + hold);
        scoreHeld += hold;
    }
    public SimpleIntegerProperty getScoreHoldSIP() {
        return scoreHeldSIP;
    }
    public int getScoreHold(){
        return scoreHeld;
    }
    public void clearScoreHold() {
        scoreHeldSIP.set(0);
        scoreHeld = 0;
    }
    public void holdFullRun(int FullRun){
        this.FullRun = FullRun;
    }
    public int getFullRun(){
        return FullRun;
    }
    public void clearFullRun(){
        FullRun = 0;
    }

//Color Choice
    public void setPlayerDieColor(Color dieColor){
        this.dieColor = dieColor;
    }
    public Color getPlayerDieColor(){
        return dieColor;
    }
    public void setPlayerPipColor(Color pipColor){
        this.pipColor = pipColor;
    }
    public Color getPlayerPipColor(){
        return pipColor;
    }
//Disposal
    public void playerDelete() {
    namePlayer = null;
    dieColor = null;
    pipColor = null;
    }
}