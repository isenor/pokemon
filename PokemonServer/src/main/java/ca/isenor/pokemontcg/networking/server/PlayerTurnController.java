package ca.isenor.pokemontcg.networking.server;

import ca.isenor.pokemontcg.model.GameModel;
import ca.isenor.pokemontcg.networking.server.lock.PokeLock;
import ca.isenor.pokemontcg.player.Player;

/**
 * The controller running on the server which weaves the players' turns together.
 *
 * @author dawud
 *
 */
public class PlayerTurnController {
	private static final String NULLCMD = "<nop>";
	private int playerTurn;
	private ServerPlayerThread player0;
	private ServerPlayerThread player1;
	private GameModel model;
	//ServerWatcherThread[] watchers;
	private PokeLock lock;
	private boolean wasNotified;

	public PlayerTurnController() {
		model = new GameModel();
		lock = new PokeLock();
	}

	public void setPlayer(Player player, int playerNumber) {
		model.setPlayer(player, playerNumber);
	}

	public Player getPlayer(int playerNumber) {
		return model.getPlayer(playerNumber);
	}

	public PokeLock getLock() {
		return lock;
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
	public String takeTurn(final int playerNumber) {
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
		System.out.println("playerNumber:" + playerNumber);
		System.out.println("playerTurn:" + playerTurn);


		synchronized(this) {
			while (playerTurn != playerNumber) {
				thisPlayer.getOut().println("Waiting for turn...");
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//lock.doWait();
			}
		}



		thisPlayer.getOut().println("It is your turn.");

		String command = "";

		thisPlayer.getOut().println("===================");
		thisPlayer.getOut().println("Enter your command:");

		// Polls for updates to the player command.
		while (!"done".equals(command) && !"end".equals(command)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!NULLCMD.equals(thisPlayer.getCmd())) {
				System.out.println("Player" + playerNumber + ": " + thisPlayer.getCmd());
				otherPlayer.getOut()
				.println(getPlayer(playerNumber).getName() + ": cmd: " + thisPlayer.getCmd());

				command = interpret(thisPlayer.getCmd());
				thisPlayer.setCmd(NULLCMD);

			}
		}

		synchronized(this) {
			playerTurn = (playerTurn + 1) % 2;
			this.notifyAll();
		}
		//lock.doNotify();
		return command;
	}

	/**
	 * Interpret the result of a command
	 *
	 * @param cmd
	 * @return
	 */
	private String interpret(String cmd) {
		String result;
		if (cmd.equals("done")) {
			result = cmd;
		}
		else {
			result = "unknown command: " + cmd;
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