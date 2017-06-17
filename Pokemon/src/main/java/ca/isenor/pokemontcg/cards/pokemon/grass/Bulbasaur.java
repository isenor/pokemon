package ca.isenor.pokemontcg.cards.pokemon.grass;

import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.EnergyAmount;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.cards.pokemon.PokemonCardDetails;

public class Bulbasaur extends Pokemon {

	public Bulbasaur() {
		super("Bulbasaur",
				new PokemonCardDetails(40, 1, Type.GRASS, Type.FIRE, Type.NONE, Stage.BASIC),
				"Leech Seed");
	}
	@Override
	public void attack(int attackNumber, Pokemon opponent) {
		if (attackNumber == 0) {
			doDamage(opponent,20);
		}
	}

	@Override
	public EnergyAmount getAttackCost(int attackNumber) {
		EnergyAmount energy = new EnergyAmount();
		if (attackNumber == 0) {
			energy.setEntry(Type.GRASS,2);
		}
		return energy;
	}
}
