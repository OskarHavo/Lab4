package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer {

	private final int UNIT_SIZE = 20;
	private GameGrid grid;

	/**
	 * The constructor
	 * 
	 * @param grid
	 *            The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid) {
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize() * UNIT_SIZE + 1, grid.getSize() * UNIT_SIZE + 1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates of the panel
	 * 
	 * @param x
	 *            the x coordinates
	 * @param y
	 *            the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y) {
		// returnerar kvoten mellan x och y coordinat och UNIT_SIZE
		return new int[] { x / this.UNIT_SIZE, y / this.UNIT_SIZE };
	}

	/**
	 * @param arg0
	 * 
	 * @param arg1
	 */
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}

	/**
	 * Ritar ut brädet och även symbolerna för spelar- resp motståndarrutorna.
	 * 
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Itererar igenom och ritar ut rektanglar.
		for (int x = 0; x < this.grid.getSize(); x++) {
			for (int y = 0; y < this.grid.getSize(); y++) {
				g.drawRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

				/*
				 * Olika färger för spelare 1 och 2. Crosses and circles
				 */
				if (grid.getLocation(x, y) == grid.ME) {
					g.setColor(Color.BLUE);
					g.drawOval(UNIT_SIZE * x + 1, UNIT_SIZE * y + 1, UNIT_SIZE - 2, UNIT_SIZE - 2);
					// g.drawRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

				} else if (grid.getLocation(x, y) == grid.OTHER) {
					g.setColor(Color.RED);
					g.drawLine(UNIT_SIZE * x, UNIT_SIZE * y, UNIT_SIZE * x + x, y * UNIT_SIZE + y);
					g.drawLine(UNIT_SIZE * x, y * UNIT_SIZE + y, UNIT_SIZE * x + x, UNIT_SIZE * y);
					// g.drawRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

				}

			}

		}
		this.repaint();
	}

}
