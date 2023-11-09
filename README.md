
# TrekWars

The game is played in a 23*55 game field including walls. There are two competitors: Player (P) and Computer (C). There are some treasures/numbers in the game, which the players collect to increase their scores. The aim of the game is gaining the highest end-game score.

Computer (C) movements were determined inspired by the heat map. Each treasure and player has a certain value, and these values ​​are kept according to their position in the maze in a separate matrix the same size as the maze. These values ​​are divided by 2 and rewritten from their current positions to each of their neighbors. This is done for all locations and then repeated for each treasure or player value. During this iteration process, the values ​​are not reset but added to them. Thus, a value map similar to a heat map is obtained. The computer could moves to the most valuable position according to its current location with this matrix. Thanks to this improvement, the computer does not just chase the player or move randomly. The computer moves to the most optimal positions, which makes it difficult to deal with it.

The game is console-based so jar extension Enigma file is also shared. It must be added to the project for the project to work.

## Description

![TrekWars-project-description-1](https://github.com/tolgamertsaruhan/TrekWars/blob/main/images-for-readme/TrekWars-project-description-1.png)

![TrekWars-project-description-2](https://github.com/tolgamertsaruhan/TrekWars/blob/main/images-for-readme/TrekWars-project-description-2.png)

![TrekWars-project-description-3](https://github.com/tolgamertsaruhan/TrekWars/blob/main/images-for-readme/TrekWars-project-description-3.png)

![TrekWars-project-description-4](https://github.com/tolgamertsaruhan/TrekWars/blob/main/images-for-readme/TrekWars-project-description-4.png)

## Poster

![TrekWars-project-poster](https://github.com/tolgamertsaruhan/TrekWars/blob/main/images-for-readme/TrekWars-project-poster.png)

## Preview

![TrekWars-project-gif](https://github.com/tolgamertsaruhan/TrekWars/blob/main/images-for-readme/TrekWars-project-gif.gif)
