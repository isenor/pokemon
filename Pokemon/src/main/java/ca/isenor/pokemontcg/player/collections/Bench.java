package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
/**
 * The benched Pokemon that a trainer has access to.
 * <p> Initial max size of 5 Pokemon.
 * @author dawud
 *
 */
public class Bench implements Serializable {
	private static final long serialVersionUID = -4685486583007035960L;

	private List<Pokemon> cards;
	private int maxSize;

	public Bench() {
		cards = new LinkedList<>();
		maxSize = 5;
	}

	/**
	 * Add a Pokemon to the player's bench. This method checks the max hand size before adding.
	 *
	 * @param pokemon
	 * @return true if the Pokemon was added to the bench; false otherwise
	 */
	public boolean add(Pokemon pokemon) {
		if (cards.size() < maxSize) {
			return cards.add(pokemon);
		}
		return false;
	}

	/**
	 * Gets the Pokemon without removing it from the bench
	 *
	 * @param index
	 * @return the selected Pokemon
	 */
	public Pokemon get(int index) {
		return cards.get(index);
	}

	/**
	 * Removes and gets the Pokemon at index in the bench
	 *
	 * @param index
	 * @return the selected Pokemon
	 */
	public Pokemon remove(int index) {
		return cards.remove(index);
	}

	/**
	 *
	 * @return the number of Pokemon on the bench
	 */
	public int size() {
		return cards.size();
	}

	public int maxSize() {
		return maxSize;
	}

	public boolean isEmpty() {
		return cards.size() == 0;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (cards.isEmpty()) {
			return "0: -----";
		}
		for (int i = 1; i <= cards.size(); i++) {
			str.append(i + ": " + cards.get(i-1));
			if (i != cards.size()) {
				str.append("\n");
			}
		}
		return str.toString();
	}
}
