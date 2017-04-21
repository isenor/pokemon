package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardType;
import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;

/**
 * Represents the hand of a player. It's a list of cards, essentially.
 */
public class Hand implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5307908749382498143L;
	private LinkedList<Card> cards;

	public Hand() {
		cards = new LinkedList<>();
	}

	/**
	 * Add a card to the player's hand.
	 * @param card - the card to be added
	 */
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
	 * At the beginning of the game, a player must select a basic pokemon from his/her hand to
	 * make his/her starting active pokemon. Therefore, a player's opening hand is required to
	 * have at least one basic pokemon in it. This method checks to see if such a pokemon exists.
	 *
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

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int i = 1;
		for (Card card: cards) {
			str.append(i + ": " + card + "\n");
			i++;
		}
		return str.toString();
	}
}