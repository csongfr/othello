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

This project trys implement such a game. Although the board has a standard dimension 8x8, project allows to configure board length and height in the property file. The initial 4 disks will be placed in the middle of the board. However due to display limitation, it will assume that columns will not be larger than 26 (a-z).

Player can also choose his color in the configuration, as well as the preference to display or not the coordinates of game board. 

Technically, Spring Boot is chosen to ensure a good maintainability. Game input and output are made abstract to support further improvement, currently only contains command line implementation, but will be able to extend easily. Unit tests are performed with original JUnit packages, and an integration test is done with Spring Boot Test utilities.

The algorithm is quite basic in the first version, might have way to get a better time complexity, but will probably downgrade the code readability.
