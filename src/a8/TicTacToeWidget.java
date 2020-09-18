
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {
	
	private static final long serialVersionUID = 1L;

	private enum Player {BLACK, WHITE}

//	Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
//	Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f);
//	Color DEFAULT_BACKGROUND_NADA = new Color(0.1f, 0.1f, 0.1f);

	

	private TTTBoard _board;/* SpotBoard playing area. */
	private JLabel _message;/* Label for messages. */
	private boolean _game_won;/* Indicates if games was been won already. */
	private Player _next_to_play;/* Identifies who has next turn. */
	//private Color background;

	public TicTacToeWidget() {

		/* Create SpotBoard and message label. */

		_board = new TTTBoard(3, 3);
		_message = new JLabel();

		/* Set layout and place SpotBoard at center. */

		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);
		//_board.setBackground(DEFAULT_BACKGROUND_NADA);

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

		/*
		 * Add ourselves as a spot listener for all of the spots on the spot board.
		 */
		_board.addSpotListener(this);

		/* Reset game. */
		resetGame();
	}

	private void resetGame() {
		/*
		 * Clear all spots on board. Uses the fact that SpotBoard implements
		 * Iterable<Spot> to do this in a for-each loop.
		 */

		for (Spot s : _board) {
			s.clearSpot();
			//s.removeSpotListener(l);
		}

		/* Reset game won and next to play fields */
		_game_won = false;
		_next_to_play = Player.WHITE;

		/* Display game start message. */

		_message.setText("Welcome to the TicTacToe! White to play.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Handles reset game button. Simply reset the game. */
		resetGame();
	}

	
	@Override
	public void spotClicked(Spot s) {

		/* If game already won, do nothing. */
		if (_game_won) {
			return;
		}

		/*
		 * Set up player and next player name strings, and player color as local
		 * variables to be used later.
		 */
		
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;


		if (_next_to_play == Player.BLACK) {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "White";
			_next_to_play = Player.WHITE;
		} else {
			player_color = Color.WHITE;
			player_name = "White";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		}

		/* Set color of spot clicked and toggle. */
		s.setSpotColor(player_color);
		s.toggleSpot();
		
		
		/*Check if spot is three in a row*/		
		
		if (_board.testcrossDownwards() || _board.testcrossUpwards() || _board.testTopRow() || _board.testMiddleRow() || _board.testBottomRow() ||
				_board.testLeftColumn() || _board.testMiddleColumn() || _board.testRightColumn()) {
			
			_message.setText(player_name + " wins!");
			_game_won = true;
			_board.setEnabled(false);
			
		} else if (_board.isTicTacRunThru()) {
			_message.setText("It's a DRAW!");
			_game_won = true;
			_board.setEnabled(false);

		} else {
		_message.setText("You clicked on " + s.getCoordString() +". " + next_player_name + " to play.");
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
	public void spotExited(Spot n) {
		/* Unhighlight spot. */

		n.unhighlightSpot();
	}

}
