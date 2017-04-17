package ca.isenor.pokemontcg.player.cards;

import java.util.HashMap;
import java.util.Map;

import ca.isenor.pokemontcg.player.cards.energy.fire.BasicFireEnergy;
import ca.isenor.pokemontcg.player.cards.energy.water.BasicWaterEnergy;
import ca.isenor.pokemontcg.player.cards.pokemon.fire.Charmander;
import ca.isenor.pokemontcg.player.cards.pokemon.fire.Fennekin;
import ca.isenor.pokemontcg.player.cards.pokemon.water.Froakie;
import ca.isenor.pokemontcg.player.cards.pokemon.water.Squirtle;

public class CardMapper {
	public static Map initializeMap() {
		Map<String,Card> cardMap = new HashMap<>();
		cardMap.put("Basic Water Energy",new BasicWaterEnergy());
		cardMap.put("Basic Fire Energy",new BasicFireEnergy());
		cardMap.put("Charmander",new Charmander());
		cardMap.put("Fennekin",new Fennekin());
		cardMap.put("Froakie", new Froakie());
		cardMap.put("Squirtle", new Squirtle());
		return cardMap;
	}
}
