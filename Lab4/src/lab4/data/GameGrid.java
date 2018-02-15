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
	 * @param player
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
	 * Itererar inom grid[][] och ställer allt till EMPTY.
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
	 * @param player
	 *            the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {
		int counter = 0;
		int range = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				/*
				 * Medans INROW > range; Rutan x och range stycken rutor och höger inte
				 * överskrider radlängden; Och rutan i (x + range++) och y tillhör spelaren.
				 * 
				 * Då får du en poäng och counter ökar. Är counter == INROW, returna true,
				 * annars nollställ counter & range och gå till den vertikala kontrollen osv.
				 */

				while (range < INROW && x + range < this.size && getLocation((x + range++), y) == player) { // Höger
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				// Måste återställas efter varje while loop.
				counter = 0;
				range = 0;

				while (range < INROW && y + range < this.size && getLocation(x, (y + range++)) == player) { // Uppåt
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				counter = 0;
				range = 0;

				while (x + range < this.size && y + range < this.size
						&& getLocation(x + range, (y + range++)) == player) { // Nordöst
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				counter = 0;
				range = 0;

				int rangeY = 0;
				while (x + range < this.size && y - range >= 0 && getLocation((x + range++), y - rangeY++) == player) {// Sydöst
					counter++;
					if (counter == INROW) {
						return true;
					}
				}
				counter = 0;
				range = 0;

			}

		}
		return false;
	}

}
