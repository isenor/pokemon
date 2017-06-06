package ca.isenor.pokemontcg.player;

import java.io.Serializable;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
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

	/**
	 * Draw 7 cards from the top of the player's deck to initialize the opening hand.
	 * @see Deck
	 * @see Deck#draw()
	 * @see Player#draw()
	 */
	public void openingHand() {
		draw();
		draw();
		draw();
		draw();
		draw();
		draw();
		draw();
	}

	/**
	 * Put a player's hand back into the deck (on bottom)
	 */
	public void putHandIntoDeck() {
		while (hand.size() > 0) {
			deck.putOnBottom(hand.remove(hand.size()-1));
		}
	}

	public PrizeCards getPrizeCards() {
		return prizeCards;
	}

	public void setPrizeCards() {
		for (int i = 0; i < prizeCards.getMaxPrizes(); i++) {
			prizeCards.add(deck.draw());
		}
	}


	/**
	 * The reference to the player's hand
	 * @return Hand
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * The reference to the player's active pokemon
	 * @return Pokemon
	 */
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
	 * Takes the top Card from the player's deck and
	 * puts it into the players hand (at the end).
	 *
	 * @return the card that was drawn
	 */
	public Card draw() {
		Card card = deck.draw();
		hand.add(card);
		return card;
	}

	/**
	 * The reference to the player's deck
	 * @return Deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * The reference to the player's bench
	 *
	 * @return Bench
	 */
	public Bench getBench() {
		return bench;
	}

	/**
	 * Set the bench reference for a player
	 *
	 * @param bench
	 */
	public void setBench(Bench bench) {
		this.bench = bench;
	}
}
