/*
Auther: Damion Shakespear
Date Created: 4/26/2025

This is my Final Project for the class Software Development, Farkle is what I chose to build.
 */
import javafx.scene.paint.Color;

public class CompOpponent extends Player {

    public CompOpponent() {
        super("Computer");
    }
    public CompOpponent(int i, Color dieColor, Color pipColor) {
        super("Computer " + i, dieColor, pipColor);
    }
}
