
	import java.awt.BorderLayout;
	import javax.swing.JFrame;
	import javax.swing.JPanel;


public class TTTGame {
	

		public static void main(String[] args) {
			
			/* Create top level window. */
			
			JFrame mainFrame = new JFrame();
			mainFrame.setTitle("TicTacToe");
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//mainFrame.setBackground(BLUE);

			/* Create panel for content. Uses BorderLayout. */
			JPanel top_panel = new JPanel();
			top_panel.setLayout(new BorderLayout());
			mainFrame.setContentPane(top_panel);

			/* Create TicTacToeWidget component and put into center
			 * of content panel.
			 */
			
			TicTacToeWidget ttt = new TicTacToeWidget();
			top_panel.add(ttt, BorderLayout.CENTER);


			/* Pack main frame and make visible. */
			
			mainFrame.pack();
			mainFrame.setVisible(true);		

		}
	
}

