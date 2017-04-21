package ca.isenor.pokemontcg.cards;

public interface Card {
	String getName();
	CardType getCardType();
	@Override
	public String toString();
}