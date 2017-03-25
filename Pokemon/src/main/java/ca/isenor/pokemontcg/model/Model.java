package ca.isenor.pokemontcg.model;

import ca.isenor.pokemontcg.player.Player;

public class Model {
	private int turn;
	private Player playerA;
	private Player playerB;

	public Model(Player a, Player b) {
		turn = 0;
		playerA = a;
		playerB = b;
	}

	public void initGame() {
		System.out.println("Initializing the game model...");
		if (playerA.getDeck().isValid()) {
			System.out.println(playerA.getName() + "'s deck valid. Proceeding...");
			if (playerB.getDeck().isValid()) {
				System.out.println(playerB.getName() + "'s deck valid. Proceeding...");
				playerA.openingHand();
				playerB.openingHand();
				System.out.println("Opening hands collected...");

			}
			else {
				System.out.println(playerB.getName() + "'s deck is not valid.");
			}
		}
		else {
			System.out.println(playerA.getName() + "'s deck is not valid.");
		}
	}

	public void beginTurn(Player player) {
		System.out.println("Beginning a new turn...");
		turn++;
		player.draw();
	}

	public int getTurn() {
		return turn;
	}

	public Player getPlayerA() {
		return playerA;
	}

	public Player getPlayerB() {
		return playerB;
	}



}
