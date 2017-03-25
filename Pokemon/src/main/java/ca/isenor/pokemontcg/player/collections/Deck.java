package ca.isenor.pokemontcg.player.collections;

import java.util.LinkedList;
import java.util.Random;

import ca.isenor.pokemontcg.player.cards.Card;
/**
 * Holds the list of cards a player draws from.
 *
 * @author dawud
 *
 */
public class Deck {
	private LinkedList<Card> deck;

	public Deck() {
		deck = new LinkedList<>();
	}

	/**
	 * Randomizes the order of the cards in the deck.
	 */
	public void shuffle() {
		Random gen = new Random();
		for(int i = 0 ; i < deck.size() * 100 ; i++) {
			int index = gen.nextInt(deck.size());
			putOnTop(remove(index));
		}
	}

	private Card remove(int index) {
		return deck.remove(index);
	}

	public void putOnTop(Card card) {
		deck.addFirst(card);
	}

	public void putOnBottom(Card card) {
		deck.addLast(card);
	}

	/**
	 * Check if the deck is valid, i.e. no more than 4 of a card etc.
	 * @return true if the deck follows the rules; false otherwise
	 */
	public boolean isValid() {
		return deck.size() > 7;
	}

	/**
	 * @return a card from the top of the deck
	 */
	public Card draw() {
		return deck.removeFirst();
	}
}
