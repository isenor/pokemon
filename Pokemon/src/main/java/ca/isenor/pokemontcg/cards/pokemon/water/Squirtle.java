package ca.isenor.pokemontcg.cards.pokemon.water;

import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.EnergyAmount;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.cards.pokemon.PokemonCardDetails;

public class Squirtle extends Pokemon {
	public Squirtle() {
		super("Squirtle", new PokemonCardDetails(60, 1, Type.WATER, Type.GRASS, Type.NONE,
				Stage.BASIC),
				"Tackle");
	}

	@Override
	public void attack(int attackNumber, Pokemon opponent) {
		switch(attackNumber) {
		case 0:
			doDamage(opponent,10);
			break;

		default:
		}
	}

	@Override
	public EnergyAmount getAttackCost(int attackNumber) {
		EnergyAmount energy = new EnergyAmount();
		switch(attackNumber) {
		case 0:
			energy.setEntry(Type.COLORLESS, 1);
			break;
		default:
		}

		return energy;
	}
}
