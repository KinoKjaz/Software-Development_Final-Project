# Software-Development_Final-Project

## Synopsis
This was my Final Project in my Software Development Class using JavaFX. I chose to make Farkle which is a dice game, however rather than Farkle, I took the liberty of calling it FlipetyGibbitz.

## How to Run
You'll need these files to run the Game, start with FarkleStart.java

![FlipetyGibbitzFiles](https://github.com/user-attachments/assets/f7bb3ec5-6b81-430f-b605-f908f3279e57)

## How to Play
You'll begin in the Menu to add players, each Player can design dice with their choice colors. When ready continue with Start, this will call the prompts for how many Computer Opponents are wanted, and what Score to play to.

![FlipetyGibbitzStartMenu](https://github.com/user-attachments/assets/7a80578a-007d-4de0-a81f-12629e18bc19)

Afterwords the game will begin in the order of Players inputed, then the Computers. There is a green triangle in Top right corner for viewing the Scoreboard, and another attached when toggled to review game rules.

![FlipetyGibbitzGame](https://github.com/user-attachments/assets/cbbfc83f-a1bb-4fc7-b822-e0de0d2da112)

## Code Example
Here is an example of the code for creating and rolling the dice found in the ControlDice.java file.

```
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
