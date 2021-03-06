package ca.isenor.pokemontcg.networking.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardType;
import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.player.Player;

/**
 * A thread running on the server side for each player connected.
 * Handles initial setup of player objects for the Game Model.
 *
 * @author dawud
 *
 */
public class ServerPlayerThread extends Thread {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private PlayerTurnController controller;
	private final int playerNumber;

	private String cmd = "<nop>";

	public ServerPlayerThread(Socket socket, PlayerTurnController controller, int playerNumber) {
		super("ServerPlayerThread" + playerNumber);
		this.socket = socket;
		this.controller = controller;
		this.playerNumber = playerNumber;
	}

	@Override
	public void run() {

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			objectInput = new ObjectInputStream(socket.getInputStream());
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			String inputLine;

			controller.setPlayer(readPlayerData(),playerNumber);

			out.println("Welcome Pokemon Trainer " + controller.getPlayer(playerNumber).getName() + "!");
			out.println(controller.getPlayer(playerNumber).getDeck());

			if (playerNumber == controller.getPlayerTurn()) {
				out.println("Waiting for another player to join...");
				controller.getLock().doWait();
				out.println("Another player has joined.");
			}
			else {
				out.println("Letting the other player know you're here");
				controller.getLock().doNotify();
			}

			setupPlayer();

			if (controller.getLock().checkAndIncrement()) {
				out.println("Waiting for other player to choose Active Pokemon");
				controller.getLock().doWait();
				out.println("The other player is done");
			}
			else {
				out.println("Letting other player know you're done choosing your Active Pokemon");
				controller.getLock().doNotify();
			}

			PlayerInputThread chat = new PlayerInputThread(controller,playerNumber);
			chat.start();

			out.println("========================");
			out.println("     Starting game!");
			out.println("Opponent:");
			out.println(controller.getPlayer((playerNumber + 1) % 2).getName());
			out.println("========================");
			boolean finished = false;
			while(!finished && controller.getPlayerThread((playerNumber + 1) % 2) != null) {
				inputLine = controller.takeTurn(playerNumber);

				if ("quit".equals(inputLine)) {
					finished = true;
				}
			}
			System.out.println("THIS IS THE END!");
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Player readPlayerData() {
		try {
			try {
				return (Player) objectInput.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Failed to receive player data. Aborting...");
				out.println("Failed to receive player data. Aborting...");
				e.printStackTrace();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Player setupPlayer()  {
		Player player = controller.getPlayer(playerNumber);
		player.getDeck().shuffle();
		setupHand(player);
		while (player.getActive() == null) {
			try {
				setupActivePokemon(player);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return player;
	}

	private void setupHand(Player player)  {
		player.openingHand();
		out.println("handinit");
		out.println("Opening hand:\n" + player.getHand());
		out.println("complete");
		while (!player.getHand().hasBasic()) {
			player.putHandIntoDeck();
			player.getDeck().shuffle();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			player.openingHand();
			out.println("mulligan");
			out.println("Opening hand:\n" + player.getHand());
			out.println("complete");
		}
	}

	private void setupActivePokemon(Player player) throws IOException {
		String pokeName = "invalid";
		boolean success = false;
		while (!success) {
			out.println("Pick a Basic Pokemon from your hand to be your starting active Pokemon.");
			final int arrayOffset = 1;
			String inputInt;
			while (!success && (inputInt = in.readLine()) != null) {
				int selection = Integer.parseInt(inputInt) - arrayOffset;
				if (selection <= player.getHand().size() && selection >= 0) {
					Card card = player.getHand().getCard(selection);
					if (isBasicPokemon(card)) {
						pokeName = ((Pokemon)card).getName();
						out.println("You have selected " + pokeName + ".\nAre you sure? (y/n)");
						String conf = in.readLine();
						if (conf.startsWith("y")) {
							player.setActive((Pokemon)player.getHand().remove(selection));
							success = true;
						}
					}
					else {
						out.println("That is an invalid selection: " + card.getName() + " is not a Basic Pokemon.");
					}
				}
				else {
					out.println("That is an invalid selection: " + (selection + arrayOffset) + " is not within range.");
				}

				if (!success)
					out.println("Please select again.");
			}
		}
		out.println("Your active pokemon has been set to " + pokeName + ".");
	}

	private boolean isBasicPokemon(Card card) {
		if (card.getCardType() == CardType.POKEMON) {
			Pokemon pokemon = (Pokemon)card;
			return pokemon.getStage() == Stage.BASIC;
		}
		return false;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * Get the PrintWriter of this thread so that messages can be
	 * directly passed.
	 *
	 * @return the PrintWriter of this thread
	 */
	public PrintWriter getOut() {
		return out;
	}

	public BufferedReader getIn() {
		return in;
	}

	public ObjectInputStream getObjectInput() {
		return objectInput;
	}

	public ObjectOutputStream getObjectOutput() {
		return objectOutput;
	}
}
