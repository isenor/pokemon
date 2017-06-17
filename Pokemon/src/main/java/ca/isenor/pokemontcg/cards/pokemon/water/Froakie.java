package ca.isenor.pokemontcg.cards.pokemon.water;

import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.EnergyAmount;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.cards.pokemon.PokemonCardDetails;

public class Froakie extends Pokemon {
	public Froakie() {
		super("Froakie", new PokemonCardDetails(60, 1, Type.WATER, Type.LIGHTNING, Type.NONE,
				Stage.BASIC),
				"Pound",
				"Water Drip");
	}

	@Override
	public void attack(int attackNumber, Pokemon opponent) {
		switch(attackNumber) {
		case 0:
			doDamage(opponent,10);
			break;
		case 1:
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
			energy.setEntry(Type.COLORLESS, 1);
			break;
		case 1:
			energy.setEntry(Type.WATER, 1);
			energy.setEntry(Type.COLORLESS, 1);
			break;
		default:
		}

		return energy;
	}
}
