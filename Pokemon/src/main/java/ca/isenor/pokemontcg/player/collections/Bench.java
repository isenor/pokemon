package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.LinkedList;

import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
/**
 * The benched Pokemon that a trainer has access to.
 * <p> Initial max size of 5 Pokemon.
 * @author dawud
 *
 */
public class Bench implements Serializable {
	private static final long serialVersionUID = -4685486583007035960L;

	private LinkedList<Pokemon> bench;
	private int maxSize;

	public Bench() {
		bench = new LinkedList<>();
		maxSize = 5;
	}

	/**
	 * Add a Pokemon to the player's bench. This method checks the max hand size before adding.
	 *
	 * @param pokemon
	 * @return true if the Pokemon was added to the bench; false otherwise
	 */
	public boolean add(Pokemon pokemon) {
		if (bench.size() < maxSize) {
			return bench.add(pokemon);
		}
		return false;
	}

	/**
	 * Removes and gets the Pokemon at index in the bench
	 *
	 * @param index
	 * @return the selected Pokemon
	 */
	public Pokemon remove(int index) {
		return bench.remove(index);
	}

	/**
	 *
	 * @return the number of Pokemon on the bench
	 */
	public int size() {
		return bench.size();
	}

	public int maxSize() {
		return maxSize;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (bench.isEmpty()) {
			return "0: -----";
		}
		for (int i = 1; i <= bench.size(); i++) {
			str.append(i + ": " + bench.get(i-1));
			if (i != bench.size()) {
				str.append("\n");
			}
		}
		return str.toString();
	}
}
