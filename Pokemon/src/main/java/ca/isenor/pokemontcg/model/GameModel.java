package ca.isenor.pokemontcg.model;

import ca.isenor.pokemontcg.player.Player;

public class GameModel {
	private int turn;
	private Player player0;
	private Player player1;

	public GameModel(Player a, Player b) {
		turn = 0;
		player0 = a;
		player1 = b;
	}

	public GameModel() {

	}

	public void setPlayer(Player player, int playerNumber) {
		if (playerNumber == 0) {
			player0 = player;
		}
		else if (playerNumber == 1){
			player1 = player;
		}
	}

	public Player getPlayer(int playerNumber) {
		if (playerNumber == 0) {
			return player0;
		}
		else {
			return player1;
		}
	}

	public Player getOpponentOf(int playerNumber) {
		return getPlayer((playerNumber + 1) % 2);
	}

	public int getTurn() {
		return turn;
	}

	public int getPlayerTurn() {
		return turn % 2;
	}

	public void endTurn(int playerNumber) {
		getPlayer(playerNumber).setPlayedEnergy(false);
		getPlayer(playerNumber).setRetreated(false);
		turn++;
	}

	public boolean isFirstTurn(int playerNumber) {
		return playerNumber == turn;
	}

	@Deprecated
	public void initGame() {
		System.out.println("Initializing the game model...");
		if (player0.getDeck().isValid()) {
			System.out.println(player0.getName() + "'s deck valid. Proceeding...");
			if (player1.getDeck().isValid()) {
				System.out.println(player1.getName() + "'s deck valid. Proceeding...");
				player0.openingHand();
				player1.openingHand();
				System.out.println("Opening hands collected...");

			}
			else {
				System.out.println(player1.getName() + "'s deck is not valid.");
			}
		}
		else {
			System.out.println(player0.getName() + "'s deck is not valid.");
		}
	}

	@Deprecated
	public void beginTurn(Player player) {
		System.out.println("Beginning a new turn...");
		turn++;
		player.draw();
	}

	@Deprecated
	public Player getPlayerA() {
		return player0;
	}

	@Deprecated
	public Player getPlayerB() {
		return player1;
	}
}
