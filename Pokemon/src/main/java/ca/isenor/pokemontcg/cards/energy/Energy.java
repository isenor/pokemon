package ca.isenor.pokemontcg.cards.energy;

import java.io.Serializable;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardType;
import ca.isenor.pokemontcg.cards.Type;

public abstract class Energy implements Card,Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9197228726946238989L;

	private static final CardType cardType = CardType.ENERGY;

	private String name;
	private Type type;
	private boolean basic;
	private int amount;

	public Energy(String name, Type type, boolean basic) {
		this.name = name;
		this.type = type;
		this.basic = basic;
		this.amount = 1;
	}

	public Energy(String name, Type type, boolean basic, int amount) {
		this.name = name;
		this.type = type;
		this.basic = basic;
		this.amount = amount;
	}

	@Override
	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	@Override
	public CardType getCardType() {
		return cardType;
	}

	public boolean isBasic() {
		return basic;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String longDescription() {
		return toString();
	}
}