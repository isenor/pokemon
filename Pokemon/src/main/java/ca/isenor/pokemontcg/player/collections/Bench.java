package ca.isenor.pokemontcg.player.collections;

import java.util.LinkedList;

import ca.isenor.pokemontcg.player.cards.pokemon.Pokemon;
/**
 * The benched pokemon that a trainer has access to.
 * <p> Initial max size of 5 pokemon.
 * @author dawud
 *
 */
public class Bench {
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
