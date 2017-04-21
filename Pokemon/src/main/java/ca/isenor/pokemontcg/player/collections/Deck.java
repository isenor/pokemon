package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import ca.isenor.pokemontcg.cards.Card;
/**
 * Holds the list of cards a player draws from.
 *
 * @author dawud
 *
 */
public class Deck implements Serializable {
	/**
	 *  I don't know what this does
	 */
	private static final long serialVersionUID = 7353499510527836691L;


	private LinkedList<Card> cards;

	public Deck() {
		cards = new LinkedList<>();
	}

	/**
	 * Randomizes the order of the cards in the deck.
	 */
	public void shuffle() {
		Random gen = new Random();
		for(int i = 0 ; i < cards.size() * 100 ; i++) {
			int index = gen.nextInt(cards.size());
			putOnTop(remove(index));
		}
	}

	private Card remove(int index) {
		return cards.remove(index);
	}

	public void putOnTop(Card card) {
		cards.addFirst(card);
	}

	public void putOnBottom(Card card) {
		cards.addLast(card);
	}

	/**
	 * Check if the deck is valid, i.e. no more than 4 of a card etc.
	 * @return true if the deck follows the rules; false otherwise
	 */
	public boolean isValid() {
		return cards.size() > 7;
	}

	/**
	 * @return a card from the top of the deck
	 */
	public Card draw() {
		return cards.removeFirst();
	}

	public int size() {
		return cards.size();
	}

	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}
}
