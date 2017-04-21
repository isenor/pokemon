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

	public Energy(String name, Type type, boolean basic) {
		this.name = name;
		this.type = type;
		this.basic = basic;
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

	@Override
	public String toString() {
		return "Energy [name=" + name + "]";
	}
}