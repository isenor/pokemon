package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import ca.isenor.pokemontcg.player.cards.Card;
import ca.isenor.pokemontcg.player.cards.CardType;
import ca.isenor.pokemontcg.player.cards.Stage;
import ca.isenor.pokemontcg.player.cards.pokemon.Pokemon;

public class Hand implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5307908749382498143L;
	private LinkedList<Card> cards;

	public Hand() {
		cards = new LinkedList<>();
	}

	public void add(Card card) {
		cards.add(card);
	}

	public Card getCard(int index) {
		return cards.get(index);
	}

	public int size() {
		return cards.size();
	}

	/**
	 * @return true if there is a basic pokemon in the hand; false otherwise
	 */
	public boolean hasBasic() {
		boolean basicExists = false;
		Iterator<Card> iterator = cards.iterator();
		do {
			Card curr = iterator.next();
			if (curr.getCardType() == CardType.POKEMON && ((Pokemon)curr).getStage() == Stage.BASIC) {
				basicExists = true;
			}
		} while (iterator.hasNext());
		return basicExists;
	}


}
