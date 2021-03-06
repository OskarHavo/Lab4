package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	public static final int INROW = 5;

	private int grid[][];
	private int size = 0;

	/**
	 * Constructor
	 * 
	 * @param size
	 *            The width/height of the game grid
	 */
	public GameGrid(int size) {
		this.grid = new int[size][size];
		this.size = size;
		initGrid();

	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y) {

		return this.grid[x][y];
	}

	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param player the player to move
	 * 
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {
		// Upptagen ruta? return false.
		if (grid[x][y] != EMPTY) {
			return false;
		}
		grid[x][y] = player;
		setChanged();
		notifyObservers();
		return true;

	}

	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		initGrid();
		setChanged();
		notifyObservers();

	}

	/**
	 * Itererar inom grid[][] och st�ller allt till EMPTY.
	 */

	private void initGrid() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				grid[x][y] = EMPTY;

			}
		}

	}

	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 *            
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {
		int counter = 0;
		int next = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				/*
				 * Medans INROW > range; Rutan x och range stycken rutor och h�ger inte
				 * �verskrider radl�ngden; Och rutan i (x + range++) och y tillh�r spelaren.
				 * 
				 * D� f�r du en po�ng och counter �kar. �r counter == INROW, returna true,
				 * annars nollst�ll counter & range och g� till den vertikala kontrollen osv.
				 */

				while (next < INROW && x + next < this.size && getLocation((x + next++), y) == player) { // H�ger
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				// M�ste �terst�llas efter varje while loop.
				counter = 0;
				next = 0;

				while (next < INROW && y + next < this.size && getLocation(x, (y + next++)) == player) { // Upp�t
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				counter = 0;
				next = 0;

				while (x + next < this.size && y + next < this.size
						&& getLocation(x + next, (y + next++)) == player) { // Nord�st
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				counter = 0;
				next = 0;

				int rangeY = 0;
				while (x + next < this.size && y - next >= 0 && getLocation((x + next++), y - rangeY++) == player) {// Syd�st
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				counter = 0;
				next = 0;

			}

		}
		return false;
	}

}
