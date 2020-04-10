package a8;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class C4Game {

	public static void main(String[] args) {
	
		
/* Create top level window. */
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Connect Four");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Create panel for content. Uses BorderLayout. */
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		main_frame.setContentPane(top_panel);

		/* Create ExampleWidget component and put into center
		 * of content panel.
		 */
		
		C4Widget C4 = new C4Widget();
		top_panel.add(C4, BorderLayout.CENTER);


		/* Pack main frame and make visible. */
		
		main_frame.pack();
		main_frame.setVisible(true);		
	}
	
}
//After cliking a spot call methods to see if c4 has been fulfiled in any of the directions
