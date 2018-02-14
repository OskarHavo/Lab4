package lab4.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	
	private JButton connectButton,disconnectButton,newGameButton;
	private JLabel messageLabel;
	private JFrame frame;
	private JPanel panel;
	
	private GamePanel gameGridPanel;
	
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		gameGridPanel = new GamePanel(gamestate.getGameGrid());
		
		//jframe
		frame = new JFrame();
		panel = new JPanel();
		frame.add(panel);
		panel.add(gameGridPanel);
		//buttons
		connectButton = new JButton();
		connectButton.setVisible(true);
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		disconnectButton = new JButton();
		disconnectButton.setVisible(true);
		disconnectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		newGameButton = new JButton();
		newGameButton.setVisible(true);
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		panel.add(connectButton);
		panel.add(disconnectButton);
		panel.add(newGameButton);
		
		//label
		messageLabel = new JLabel();
		messageLabel.setVisible(true);
		panel.add(messageLabel);

		
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}
