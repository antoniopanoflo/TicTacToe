package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class C4Widget extends JPanel implements ActionListener, SpotListener {
	
	private enum Player {RED, BLACK};
	private static final long serialVersionUID = 1L;

	private C4Board _board;		/* SpotBoard playing area. */
	private JLabel _message;		/* Label for messages. */
	private boolean _game_won;		/* Indicates if games was been won already.*/
	private Player _next_to_play;	/* Identifies who has next turn. */
	
	public C4Widget() {
		
		/* Create SpotBoard and message label. */
		
		_board = new C4Board(7,6);
		_message = new JLabel();
		
		/* Set layout and place SpotBoard at center. */
		
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);

		/* Create subpanel for message area and reset button. */
		
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());//

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

		for (Spot s : _board) {
			s.unhighlightSpot();
			s.clearSpot();
			
		}
		
		/* Reset game won and next to play fields */
		_game_won = false;
		_next_to_play = Player.RED;		
		
		/* Display game start message. */
		
		_message.setText("Welcome to Connect Four. Red to play.");
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
		if (_game_won) {
			return;
		}


		/* Set up player and next player name strings,
		 * and player color as local variables to
		 * be used later.
		 */
		
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		
		if (_next_to_play == Player.RED) {
			player_color = Color.RED;
			player_name = "Red";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		} else {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "Red";
			_next_to_play = Player.RED;			
		}
				
	
		
		for (int i = 5 ; i >= 0 ; i--) {
		 if (_board.getSpotAt(s.getSpotX(), i).isEmpty()) {
			 
			// System.out.println(s.getSpotX() + " " + i);
				_board.getSpotAt(s.getSpotX(), i).setSpotColor(player_color);
				_board.getSpotAt(s.getSpotX(), i).toggleSpot();
				_message.setText(next_player_name + " to play");
				break;
		 }
		}
		
		
		for (Spot x : _board) {
			if (x.getSpotX() == s.getSpotX()) {
				x.unhighlightSpot();
				s.highlightSpot();
			}
		}
		
		
		if (_board.isHorizontal() || _board.isVertical() || _board.isDiagonalUpwards()
				|| _board.isDiagonalDownwards()) {
			_message.setText(player_name + " wins!");

			_game_won = true;
			_board.setEnabled(false);
		}
		
	 else if (_board.isC4RunThru()) {
		_message.setText("It's a DRAW!");
		_game_won = true;
		_board.setEnabled(false);
	 }
	}

	@Override
	public void spotEntered(Spot s) {
		/* Highlight spot if game still going on. */
		
		if (_game_won) {
			return;
		}
		
		int s_x = s.getSpotX();
		 
		for (Spot x : _board) {
			if (x.getSpotX() == s_x && x.isEmpty()) {
				x.highlightSpot();
			}
		}
		
		}

	@Override  
	public void spotExited(Spot s) {
		/* Unhighlight spot. */
		
		if (_game_won) {
			return;
		}
		
		
		
		int s_x = s.getSpotX();
		
		for (Spot x : _board) {
			if (x.getSpotX() == s_x) {
				x.unhighlightSpot();
			}
		}
	
		
		}
	
	
	}
	
	
	
