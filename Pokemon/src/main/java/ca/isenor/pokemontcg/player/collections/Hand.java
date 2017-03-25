package ca.isenor.pokemontcg.player.collections;

import java.util.LinkedList;

import ca.isenor.pokemontcg.player.cards.Card;

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
}
