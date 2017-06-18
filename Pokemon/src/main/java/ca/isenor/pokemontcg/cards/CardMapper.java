package ca.isenor.pokemontcg.cards;

import java.util.HashMap;
import java.util.Map;

import ca.isenor.pokemontcg.cards.energy.fire.BasicFireEnergy;
import ca.isenor.pokemontcg.cards.energy.grass.BasicGrassEnergy;
import ca.isenor.pokemontcg.cards.energy.water.BasicWaterEnergy;
import ca.isenor.pokemontcg.cards.pokemon.fire.Charmander;
import ca.isenor.pokemontcg.cards.pokemon.fire.Fennekin;
import ca.isenor.pokemontcg.cards.pokemon.grass.Bulbasaur;
import ca.isenor.pokemontcg.cards.pokemon.water.CandleWind;
import ca.isenor.pokemontcg.cards.pokemon.water.Froakie;
import ca.isenor.pokemontcg.cards.pokemon.water.Squirtle;

/**
 * should probably make it so these are read in as xml or something
 *
 * @author dawud
 *
 */
public class CardMapper {

	private CardMapper() {}

	public static Map<String, Card> initializeMap() {
		Map<String,Card> cardMap = new HashMap<>();
		cardMap.put("Basic Water Energy", new BasicWaterEnergy());
		cardMap.put("Basic Fire Energy", new BasicFireEnergy());
		cardMap.put("Basic Grass Energy", new BasicGrassEnergy());
		cardMap.put("Charmander",new Charmander());
		cardMap.put("Fennekin", new Fennekin());
		cardMap.put("Froakie", new Froakie());
		cardMap.put("Squirtle", new Squirtle());
		cardMap.put("Bulbasaur", new Bulbasaur());
		cardMap.put("CandleWind", new CandleWind());
		return cardMap;
	}
}
