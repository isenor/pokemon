package ca.isenor.pokemontcg.networking.server;
import java.io.IOException;

import ca.isenor.pokemontcg.player.Player;

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
	private ServerPlayerThread thisPlayer;
	private ServerPlayerThread otherPlayer;

	public PlayerInputThread(PlayerTurnController controller, int playerNumber) {
		this.controller = controller;
		this.playerNumber = playerNumber;
	}

	@Override
	public void run() {

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
				Player player = controller.getPlayer(playerNumber);
				if (message.startsWith("chat")) {
					otherPlayer.getOut().println(player.getName() + ": " + message.substring(5));
				}
				else if (message.startsWith("opp")) {
					oppCommands(message.substring(4));
				}
				else if (message.startsWith("quit")) {
					otherPlayer.getOut().println(player.getName() + " has left the game.");
					finished = true;
					System.out.println("Ending player input thread for Player" + playerNumber);
				}
				else if ("clear".equals(message)) {
					thisPlayer.getOut().println("clearscreen");
				}
				else if ("deck".equals(message)) {
					thisPlayer.getOut().println("\nYou have " + player.getDeck().size() + " cards left in your deck.");
					System.out.println("Player" + playerNumber + " counted the cards in his/her deck.");
				}
				else if ("prizes".equals(message)) {
					thisPlayer.getOut().println("\nYou have " + player.getPrizeCards().size() + " prize cards left.");
					System.out.println("Player" + playerNumber + " counted his/her prize cards.");
				}
				else if ("bench".equals(message)) {
					thisPlayer.getOut().println("multiline");
					thisPlayer.getOut().println(player.getBench());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her bench.");
				}
				else if ("hand".equals(message)) {
					thisPlayer.getOut().println("multiline");
					thisPlayer.getOut().println(player.getHand());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her hand.");
				}
				else if ("active".equals(message)) {
					thisPlayer.getOut().println("multiline");
					thisPlayer.getOut().println("Active Pokemon:\n" + player.getActive().longDescription());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her active Pokemon.");
				}
				else if ("myturn".equals(message)) {
					String whosTurn = "No. It is " + controller.getOpponentOf(playerNumber).getName() + "'s turn.";
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
							" for " + player.getName());
					thisPlayer.getOut().println("Unable to perform the command: " + message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		thisPlayer.getOut().println("quit");
	}

	private void oppCommands(String message) {
		Player opp = controller.getOpponentOf(playerNumber);
		if ("bench".equals(message)) {
			thisPlayer.getOut().println("\n" + opp.getName() + "'s bench:");
			thisPlayer.getOut().println(opp.getBench());
			System.out.println("Player" + playerNumber + " looked at his/her opponents bench.");
		}
		else if ("active".equals(message)) {
			thisPlayer.getOut().println("\n" + opp.getName() + "'s active Pokemon:");
			thisPlayer.getOut().println(opp.getActive().longDescription());
			System.out.println("Player" + playerNumber + " looked at his/her opponents active Pokemon.");
		}
		else if ("hand".equals(message)) {
			thisPlayer.getOut().println("\n" + opp.getName() + "'s hand has " + opp.getHand().size() + " cards in it.");
			System.out.println("Player" + playerNumber + " counted the cards in his/her opponent's hand.");
		}
		else if ("prizes".equals(message)) {
			thisPlayer.getOut().println("\n" + opp.getName() + " has " + opp.getPrizeCards().size() + " prize cards left.");
			System.out.println("Player" + playerNumber + " counted his/her opponent's prize cards.");
		}
		else if ("deck".equals(message)) {
			thisPlayer.getOut().println("\n" + opp.getName() + "'s deck has " + opp.getDeck().size() + " cards left.");
			System.out.println("Player" + playerNumber + " counted the cards in his/her opponent's deck.");
		}
	}
}
