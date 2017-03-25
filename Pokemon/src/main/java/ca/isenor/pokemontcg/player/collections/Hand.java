package ca.isenor.pokemontcg.player.collections;

import java.util.Iterator;
import java.util.LinkedList;

import ca.isenor.pokemontcg.player.cards.Card;
import ca.isenor.pokemontcg.player.cards.CardType;
import ca.isenor.pokemontcg.player.cards.Stage;
import ca.isenor.pokemontcg.player.cards.pokemon.Pokemon;

public class Hand {
	private LinkedList<Card> hand;

	public Hand() {
		hand = new LinkedList<>();
	}

	public void add(Card card) {
		hand.add(card);
	}

	public Card getCard(int index) {
		return hand.get(index);
	}

	public int size() {
		return hand.size();
	}

	/**
	 * @return true if there is a basic pokemon in the hand; false otherwise
	 */
	public boolean hasBasic() {
		boolean basicExists = false;
		Iterator<Card> iterator = hand.iterator();
		do {
			Card curr = iterator.next();
			if (curr.getCardType() == CardType.POKEMON && ((Pokemon)curr).getStage() == Stage.BASIC) {
				basicExists = true;
			}
		} while (iterator.hasNext());
		return basicExists;
	}


}
