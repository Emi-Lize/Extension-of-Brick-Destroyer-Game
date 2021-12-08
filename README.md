# COMP2042_CW_hcyla2

### Table of Contents
1. [Game Information](https://github.com/Emi-Lize/COMP2042_CW_hcyla2#game-information)
2. [Controls](https://github.com/Emi-Lize/COMP2042_CW_hcyla2#controls)
3. [Maintenance](https://github.com/Emi-Lize/COMP2042_CW_hcyla2#maintenance)
4. [Extension](https://github.com/Emi-Lize/COMP2042_CW_hcyla2#extension)

## Game Information
A brick destroyer game with 5 levels. To complete a level, all bricks on the screen must be broken. Each level consists of different types of bricks which have different properties. The last level has small yellow squares known as power-ups which increase the chances of breaking the bricks in the level. *Original source code from https://github.com/FilippoRanza/Brick_Destroy.*

## Controls
- A - Move Left
- D - Move Right
- SPACE – Start/Pause the Game
- ESC – Enter/Exit the Pause Menu

## Maintenance
- Moved Crack class from Brick class to its own class as there were two classes in an abstract class
- Created a super class called MenuDesign for starting screen related designs to promote encapsulation
- Created a super class called MidGameDesign for mid-game screen related designs to promote encapsulation
- Moved code related to the operation of the game from Wall to a new class called GameSystem to promote single responsibility
- Moved code related to the design of the home menu from HomeMenu to a new class called HomeDesign to promote single responsibility
- Moved code related to the design of the game from GameBoard to a new class called GameDesign to promote single responsibility
- Moved code related to the pause menu from GameDesign to a new class called PauseMenu to promote single responsibility
- Separated the classes into different packages

## Extension
- Added scores to the game which records how long the player takes to complete the game by using HighScore and ScoreDesign class
- The time taken by the player and the top 5 high scores of the level is displayed after each level is completed
- If the player beats one of the top 5 scores, their score will replace that score and be saved to the score text file
- “NEW HIGHSCORE!” will be displayed next to the score of the player in the list of high scores if the player’s score has been added to it
- Added a new level which consists of a new brick type called Magic Brick and Power-Ups by using MagicBrick and PowerUp class
- Magic Brick has a strength of two and a probability to have its strength reduced when the ball hits it
- Power-Ups increase the probability of magic brick’s strength reducing by 0.05 for each power-up
- There are only 10 power-ups available in the last level
- Reduced the width of the player bar in the new level by one-third
- Added an info screen to provide information about the game and the game controls by using Info and InfoDesign class
- Added a background image to the home menu and info screen by using BackgroundImage class
