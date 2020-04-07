package a8;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import javax.swing.JPanel;
/*
 * JSpotBoard is a user interface component that implements SpotBoard.
 * 
 * Spot width and spot height are specified to the constructor. 
 * 
 * By default, the spots on the spot board are set up with a checker board pattern
 * for background colors and yellow highlighting.
 * 
 * Uses SpotBoardIterator to implement Iterable<Spot>
 * 
 */

public class TTTBoard extends JPanel implements SpotBoard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 500;
	private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final Color DEFAULT_SPOT_COLOR = Color.BLACK;
	private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

	private static Spot[][] _spots;
	
	public TTTBoard(int width, int height) {
		if (width < 1 || height < 1 || width > 500 || height > 500) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		setLayout(new GridLayout(height, width));
		_spots = new Spot[width][height];
		
		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/width, DEFAULT_SCREEN_HEIGHT/height);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				Color bg = DEFAULT_BACKGROUND_LIGHT;//((x+y)%2 == 0) ? DEFAULT_BACKGROUND_LIGHT : DEFAULT_BACKGROUND_DARK;
				_spots[x][y] = new JSpot(bg, DEFAULT_SPOT_COLOR, DEFAULT_HIGHLIGHT_COLOR, this, x, y);
				((JSpot)_spots[x][y]).setPreferredSize(preferred_size);
				add(((JSpot) _spots[x][y]));
			}			
		}
	}

	// Getters for SpotWidth and SpotHeight properties
	
	
	public int getSpotWidth() {
		return _spots.length;
	}
	
	public int getSpotHeight() {
		return _spots[0].length;
	}

	// Lookup method for Spot at position (x,y)
	
	public Spot getSpotAt(int x, int y) {
		if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates");
		}
		
		return _spots[x][y];
	}
	
	// Convenience methods for (de)registering spot listeners.
	
	@Override
	public void addSpotListener(SpotListener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_spots[x][y].addSpotListener(spot_listener);
			}
		}
	}
	
	@Override
	public void removeSpotListener(SpotListener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_spots[x][y].removeSpotListener(spot_listener);
			}
		}
	}

	@Override
	public Iterator<Spot> iterator() {
		return new SpotBoardIterator(this);
	}
	
	public boolean isTicTacRunThru() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (_spots[i][j].isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public boolean testcrossDownwards() {
		return _spots[0][0].getSpotColor() == _spots[1][1].getSpotColor()
				&& _spots[1][1].getSpotColor() == _spots[2][2].getSpotColor()
						&& _spots[0][0].isEmpty() == false
						&& _spots[1][1].isEmpty() == false && _spots[2][2].isEmpty() == false;
	}
	public boolean testcrossUpwards() {
		return _spots[0][2].getSpotColor() == _spots[1][1].getSpotColor()
				&& _spots[1][1].getSpotColor() == _spots[2][0].getSpotColor()
						&& _spots[0][2].isEmpty() == false
						&& _spots[1][1].isEmpty() == false && _spots[2][0].isEmpty() == false;
	}
	public boolean testTopRow() {
		return _spots[0][0].getSpotColor() == _spots[1][0].getSpotColor()
				&& _spots[1][0].getSpotColor() == _spots[2][0].getSpotColor()
						&& _spots[0][0].isEmpty() == false
						&& _spots[1][0].isEmpty() == false && _spots[2][0].isEmpty() == false;
	}
	public boolean testMiddleRow() {
		return _spots[0][1].getSpotColor() == _spots[1][1].getSpotColor()
				&& _spots[1][1].getSpotColor() == _spots[2][1].getSpotColor()
						&& _spots[0][1].isEmpty() == false
						&& _spots[1][1].isEmpty() == false && _spots[2][1].isEmpty() == false;
	}
	public boolean testBottomRow() {
		return _spots[0][2].getSpotColor() == _spots[1][2].getSpotColor()
				&& _spots[1][2].getSpotColor() == _spots[2][2].getSpotColor()
						&& _spots[0][2].isEmpty() == false
						&& _spots[1][2].isEmpty() == false && _spots[2][2].isEmpty() == false;
	}
	public boolean testLeftColumn() {
		return _spots[0][0].getSpotColor() == _spots[0][1].getSpotColor()
				&& _spots[0][1].getSpotColor() == _spots[0][2].getSpotColor()
						&& _spots[0][0].isEmpty() == false
						&& _spots[0][1].isEmpty() == false && _spots[0][2].isEmpty() == false;
	}
	public boolean testMiddleColumn() {
		return _spots[1][0].getSpotColor() == _spots[1][1].getSpotColor()
				&& _spots[1][1].getSpotColor() == _spots[1][2].getSpotColor()
						&& _spots[1][0].isEmpty() == false
						&& _spots[1][1].isEmpty() == false && _spots[1][2].isEmpty() == false;
	}
	public boolean testRightColumn() {
		return _spots[2][0].getSpotColor() == _spots[2][1].getSpotColor()
				&& _spots[2][1].getSpotColor() == _spots[2][2].getSpotColor()
						&& _spots[2][0].isEmpty() == false
						&& _spots[2][1].isEmpty() == false && _spots[2][2].isEmpty() == false;
	}

}
