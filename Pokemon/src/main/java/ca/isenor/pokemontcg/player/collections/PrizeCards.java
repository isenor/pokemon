package ca.isenor.pokemontcg.player.collections;

import java.util.ArrayList;

import ca.isenor.pokemontcg.player.cards.Card;
/**
 * The set of prize cards that a player draws from when
 * the opponents pokemon is knocked out.
 *
 * @author dawud
 *
 */
public class PrizeCards {
	private ArrayList<Card> prizes;
	private int maxPrizes;

	public PrizeCards() {
		maxPrizes = 6;
		prizes = new ArrayList<>(maxPrizes);
	}

	/**
	 * @param size specify the starting number of prize cards
	 */
	public PrizeCards(int size) {
		maxPrizes = size;
		prizes = new ArrayList<>(maxPrizes);
	}

	public void add(Card card) {
		prizes.add(card);
	}

	public int getMaxPrizes() {
		return maxPrizes;
	}
	/**
	 * Pick an index for a prize
	 *
	 * @param index the prize card to pick
	 * @return a new prize
	 */
	public Card pick(int index) {
		return prizes.remove(index);
	}
}