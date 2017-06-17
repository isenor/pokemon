package ca.isenor.pokemontcg.cards.pokemon.fire;

import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.EnergyAmount;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.cards.pokemon.PokemonCardDetails;

public class Fennekin extends Pokemon {
	public Fennekin() {
		super("Fennekin",
				new PokemonCardDetails(60, 1, Type.FIRE, Type.WATER, Type.NONE,	Stage.BASIC),
				"Will-O-Wisp");
	}

	@Override
	public void attack(int attackNumber, Pokemon opponent) {
		switch(attackNumber) {
		case 0:
			doDamage(opponent,20);
			break;
		default:
		}
	}

	@Override
	public EnergyAmount getAttackCost(int attackNumber) {
		EnergyAmount energy = new EnergyAmount();
		switch(attackNumber) {
		case 0:
			energy.setEntry(Type.FIRE, 1);
			break;
		default:
		}

		return energy;
	}

}
