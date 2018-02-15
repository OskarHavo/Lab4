/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;
	
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		if(currentState == MY_TURN) {
			
			if (getGameGrid().move(x, y, 1)) {
				message = "Du gjorde ett drag";
				client.sendMoveMessage(x, y);
				currentState = 2;
				if(getGameGrid().isWinner(1)) {
					message = "Du vann!";
					currentState = 3;
				}
			} else {
				message = "Så kan du inte göra!";
			}
			
		} else if (currentState == NOT_STARTED){
			message = "Spelet inte startat";
		} else {
			message = "Det är inte din tur!";
		}
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		getGameGrid().clearGrid();
		currentState = OTHER_TURN;
		message = "Du har utmanat en annan spelare.";
		client.sendNewGameMessage();
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		getGameGrid().clearGrid();
		currentState = MY_TURN;
		message = "Du har blivit utmanad. Din tur!";
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		getGameGrid().clearGrid();
		currentState = NOT_STARTED;
		message = "Player disconnected!";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		getGameGrid().clearGrid();
		currentState = NOT_STARTED;
		message = "Player disconnected";
		setChanged();
		notifyObservers();
		client.disconnect();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		if(currentState == OTHER_TURN) {
			
			if (getGameGrid().move(x, y, 2)) {
				message = "Din motståndare gjorde ett drag";
				currentState = 1;
				if(getGameGrid().isWinner(1)) {
					message = "Du förlorade!";
					currentState = 3;
				}
			}
			
		} else if (currentState == NOT_STARTED){
			message = "Spelet inte startat";
		} else {
			message = "Det är inte din tur!";
		}
		setChanged();
		notifyObservers();
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();	
	}
	
}
