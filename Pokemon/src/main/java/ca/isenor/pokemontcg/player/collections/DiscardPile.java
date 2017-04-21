package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.LinkedList;

import ca.isenor.pokemontcg.cards.Card;

public class DiscardPile implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4646026323344798122L;
	private LinkedList<Card> pile;

	public DiscardPile() {
		pile = new LinkedList<>();
	}

	public void add(Card card) {
		pile.add(card);
	}
}
