Interfaces for a Spot and a 2D field of Spots called SpotBoard:
* Spot
* SpotBoard

Those interfaces are implemented as Java Swing components that extend JPanel by:
* JSpot
* JSpotBoard

The SpotListener interface can be used to observer enter/exit/click events on a Spot.

ExampleWidet is a user interface widget that implements a simple game where two players
take turns trying to find a secret spot. ExampleGame simply sets up a top-level window 
with a single ExampleWidget component in its layout. In this game, a blue player
and a green player take turns either setting or clearing spots on the board. The goal is to
find the secret spot. When one of them does, the game is over and it calculates a "score". The
score is simply the number of spots on the board plus one for every spot that is set to your
opponent's color and minus one for every spot on the board that is set to your color. 

# TIC TAC TOE

A TicTacToe game where the main game class is called `TicTacToeGame`
and the widget class `TicTacToeWidget`. The game has the following features:

* Players are black and white.
* Background of board is uniform.
* Spots are highlighted when entered only if clicking on them is a legal move (i.e., spot not already selected).
* Start of game should have welcome message and indicate that white goes first.
* After a game winning move, message should indicate who won and spot highlighting should stop.
* After a game drawing move, message should indicate that game is a draw.
* After a move that neither wins or draws, message should indicate who goes next.

