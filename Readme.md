# Othello

Othello game (Reversi) is a strategy board game for two players, played on an 8×8 uncheckered board. There are 2 players 'O' (light) and 'X' (dark), they take alternate turns to place disks on the board.

'X' goes first and *must* place an 'X' on the board, in such a position that there exists at least one straight (horizontal, vertical, or diagonal) occupied line of 'O's between the new 'X' and another 'X' on the board.

After placing the piece, all 'O's lying on all straight lines between the new 'X' and any existing 'X' are captured (i.e. they turn into 'X's )

Now 'O' plays. 'O' operates under the same rules, with the roles reversed: 'O' places an 'O' on the board in such a position where *at least one* 'X' is captured

Starting position

````text
1 --------
2 --------
3 --------
4 ---OX---
5 ---XO---
6 --------
7 --------
8 --------
  abcdefgh
````

Moves are specified as coordinates. Column+row or row+column (e.g. 3d or d3)

If a player cannot make a valid move (_capturing at least one of the opposing player's pieces along a straight line_), play passes back to the other player.

The game ends when either
 1. neither player can make a valid move
 2. the board is full

The player with the most pieces on the board at the end of the game wins.

For more detail: https://en.wikipedia.org/wiki/Reversi

## Sample output

````text
1 --------
2 --------
3 --------
4 ---OX---
5 ---XO---
6 --------
7 --------
8 --------
  abcdefgh

Player 'X' move: 3d
1 --------
2 --------
3 ---X----
4 ---XX---
5 ---XO---
6 --------
7 --------
8 --------
  abcdefgh

Player 'O' move: c5
1 --------
2 --------
3 ---X----
4 ---XX---
5 --OOO---
6 --------
7 --------
8 --------
  abcdefgh

Player 'X' move: e7
Invalid move. Please try again.

Player 'X' move: e6
1 --------
2 --------
3 ---X----
4 ---XX---
5 --OOX---
6 ----X---
7 --------
8 --------
  abcdefgh

Player 'O' move: 5f
1 --------
2 --------
3 ---X----
4 ---XX---
5 --OOOO--
6 ----X---
7 --------
8 --------
  abcdefgh
````

_Many, many moves later..._

````text
Player 'O' move: a7
1 XXXXXXXX
2 XXXXXXOX
3 XXXXXXXX
4 XXXXXXXX
5 OXXXXXXX
6 OXXXXXXX
7 OOOOXXXX
8 OXXOXXXX
  abcdefgh

No further moves available
Player 'X' wins ( 55 vs 9 )
````

# Project

This project tries to implement such a game. Although the board has a standard dimension 8x8, project allows to configure board length and height in the property file. However due to display limitation, it will assume that columns will not be larger than 26 (a-z). Game starting position is also configurable in the same file.

Player chooses his color in the configuration, as well as the preference to display or not the coordinates of game board. 

Technically, Spring Boot is chosen to ensure a good maintainability. Game input and output are made abstract to support further improvements. A default command line implementation is provided in this project, User can extend _GameInput_ and _GameOutput_ interface to have his own implementations. 

Important methods are tested with JUnit packages, an integration test is done with the help of Spring Boot Test utilities.

