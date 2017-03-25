package ca.isenor.pokemontcg.player.cards;

public interface Card {
	String getName();
	CardType getCardType();
	@Override
	public String toString();
}