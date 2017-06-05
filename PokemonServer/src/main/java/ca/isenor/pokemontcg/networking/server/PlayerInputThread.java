package ca.isenor.pokemontcg.networking.server;
import java.io.IOException;

import ca.isenor.pokemontcg.cards.pokemon.Pokemon;

/**
 * Handles commands sent from the client/player concurrently with turn operations.
 * This allows for commands to be accepted and acted upon even when it is the other player's
 * turn
 *
 * @author dawud
 *
 */
public class PlayerInputThread extends Thread {
	private PlayerTurnController controller;
	private int playerNumber;

	public PlayerInputThread(PlayerTurnController controller, int playerNumber) {
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
					System.out.println("Ending player input thread for Player" + playerNumber);
				}
				else if ("clear".equals(message)) {
					thisPlayer.getOut().println("clearscreen");
				}
				else if ("bench".equals(message)) {
					thisPlayer.getOut().println("multiline");
					thisPlayer.getOut().println(controller.getPlayer(playerNumber).getBench());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her bench.");
				}
				else if ("hand".equals(message)) {
					thisPlayer.getOut().println("multiline");
					thisPlayer.getOut().println(controller.getPlayer(playerNumber).getHand());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her hand.");
				}
				else if ("active".equals(message)) {
					thisPlayer.getOut().println("multiline");
					Pokemon active = controller.getPlayer(playerNumber).getActive();
					thisPlayer.getOut().println("Active Pokemon:\n" + active.longDescription());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her active Pokemon.");
				}
				else if ("myturn".equals(message)) {
					int otherPlayerNumber = (playerNumber + 1) % 2;
					String whosTurn = "No. It is " + controller.getPlayer(otherPlayerNumber).getName() + "'s turn.";
					if (controller.getPlayerTurn() == playerNumber) {
						whosTurn = "Yes. It is your turn";
					}
					thisPlayer.getOut().println(whosTurn);
				}
				else if (controller.getPlayerTurn() == playerNumber) {
					System.out.println("Command received on player" + playerNumber + "'s turn.");
					thisPlayer.setCmd(message);
				}
				else {
					System.out.println("Unable to perform command " + message +
							" for " + controller.getPlayer(playerNumber).getName());
					thisPlayer.getOut().println("Unable to perform the command: " + message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		thisPlayer.getOut().println("quit");
	}
}
