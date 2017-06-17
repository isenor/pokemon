package ca.isenor.pokemontcg.cards.pokemon.water;

import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.EnergyAmount;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.cards.pokemon.PokemonCardDetails;

/**
 * Aminah's pokemon
 */
public class CandleWind extends Pokemon {
	public CandleWind() {
		super("CandleWind", new PokemonCardDetails(50, 1, Type.WATER, Type.LIGHTNING, Type.NONE,
				Stage.BASIC),
				"Shooting Rocks",
				"Wind Power");
	}

	//Shooting Rocks 20 energy 1 blue
	//Wind power 100 energy 3 blue
	@Override
	public void attack(int attackNumber, Pokemon opponent) {
		switch(attackNumber) {
		case 0:
			doDamage(opponent,20);
			break;
		case 1:
			doDamage(opponent,100);
			break;
		default:
		}
	}

	@Override
	public EnergyAmount getAttackCost(int attackNumber) {
		EnergyAmount energy = new EnergyAmount();
		switch(attackNumber) {
		case 0:
			energy.setEntry(Type.WATER, 1);
			break;
		case 1:
			energy.setEntry(Type.WATER, 3);
			break;
		default:
		}
		return energy;
	}
}
