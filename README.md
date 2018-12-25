# ChessApplication
The traditional 2-player chess game.

Implemented algorithms for all the general rules of chess, as well as some special features, such as highlighting
potential move tiles, flipping the board, allowing pawn promotions, and keeping track of eliminated pieces for both players.

## Overall Design 
Model / View / Controller Design Pattern

### Program Hierarchy

#### Package pieces
Handles the logic for all the pieces in a game of chess.

(Interface) Piece
  (Abstact Class) Abstract Piece implements Piece
    (Class) Pawn
    (Class) Knight
    (Class) Bishop
    (Class) Rook
    (Class) Queen
    (Class) King 

(Enum) PieceColor 
  WHITE, BLACK
  
(Enum) MoveDir 
  LEFT, RIGHT, 
	FORWARD, BACKWARD, 
	DIAGONALLY_LEFT_FORWARD, 
	DIAGONALLY_RIGHT_FORWARD, 
	DIAGONALLY_LEFT_BACKWARD, 
	DIAGONALLY_RIGHT_BACKWARD

#### Package chessboard
Represents the model (i.e. the logic of the board, which is an 8x8 grid of tiles).

(Class) BoardSimulator
(Class) Tile
 
#### Package chessApplication
Represents the View and the Controller (i.e. the GUI and the event handlers to process moves)

(Class) ChessController
(Class) ChessGrid
(Class) ChessApplicationLauncher
(GUI from SceneBuilder) ChessApplicationUI.fxml

#### Package main
Launches the game

(Class) MainChessAppLauncher

  
  

