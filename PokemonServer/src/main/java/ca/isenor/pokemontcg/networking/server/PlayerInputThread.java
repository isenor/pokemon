package ca.isenor.pokemontcg.networking.server;
import java.io.IOException;
import java.io.PrintWriter;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardType;
import ca.isenor.pokemontcg.cards.energy.Energy;
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
				Player player = controller.getModel().getPlayer(playerNumber);
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
				else if (message.startsWith("select") && controller.getModel().getPlayerTurn() == playerNumber) {
					selectCommands(message.substring("select".length() + 1));
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
					String whosTurn = "No. It is " + controller.getModel().getOpponentOf(playerNumber).getName() + "'s turn.";
					if (controller.getModel().getPlayerTurn() == playerNumber) {
						whosTurn = "Yes. It is your turn";
					}
					thisPlayer.getOut().println(whosTurn);
				}
				else if (controller.getModel().getPlayerTurn() == playerNumber) {
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
			thisPlayer.getOut().println("unable to correctly read input");
		}
		thisPlayer.getOut().println("quit");
	}

	private void oppCommands(String message) {
		Player opp = controller.getModel().getOpponentOf(playerNumber);
		PrintWriter out = thisPlayer.getOut();
		if ("bench".equals(message)) {
			out.println("\n" + opp.getName() + "'s bench:");
			out.println(opp.getBench());
			System.out.println("Player" + playerNumber + " looked at his/her opponent's bench.");
		}
		else if ("active".equals(message)) {
			out.println("\n" + opp.getName() + "'s active Pokemon:");
			out.println(opp.getActive().longDescription());
			System.out.println("Player" + playerNumber + " looked at his/her opponent's active Pokemon.");
		}
		else if ("hand".equals(message)) {
			out.println("\n" + opp.getName() + "'s hand has " + opp.getHand().size() + " cards in it.");
			System.out.println("Player" + playerNumber + " counted the cards in his/her opponent's hand.");
		}
		else if ("prizes".equals(message)) {
			out.println("\n" + opp.getName() + " has " + opp.getPrizeCards().size() + " prize cards left.");
			System.out.println("Player" + playerNumber + " counted his/her opponent's prize cards.");
		}
		else if ("deck".equals(message)) {
			out.println("\n" + opp.getName() + "'s deck has " + opp.getDeck().size() + " cards left.");
			System.out.println("Player" + playerNumber + " counted the cards in his/her opponent's deck.");
		}
		else {

		}
	}

	private void selectCommands(String message) throws IOException {
		Player player = controller.getModel().getPlayer(playerNumber);
		PrintWriter out = thisPlayer.getOut();
		if ("active".equals(message.trim())) {
			out.println("\nActive:" + player.getActive().longDescription());
			out.println("Actions:");
			if (controller.getModel().getTurn() != 0) {
				out.println("Attack1\nAttack2");
			}
		}
		else if (message.startsWith("bench")) {
			out.println("select bench x");
		}
		else if (message.startsWith("hand")) {
			handCommands(message.substring("hand".length() + 1));
		}
		else {
			out.println("unknown command");
		}
	}

	private void handCommands(String message) throws IOException {
		Player player = controller.getModel().getPlayer(playerNumber);
		PrintWriter out = thisPlayer.getOut();
		final int arrayOffset = 1;
		int input = -1;
		try {
			input = Integer.parseInt(message);
		}
		catch (NumberFormatException e) {
			out.println("unknown command argument:" + message);
			return;
		}
		if (input > 0 && input <= player.getHand().size()) {
			Card card = player.getHand().getCard(input - arrayOffset);
			out.println("\n" + card.longDescription());
			out.println("-----------------"
					+ "\nActions:");
			if (card.getCardType() == CardType.ENERGY) {
				if (!player.hasPlayedEnergy()) {
					energyActions((Energy)card, input - arrayOffset);
				}
				else {
					out.println("You've already attached energy this turn");
				}
			}
			else if (card.getCardType() == CardType.POKEMON) {

			}

		}
		else {
			out.println("Index out of range: " + input);
		}
	}

	private void energyActions(Energy energy, int cardNumber) throws IOException {
		Player player = controller.getModel().getPlayer(playerNumber);
		PrintWriter out = thisPlayer.getOut();
		out.println("1: attach to active " + player.getActive());
		final int arrayOffset = 2;
		int i;
		for (i = 0; i < player.getBench().size(); i++) {
			out.println((i + arrayOffset) + ": attach to benched " + player.getBench().get(i));
		}
		out.println((i + arrayOffset) + ": don't attach " + energy.getName());
		int selection = Integer.parseInt(thisPlayer.getIn().readLine());
		if (selection == 1) {
			out.println("\nAttach " + energy.getName() + " to " + player.getActive() + "? (y/n)");
			String conf = thisPlayer.getIn().readLine();
			if (conf.startsWith("y")) {
				player.getActive().attachEnergy((Energy)player.getHand().remove(cardNumber));
				player.setPlayedEnergy(true);
			}
			return;
		}
		else if (selection > 1 && selection < player.getBench().size() + arrayOffset){
			out.println("\nAttach " + energy.getName() + " to " + player.getBench().get(selection - arrayOffset) + "? (y/n)");
			String conf = thisPlayer.getIn().readLine();
			if (conf.startsWith("y")) {
				player.getBench().get(selection - arrayOffset).attachEnergy((Energy)player.getHand().remove(cardNumber));
				player.setPlayedEnergy(true);
			}
			return;
		}

		out.println("\nNo action taken");

	}
}
