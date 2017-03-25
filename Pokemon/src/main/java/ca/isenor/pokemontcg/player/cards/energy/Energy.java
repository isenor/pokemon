package ca.isenor.pokemontcg.player.cards.energy;

import ca.isenor.pokemontcg.player.cards.Card;
import ca.isenor.pokemontcg.player.cards.CardType;
import ca.isenor.pokemontcg.player.cards.Type;

public abstract class Energy implements Card {
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
}