package ca.isenor.pokemontcg.networking.server;
import java.io.IOException;

/**
 * Serves as a backup for when the ServerPlayerThread is waiting for its turn.
 * @author dawud
 *
 */
public class ServerInputThread extends Thread {
	private PlayerTurnController controller;
	private int playerNumber;

	public ServerInputThread(PlayerTurnController controller, int playerNumber) {
		this.controller = controller;
		this.playerNumber = playerNumber;
	}

	@Override
	public void run() {
		ServerPlayerThread thisPlayer;
		ServerPlayerThread otherPlayer;

		// Deduce relative players
		if (playerNumber == 0) {
			thisPlayer = controller.getPlayerThread(0);
			otherPlayer = controller.getPlayerThread(1);
		}
		else {//if (playerNumber == 1) {
			thisPlayer = controller.getPlayerThread(1);
			otherPlayer = controller.getPlayerThread(0);
		}
		String message;
		try {
			boolean finished = false;
			while (!finished && (message = thisPlayer.getIn().readLine()) != null) {
				System.out.println("Player" + playerNumber + ": \"" + message + "\"");
				if (message.startsWith("chat")) {
					otherPlayer.getOut().println(controller.getPlayer(playerNumber).getName() + ": " + message.substring(5));
				}
				else if (message.startsWith("quit")) {
					otherPlayer.getOut().println(controller.getPlayer(playerNumber).getName() + " has left the game.");
					finished = true;
					System.out.println("Ending server input thread for Player" + playerNumber);
				}
				else if ("hand".equals(message)) {
					thisPlayer.getOut().println(controller.getPlayer(playerNumber).getHand());
					System.out.println("Sent hand to Player" + playerNumber);
				}
				else if (controller.getPlayerTurn() == playerNumber) {
					System.out.println("Command received on player"+playerNumber+"'s turn.");
					thisPlayer.setCmd(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		thisPlayer.getOut().println("quit");
	}
}
