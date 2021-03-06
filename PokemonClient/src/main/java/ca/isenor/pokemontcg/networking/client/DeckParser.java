package ca.isenor.pokemontcg.networking.client;

import java.util.Map;
import java.util.Scanner;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardMapper;
import ca.isenor.pokemontcg.networking.client.exceptions.DeckParseException;
import ca.isenor.pokemontcg.player.collections.Deck;

public class DeckParser {
	private Map<String,Card> cardMap;

	public DeckParser() {
		cardMap = CardMapper.initializeMap();
	}
	public Deck parse(Scanner deckScan) throws DeckParseException  {
		Deck deck = new Deck();
		while (deckScan.hasNext()) {
			int amount = deckScan.nextInt();
			deckScan.skip(" ");
			String cardName = deckScan.nextLine();
			Card card = cardMap.get(cardName);
			for (int i = 0; i < amount; i++) {
				if (card != null) {
					deck.putOnBottom(card);
				}
				else
					throw new DeckParseException("No card found with the name: " + cardName + ".");
			}
		}
		return deck;
	}
}