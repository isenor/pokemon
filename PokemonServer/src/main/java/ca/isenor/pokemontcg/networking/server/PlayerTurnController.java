package ca.isenor.pokemontcg.networking.server;

import ca.isenor.pokemontcg.model.GameModel;
import ca.isenor.pokemontcg.player.Player;

/**
 * The controller running on the server which weaves the players' turns together.
 *
 * @author dawud
 *
 */
public class PlayerTurnController {
	private int playerTurn;
	ServerPlayerThread player0;
	ServerPlayerThread player1;

	GameModel model;
	//ServerWatcherThread[] watchers;

	public PlayerTurnController() {
		player0 = null;
		player1 = null;
		playerTurn = 0;
		model = new GameModel();
	}

	public void setPlayer(Player player, int playerNumber) {
		model.setPlayer(player, playerNumber);
	}

	public Player getPlayer(int playerNumber) {
		return model.getPlayer(playerNumber);
	}

	/**
	 * Add a player to the game run on the server.
	 *
	 * @param player A thread for the player being added
	 * @return a message for the server
	 */
	public String addPlayerThread(ServerPlayerThread player) {
		if (player0 == null) {
			player0 = player;
			return "PlayerA joined.";
		}
		else if (player1 == null) {
			player1 = player;
			return "PlayerB joined.";
		}
		else {
			return "Unable to add a watcher at this time.";
		}
	}

	/**
	 * Defines the steps in a turn.
	 *
	 * @param playerNumber
	 * @return A message for the server
	 */
	public synchronized String takeTurn(int playerNumber) {
		ServerPlayerThread thisPlayer;
		ServerPlayerThread otherPlayer;

		// Deduce relative players
		if (playerNumber == 0) {
			thisPlayer = player0;
			otherPlayer = player1;
		}
		else {
			thisPlayer = player1;
			otherPlayer = player0;
		}

		// decide who's turn it is
		if (playerTurn != playerNumber) {
			boolean myTurn = false;
			while(!myTurn) {
				try {
					thisPlayer.getOut().println("Waiting for turn...");
					this.wait();
					myTurn = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			thisPlayer.getOut().println("It is your turn.");
		}

		String command = "";
		try {
			thisPlayer.getOut().println("Enter your command:");

			// Polls for updates to the player command.
			while (!"done".equals(command) && !"end".equals(command)) {
				thisPlayer.sleep(10);
				//System.out.println("waiting for non-onionsauce command");
				if (!"onionsauce".equals(thisPlayer.getCmd())) {
					System.out.println("non-onionsauce:Player" + playerNumber + ": " + thisPlayer.getCmd());
					otherPlayer.getOut()
					.println("Player" + playerNumber + ": Action: " + thisPlayer.getCmd());

					command = interpret(thisPlayer.getCmd());
					thisPlayer.setCmd("onionsauce");

				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//otherPlayer.getOut().println("Player" + playerNumber + ": " + "turn done.");
		System.err.println("Player" + playerNumber + ": " + command);
		playerTurn = (playerTurn + 1) % 2;
		this.notifyAll();
		return command;
	}

	private String interpret(String cmd) {
		String result;
		if (cmd.startsWith("end")) {
			result = cmd;
		}
		else if (cmd.equals("done")) {
			result = cmd;
		}
		else {
			result = cmd + " acknowledged";
		}

		System.out.println("interpreted result: " + result);
		return result;
	}

	/**
	 * Get the reference to a player's thread based on that player's number.
	 * @param playerNumber
	 * @return the reference to a player's thread
	 */
	public ServerPlayerThread getPlayerThread(int playerNumber) {
		if (playerNumber == 0) {
			return player0;
		}
		else  {
			return player1;
		}
	}

	/**
	 *
	 * @return which player's turn it is
	 */
	public int getPlayerTurn() {
		return playerTurn;
	}
}