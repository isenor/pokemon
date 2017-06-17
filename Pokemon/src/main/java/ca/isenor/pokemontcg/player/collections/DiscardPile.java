package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.LinkedList;

import ca.isenor.pokemontcg.cards.Card;

public class DiscardPile implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4646026323344798122L;
	private LinkedList<Card> cards;

	public DiscardPile() {
		cards = new LinkedList<>();
	}

	public void add(Card card) {
		cards.add(card);
	}

	@Override
	public String toString() {
		return cards.toString();
	}
}
