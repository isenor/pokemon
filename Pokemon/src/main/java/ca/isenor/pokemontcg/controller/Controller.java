package ca.isenor.pokemontcg.controller;

import ca.isenor.pokemontcg.model.Model;
import ca.isenor.pokemontcg.player.Player;
import ca.isenor.pokemontcg.player.cards.energy.fire.BasicFireEnergy;
import ca.isenor.pokemontcg.player.cards.energy.water.BasicWaterEnergy;
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
		view.displayField();


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

}
