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
- Separated the classes into different packages
- Moved methods from existing classes to new classes to promote single responsibility
  - Brick to Crack
  - Wall to GameSystem
  - HomeMenu to HomeDesign
  - GameBoard to GameDesign
  - GameDesign to PauseMenu
- Split up large methods into smaller methods to promote single responsibility
  - runGame in GameBoard
  - initialise in HomeMenu
  - fontDesign in HomeDesign
- Moved repeated lines of codes to a new method to reduce code duplication
  - moveBall in Ball
  - reset in GameBoard
  - createLevels in Wall
- Methods were renamed to improve clarity on the purpose of the method
  - moveTo to reset in Ball and Player
  - updateBrick to drawCrack in CementBrick
  - makeCrack to setCrackPoints in Crack
  - autoLocate to centerBoard in GameFrame
  - impact to hitBall in Player
  - makeLevels to setUpLevels in Wall
- JavaDocs were created to provide information on the methods and classes
- Super classes were created such as MenuDesign and MidGameDesign to promote encapsulation
- Removed redundant variables and methods

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
