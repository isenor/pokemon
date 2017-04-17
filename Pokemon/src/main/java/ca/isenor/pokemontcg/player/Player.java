package ca.isenor.pokemontcg.player;

import java.io.Serializable;

import ca.isenor.pokemontcg.player.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.player.collections.Bench;
import ca.isenor.pokemontcg.player.collections.Deck;
import ca.isenor.pokemontcg.player.collections.DiscardPile;
import ca.isenor.pokemontcg.player.collections.Hand;
import ca.isenor.pokemontcg.player.collections.PrizeCards;
/**
 * Player object models a pokemon trainer, holds everything a player owns on the table
 *
 * @author dawud
 *
 */
public class Player implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1672656679446606781L;
	private String name;
	private Deck deck;
	private Hand hand;
	private PrizeCards prizeCards;
	private Pokemon active;
	private Bench bench;
	private DiscardPile discard;

	public Player(String name, Deck deck) {
		this.name = name;
		this.deck = deck;
		hand = new Hand();
		prizeCards = new PrizeCards();
		bench = new Bench();
		discard = new DiscardPile();
	}

	public void openingHand() {
		draw();
		draw();
		draw();
		draw();
		draw();
		draw();
		draw();
	}

	public PrizeCards getPrizeCards() {
		return prizeCards;
	}

	public void setPrizeCards() {
		for (int i = 0; i < prizeCards.getMaxPrizes(); i++) {
			prizeCards.add(deck.draw());
		}
	}


	public Hand getHand() {
		return hand;
	}

	public Pokemon getActive() {
		return active;
	}

	public void setActive(Pokemon active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	/**
	 * Takes a card from the top of the deck and
	 * puts it into the players hand (at the end).
	 */
	public void draw() {
		hand.add(deck.draw());
	}

	public Deck getDeck() {
		return deck;
	}

}
