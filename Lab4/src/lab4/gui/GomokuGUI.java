package lab4.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class GomokuGUI implements Observer {

	private GomokuClient client;
	private GomokuGameState gamestate;

	private JButton connectButton, disconnectButton, newGameButton;
	private JLabel messageLabel;
	private JFrame frame;
	private JPanel panel;
	private GamePanel gameGridPanel;

	/**
	 * The constructor
	 * 
	 * @param g
	 *            The game state that the GUI will visualize
	 * @param c
	 *            The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c) {

		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);

		// jframe
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(325, 400)); // Storlek så att layouten ser ut som på bilden.
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Grid och dess mouselistener.
		gameGridPanel = new GamePanel(this.gamestate.getGameGrid());
		this.gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int[] mousePosition = gameGridPanel.getGridPosition(e.getX(), e.getY());
				gamestate.move(mousePosition[0], mousePosition[1]); // [0] x coord ; [1] y coord
			}
		});

		// buttons
		connectButton = new JButton("Connect");
		connectButton.setVisible(true);
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ConnectionWindow cw = new ConnectionWindow(client);

			}

		});
		newGameButton = new JButton("New game");
		newGameButton.setVisible(true);
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gamestate.newGame();

			}

		});

		disconnectButton = new JButton("Disconnect");
		disconnectButton.setVisible(true);
		disconnectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gamestate.disconnect();

			}

		});

		// label
		messageLabel = new JLabel("Welcome to Gomoku!");
		messageLabel.setVisible(true);

		// jpanel
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		panel.add(gameGridPanel);
		panel.add(connectButton);
		panel.add(newGameButton);
		panel.add(disconnectButton);
		panel.add(messageLabel);

		frame.add(panel);

		frame.pack();
		gameGridPanel.setVisible(true);
		panel.setVisible(true);
		frame.setVisible(true);

	}

	public void update(Observable arg0, Object arg1) {

		// Update the buttons if the connection status has changed
		if (arg0 == client) {
			if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			} else {
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}

		// Update the status text if the gamestate has changed
		if (arg0 == gamestate) {
			messageLabel.setText(gamestate.getMessageString());
		}

	}

}
