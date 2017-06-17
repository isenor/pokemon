package ca.isenor.pokemontcg.networking.server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardType;
import ca.isenor.pokemontcg.cards.energy.Energy;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
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
	private static final String HAND = "hand";
	private static final String BENCH = "bench";
	private static final String ACTIVE = "active";

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
					otherPlayer.getOut().println(player.getName() + ": " + message.substring("chat".length()).trim());
				}
				else if (message.startsWith("opp")) {
					oppCommands(message.substring("opp".length()).trim());
				}
				else if (message.startsWith("quit")) {
					otherPlayer.getOut().println(player.getName() + " has left the game.");
					finished = true;
					System.out.println("Ending player input thread for Player" + playerNumber);
				}
				else if (message.startsWith("select") && controller.getModel().getPlayerTurn() == playerNumber) {
					selectCommands(message.substring("select".length()).trim());
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
				else if (BENCH.equals(message)) {
					thisPlayer.getOut().println("multiline");
					thisPlayer.getOut().println(player.getBench());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her bench.");
				}
				else if (HAND.equals(message)) {
					thisPlayer.getOut().println("multiline");
					thisPlayer.getOut().println(player.getHand());
					thisPlayer.getOut().println("complete");
					System.out.println("Player" + playerNumber + " looked at his/her hand.");
				}
				else if (ACTIVE.equals(message)) {
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
		if (BENCH.equals(message)) {
			out.println("\n" + opp.getName() + "'s bench:");
			out.println(opp.getBench());
			System.out.println("Player" + playerNumber + " looked at his/her opponent's bench.");
		}
		else if (ACTIVE.equals(message)) {
			out.println("\n" + opp.getName() + "'s active Pokemon:");
			out.println(opp.getActive().longDescription());
			System.out.println("Player" + playerNumber + " looked at his/her opponent's active Pokemon.");
		}
		else if (HAND.equals(message)) {
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
			out.println("unexpected command argument: " + message);
		}
	}

	private void selectCommands(String message) throws IOException {
		Player player = controller.getModel().getPlayer(playerNumber);
		PrintWriter out = thisPlayer.getOut();
		if ("".equals(message.trim())) {
			out.println("Select from the following:");
			out.println(ACTIVE + "\n" + HAND + "\n" + BENCH);
			message = thisPlayer.getIn().readLine();
		}

		if (ACTIVE.equals(message.trim())) {
			Pokemon active = player.getActive();
			activePokemonActions(active);

		}
		else if (message.startsWith(BENCH)) {
			out.println("select bench x");
		}
		else if (message.startsWith(HAND)) {
			if (message.length() > HAND.length()) {
				handCommands(message.substring(HAND.length()).trim());
			}
			else {
				out.println(player.getHand());
				out.println("\nSelect a card from your hand:");
				String input = thisPlayer.getIn().readLine();
				handCommands(input);
			}
		}
		else {
			out.println("unknown command: " + message);
		}
	}

	private void activePokemonActions(Pokemon active) throws IOException {
		PrintWriter out = thisPlayer.getOut();
		out.println("\nActive:" + active.longDescription());
		out.println("Actions:");
		int actionNumber = 1;
		Map<Integer,String> actionMap = new HashMap<>();
		if (controller.getModel().getTurn() != 0) {
			final int arrayOffset = 1;
			for (int attackNumber = 0; attackNumber < active.getNumberOfAttacks(); attackNumber++) {
				if (active.checkEnergy(active.getAttackCost(attackNumber))) {
					out.println((attackNumber + arrayOffset) + ": attack with " + active.getAttackName(attackNumber));
					actionMap.put(actionNumber,"attack");
					actionNumber++;
				}
			}
			if (active.checkEnergy(active.getRetreatCost())) {
				out.println((actionNumber) + ": retreat");
				actionMap.put(actionNumber,"retreat");
				actionNumber++;
			}
		}
		if (actionNumber == 1) {
			out.println("none");
		}
		else {
			out.println((actionNumber) + ": no action");
			String input = "";
			try {
				input = thisPlayer.getIn().readLine();
				int selection = Integer.parseInt(input);
				String action = actionMap.get(selection);
				if ("attack".equals(action)) {
					int attackNumber = selection - 1;
					out.println("\nAttack with " + active.getAttackName(attackNumber) + "?");
					String conf = thisPlayer.getIn().readLine();
					if (conf.startsWith("y")) {
						Pokemon opponent = controller.getModel().getOpponentOf(playerNumber).getActive();
						active.attack(attackNumber, opponent);
						out.println(active.getName() + " attacked " + opponent.getName() + "!!!");
						otherPlayer.getOut().println(opponent.getName() + " was attacked by " + active.getName() + "!!!");
						System.out.println(active.getName() + " attacked " + opponent.getName() + "!!!");
						thisPlayer.setCmd("done");
					}
				} else if ("retreat".equals(action)) {
					out.println("retreating... not implemented yet.");
				}
			}
			catch (NumberFormatException e) {
				out.println("unexpected command argument: " + input);
			}
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
				out.println("Add Pokemon to bench..... not yet implemented");
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
		int selection = -1;
		String input = "";
		try {
			input = thisPlayer.getIn().readLine();
			selection = Integer.parseInt(input);
		}
		catch (NumberFormatException e) {
			out.println("unexpected command argument:" + input);
			return;
		}
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
