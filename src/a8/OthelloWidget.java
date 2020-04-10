package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OthelloWidget  extends JPanel implements ActionListener, SpotListener {

		private enum Player {WHITE, BLACK};
		private static final long serialVersionUID = 1L;

		private OthelloBoard _board;		/* SpotBoard playing area. */
		private JLabel _message;		/* Label for messages. */
		private boolean _game_won;		/* Indicates if games was been won already.*/
		private Player _next_to_play;	/* Identifies who has next turn. */
		private boolean _tie;
		
		public OthelloWidget() {
			
			/* Create SpotBoard and message label. */
			
			_board = new OthelloBoard(8,8);
			_message = new JLabel();
			
			/* Set layout and place SpotBoard at center. */
			
			setLayout(new BorderLayout());
			add(_board, BorderLayout.CENTER);

			/* Create subpanel for message area and reset button. */
			
			JPanel reset_message_panel = new JPanel();
			reset_message_panel.setLayout(new BorderLayout());

			/* Reset button. Add ourselves as the action listener. */
			
			JButton reset_button = new JButton("Restart");
			reset_button.addActionListener(this);
			reset_message_panel.add(reset_button, BorderLayout.EAST);
			reset_message_panel.add(_message, BorderLayout.CENTER);

			/* Add subpanel in south area of layout. */
			
			add(reset_message_panel, BorderLayout.SOUTH);

			/* Add ourselves as a spot listener for all of the
			 * spots on the spot board.
			 */
			_board.addSpotListener(this);

			/* Reset game. */
			resetGame();
		}

		/* resetGame
		 * 
		 * Resets the game by clearing all the spots on the board,
		 * picking a new secret spot, resetting game status fields, 
		 * and displaying start message.
		 * 
		 */

		private void resetGame() {
			/* Clear all spots on board. Uses the fact that SpotBoard
			 * implements Iterable<Spot> to do this in a for-each loop.
			 */
			
			for (Spot s : _board)
			{
				s.clearSpot();
				s.setSpotColor(Color.LIGHT_GRAY);
				_game_won = false;
				_tie = false;
				_next_to_play = Player.BLACK;
				_message.setText("Welcome to Othello. Black shall go first!");
		
			}
			
//
//			for (Spot s : _board) {
//				s.clearSpot();
//			}

			/* Reset the background of the old secret spot.
			 * Check _secret_spot for null first because call to 
			 * resetGame from constructor won't have a secret spot 
			 * chosen yet.
			 */
			_board.getSpotAt(3, 3).setSpotColor(Color.WHITE);
			_board.getSpotAt(3, 3).toggleSpot();
			_board.getSpotAt(3, 4).setSpotColor(Color.BLACK);
			_board.getSpotAt(3, 4).toggleSpot();
			_board.getSpotAt(4, 3).setSpotColor(Color.BLACK);
			_board.getSpotAt(4, 3).toggleSpot();
			_board.getSpotAt(4, 4).setSpotColor(Color.WHITE);
			_board.getSpotAt(4, 4).toggleSpot();

			
		
			/* Reset game won and next to play fields */
			_game_won = false;
			_next_to_play = Player.WHITE;		
			
			/* Display game start message. */
			
			_message.setText("Welcome to Othello! White to play.");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			/* Handles reset game button. Simply reset the game. */
					resetGame();
		}

		/* Implementation of SpotListener below. Implements game
		 * logic as responses to enter/exit/click on spots.
		 */
		

		
		
		
	@Override
	public void spotClicked(Spot s) {

		/* If game already won, do nothing. */
		if (_game_won || _tie || !highlightSpot(s)
				|| s.getSpotColor() == Color.BLACK || s.getSpotColor() == Color.WHITE) {
			return;
		}

		String next_player_name = null;
		Color player_color = null;

		if (_next_to_play == Player.BLACK) {
			player_color = Color.BLACK;
			// next_player_name = "White";
			// _next_to_play = Player.WHITE;
		} else {
			player_color = Color.WHITE;
			// next_player_name = "Black";
			// _next_to_play = Player.BLACK;
		}

		if (highlightSpot(s)) {
			validSpot(s);
			s.setSpotColor(player_color);
			s.toggleSpot();

			if (player_color == Color.BLACK) {
				next_player_name = "White";
				_next_to_play = Player.WHITE;
			} else {
				next_player_name = "Black";
				_next_to_play = Player.BLACK;
			}
			_message.setText(next_player_name + " to play.");
		}

		if (isOthelloRunThru()) {
			isWinner();
		}

		else if (noValidMoves() && !isOthelloRunThru()) {
//			if (player_color == Color.BLACK) {
//				// player_color = Color.WHITE
//				next_player_name = "White";
//				_next_to_play = Player.WHITE;
//			} else {
//				next_player_name = "Black";
//				_next_to_play = Player.BLACK;
			}
			_message.setText(next_player_name + " to play.");

			if (noValidMoves()) {
				isWinner();
			} else {
				return;
			}
		}

		@Override
		public void spotEntered(Spot s) {
			/* Highlight spot if game still going on. */
			
			if (_game_won) {
				return;
			}
			s.highlightSpot();
		}

		@Override
		public void spotExited(Spot s) {
			/* Unhighlight spot. */
			
			s.unhighlightSpot();
		}
		
		
		
		
		
		
		private void isWinner() {
			int i = 0; //black spots
			int j = 0; //white spots
			
			for (Spot s : _board) {
				if (s.getSpotColor() == Color.BLACK) {
					i++;
				}
				
				else if (s.getSpotColor() == Color.WHITE) {
					j++;
				}
			}
			
			if (i > j) {
				_message.setText("Black Wins! Score: " + i + " to " + j);
			} else if (j > i) {
				_message.setText("White Wins! Score: " + j + " to " + i);
			} else {
				_message.setText("The game is a DRAW");
			}
		}
		
		private boolean isOthelloRunThru() { //Review this.
			int pieces = 0;
			for (Spot n : _board) {
				if (n.getSpotColor() == Color.BLACK || n.getSpotColor() == Color.WHITE) {
					pieces++;
				}
				if (pieces == 64) {
					return true;
				} 
			}
			
			return false;
		}
		
		private boolean noValidMoves() {
			int i = 0; //# of available empty spots on the board.
			int j = 0; //# of the spots that aren't highlightable.
			for (Spot s : _board) {
				if (s.getSpotColor() == Color.LIGHT_GRAY) {
					i++;
					if (!highlightSpot(s)) {  //find this.
						j++;
					}
				}
			}
			
			if (i == j) return true;
			else return false;
		}
		
	private boolean highlightSpot(Spot spot) { // Review the initiating as ints.
		int coordX = spot.getSpotX();
		int coordY = spot.getSpotY();
		Color spotColor;
		Color spotSearchColor;
		if (_next_to_play == Player.BLACK) {
			spotSearchColor = Color.WHITE;
			spotColor = Color.BLACK;
		} else {
			spotSearchColor = Color.BLACK;
			spotColor = Color.WHITE;
		}

		// Checking for horizontal

		if (spot.getSpotColor() == Color.BLACK || spot.getSpotColor() == Color.WHITE) {
			return false;
		}

		if ((coordX - 1 >= 0) && (_board.getSpotAt(coordX - 1, coordY).getSpotColor() == spotSearchColor)) {
			for (int i = coordX - 1; i >= 0; i--) {
				if (_board.getSpotAt(i, coordY).getSpotColor() == spotColor) {
					return true;
				}

				else if (_board.getSpotAt(i, coordY).getSpotColor() == Color.LIGHT_GRAY) {
					break;
				}
			}
		}


		// test vertically

		if ((coordY - 1 >= 0) && (_board.getSpotAt(coordX, coordY - 1).getSpotColor() == spotSearchColor)) {
			for (int i = coordY - 1; i >= 0; i--) {
				if (_board.getSpotAt(coordX, i).getSpotColor() == spotColor) {
					return true;
				} else if (_board.getSpotAt(coordX, i).getSpotColor() == Color.LIGHT_GRAY) {
					break;
				}
			}
		}

		else if ((coordY + 1 <= 7) && (_board.getSpotAt(coordX, coordY + 1).getSpotColor() == spotSearchColor)) {
			for (int i = coordY + 1; i <= 7; i++) {
				if (_board.getSpotAt(coordX, i).getSpotColor() == spotColor) {
					return true;
				} else if (_board.getSpotAt(coordX, i).getSpotColor() == Color.LIGHT_GRAY) {
					break;
				}
			}
		}

		else return false;

		return false;
	}
		
		
		
	private void validSpot(Spot spot) {
		int coordX = spot.getSpotX();
		int coordY = spot.getSpotY();
		Color spotColor;
		Color spotSearchColor;
		if (_next_to_play == Player.BLACK) {
			spotSearchColor = Color.WHITE;
			spotColor = Color.BLACK;
		} else {
			spotSearchColor = Color.BLACK;
			spotColor = Color.WHITE;
		}

		// test horizontally

		if (spot.getSpotColor() == Color.BLACK || spot.getSpotColor() == Color.WHITE) {
			return;
		}

		if ((coordX - 1 >= 0) && (_board.getSpotAt(coordX - 1, coordY).getSpotColor() == spotSearchColor)) {
			for (int i = coordX - 1; i >= 0; i--) {
				if (_board.getSpotAt(i, coordY).getSpotColor() == spotColor) {
					for (int j = i; j < coordX; j++) {
						_board.getSpotAt(j, coordY).setSpotColor(spotColor);
					}
					break;
				}

				else if (_board.getSpotAt(i, coordY).getSpotColor() == Color.LIGHT_GRAY) {
					break;
				}
			}
		}

		else if ((coordX + 1 <= 7) && (_board.getSpotAt(coordX + 1, coordY).getSpotColor() == spotSearchColor)) {
			for (int i = coordX + 1; i <= 7; i++) {
				if (_board.getSpotAt(i, coordY).getSpotColor() == spotColor) {
					for (int j = i; j > coordX; j--) {
						_board.getSpotAt(j, coordY).setSpotColor(spotColor);
					}
					break;
				}

				else if (_board.getSpotAt(i, coordY).getSpotColor() == Color.LIGHT_GRAY) {
					break;
				}
			}
		}

		// vertica check

		if ((coordY - 1 >= 0) && (_board.getSpotAt(coordX, coordY - 1).getSpotColor() == spotSearchColor)) {
			for (int i = coordY - 1; i >= 0; i--) {
				if (_board.getSpotAt(coordX, i).getSpotColor() == spotColor) {
					for (int j = i; j < coordY; j++) {
						_board.getSpotAt(coordX, j).setSpotColor(spotColor);
					}
					break;
				}

				else if (_board.getSpotAt(coordX, i).getSpotColor() == Color.LIGHT_GRAY) {
					break;
				}
			}
		}

		else if ((coordY + 1 <= 7) && (_board.getSpotAt(coordX, coordY + 1).getSpotColor() == spotSearchColor)) {
			for (int i = coordY + 1; i <= 7; i++) {
				if (_board.getSpotAt(coordX, i).getSpotColor() == spotColor) {
					for (int j = i; j > coordY; j--) {
						_board.getSpotAt(coordX, j).setSpotColor(spotColor);
					}
					break;
				}

				else if (_board.getSpotAt(coordX, i).getSpotColor() == Color.LIGHT_GRAY) {
					break;
				}
			}
		}


		
	
		if ((coordX - 1 >= 0) && (coordY + 1 <= 7)
				&& _board.getSpotAt(coordX - 1, coordY + 1).getSpotColor() == spotSearchColor) {
			for (int i = 1; i < 8; i++) {
				if ((coordX - i >= 0) && (coordY + i <= 7)
						&& (_board.getSpotAt(coordX - i, coordY + i).getSpotColor() == spotColor)) {
					for (int j = i; j >= 0; j--) {
						_board.getSpotAt(coordX - j, coordY + j).setSpotColor(spotColor);
					}
					break;
				} else if ((coordX - i >= 0) && (coordY + i <= 7)
						&& (_board.getSpotAt(coordX - i, coordY + i).getSpotColor() == Color.LIGHT_GRAY)) {
					break;
				}
			}
		}

		return;
	}
	
	
}
