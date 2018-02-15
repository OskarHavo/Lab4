package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int port;
		if (args.length == 1) {
			// argument
			port = Integer.parseInt(args[0]);

		} else {
			// default
			port = 4000;
		}
		GomokuClient client = new GomokuClient(port);
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);

	}

}
