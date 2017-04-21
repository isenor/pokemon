package ca.isenor.pokemontcg.player.collections;

import java.io.Serializable;
import java.util.LinkedList;

import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
/**
 * The benched pokemon that a trainer has access to.
 * <p> Initial max size of 5 pokemon.
 * @author dawud
 *
 */
public class Bench implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4685486583007035960L;
	private LinkedList<Pokemon> bench;
	private int maxSize;

	public Bench() {
		bench = new LinkedList<>();
		maxSize = 5;
	}

	public void add(Pokemon pokemon) {
		if (bench.size() < maxSize) {
			bench.add(pokemon);
		}
	}

	//figure out a way to determine how to choose an element from the list
	public Pokemon remove(int index) {
		return bench.remove(index);
	}
}
