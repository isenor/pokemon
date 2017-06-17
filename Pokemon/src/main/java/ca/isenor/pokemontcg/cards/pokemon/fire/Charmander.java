package ca.isenor.pokemontcg.cards.pokemon.fire;

import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.EnergyAmount;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.cards.pokemon.PokemonCardDetails;

public class Charmander extends Pokemon {
	public Charmander() {

		super("Charmander",
				new PokemonCardDetails(50, 1, Type.FIRE, Type.WATER, Type.NONE, Stage.BASIC),
				"Scratch", "Ember");
	}

	@Override
	public void attack(int attackNumber, Pokemon opponent) {
		switch(attackNumber) {
		case 0:
			doDamage(opponent,10);
			break;
		case 1:
			doDamage(opponent,30);
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
			energy.setEntry(Type.FIRE,1);
			energy.setEntry(Type.COLORLESS, 1);
			break;
		default:
		}

		return energy;
	}

}
