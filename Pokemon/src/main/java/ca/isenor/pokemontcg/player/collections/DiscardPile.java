package ca.isenor.pokemontcg.player.collections;

import java.util.LinkedList;

import ca.isenor.pokemontcg.player.cards.Card;

public class DiscardPile {
	private LinkedList<Card> pile;

	public DiscardPile() {
		pile = new LinkedList<>();
	}

	public void add(Card card) {
		pile.add(card);
	}
}
