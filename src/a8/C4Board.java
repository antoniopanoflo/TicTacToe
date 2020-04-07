package a8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JPanel;

public class C4Board extends JPanel implements SpotBoard {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 500;
	private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f);
	private static final Color DEFAULT_SPOT_COLOR = Color.BLACK;
	private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

	private static Spot[][] _spots;
	
	public C4Board(int width, int height) {
		if (width < 1 || height < 1 || width > 500 || height > 500) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		setLayout(new GridLayout(height, width));
		_spots = new Spot[width][height];
		
		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/width, DEFAULT_SCREEN_HEIGHT/height);
		
		//Might have to redo this cuz I messed it up right here.copy paste this agian from JSpotBoard.
		
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				if ((x%2) == 0) {
				Color bg = DEFAULT_BACKGROUND_DARK;
				} else { Color bg = DEFAULT_BACKGROUND_LIGHT; }
				
				
				Color bg = null;
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
}