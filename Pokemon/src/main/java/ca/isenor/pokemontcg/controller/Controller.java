package ca.isenor.pokemontcg.controller;

import java.util.Scanner;

import ca.isenor.pokemontcg.model.Model;
import ca.isenor.pokemontcg.player.Player;
import ca.isenor.pokemontcg.player.cards.Card;
import ca.isenor.pokemontcg.player.cards.CardType;
import ca.isenor.pokemontcg.player.cards.energy.fire.BasicFireEnergy;
import ca.isenor.pokemontcg.player.cards.energy.water.BasicWaterEnergy;
import ca.isenor.pokemontcg.player.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.player.cards.pokemon.fire.Charmander;
import ca.isenor.pokemontcg.player.cards.pokemon.fire.Fennekin;
import ca.isenor.pokemontcg.player.cards.pokemon.water.Froakie;
import ca.isenor.pokemontcg.player.cards.pokemon.water.Squirtle;
import ca.isenor.pokemontcg.player.collections.Deck;
import ca.isenor.pokemontcg.view.View;

public class Controller {

	// Prevent Instantiation
	private Controller() {}

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);

		Deck fire = new Deck();
		fire.putOnTop(new Charmander());
		fire.putOnTop(new Charmander());
		fire.putOnTop(new Charmander());
		fire.putOnTop(new Charmander());
		fire.putOnTop(new Fennekin());
		fire.putOnTop(new Fennekin());
		fire.putOnTop(new Fennekin());
		fire.putOnTop(new Fennekin());
		fire.putOnTop(new BasicFireEnergy());
		fire.putOnTop(new BasicFireEnergy());
		fire.putOnTop(new BasicFireEnergy());
		fire.putOnTop(new BasicFireEnergy());
		fire.shuffle();

		Deck water = new Deck();
		water.putOnTop(new Squirtle());
		water.putOnTop(new Squirtle());
		water.putOnTop(new Squirtle());
		water.putOnTop(new Squirtle());
		water.putOnTop(new Froakie());
		water.putOnTop(new Froakie());
		water.putOnTop(new Froakie());
		water.putOnTop(new Froakie());
		water.putOnTop(new BasicWaterEnergy());
		water.putOnTop(new BasicWaterEnergy());
		water.putOnTop(new BasicWaterEnergy());
		water.putOnTop(new BasicWaterEnergy());
		water.shuffle();

		Deck invalid = new Deck();
		invalid.putOnTop(new BasicWaterEnergy());

		Player playerA = new Player("Ash", fire);
		Player playerB = new Player("Misty", water);
		// TODO: make random assignment of first player (no rush)
		Player curr = playerA;
		Model gameModel = new Model(playerA, playerB);
		View view = new View(gameModel);


		gameModel.initGame();
		//		playerA.setActive(active);
		view.displayHand(playerA.getHand());

		selectActive(playerA,view);

		selectActive(playerB,view);







		boolean proceed = true;
		do {
			gameModel.beginTurn(curr);



			if (curr.getName().equals(playerA.getName())) {
				curr = playerB;
			}
			else {
				curr = playerA;
				proceed = false;
			}
		} while (proceed);


	}

	/**
	 * At the beginning of the game, a player must choose a basic Pokemon from his/her hand
	 * to be the starting active Pokemon.
	 *
	 * @param player
	 * @param view
	 */
	private static void selectActive(Player player, View view) {
		view.print(player.getName() + ": Pick a basic Pokemon to be your active Pokemon.");
		Scanner keyboard = new Scanner(System.in);
		String input;
		boolean success = false;
		do {
			input = keyboard.nextLine();
			int pickIndex = Integer.parseInt(input);
			Card pickCard = player.getHand().getCard(pickIndex);
			if (pickCard.getCardType() == CardType.POKEMON) {
				player.setActive((Pokemon)pickCard);
				success = true;
			}
			else {
				view.print(player.getName() + ": " + pickCard.getName() + " is not a Basic Pokemon.");
			}

		} while (!input.startsWith("done") && !success);
		keyboard.close();
	}

}
