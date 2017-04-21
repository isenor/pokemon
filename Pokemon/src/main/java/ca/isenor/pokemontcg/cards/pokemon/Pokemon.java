package ca.isenor.pokemontcg.cards.pokemon;

import java.io.Serializable;
import java.util.LinkedList;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardType;
import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.Energy;

public abstract class Pokemon implements Card, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5432201733525003969L;

	private static final CardType cardType = CardType.POKEMON;

	private String name;
	private int hitPoints;
	private int damage;
	private int retreatCost;
	private Type type;
	private Type weakness;
	private Type resistance;
	private Stage stage;
	private LinkedList<Energy> attachedEnergy;

	public Pokemon(String name,
			int hitPoints,
			int retreatCost,
			Type type,
			Type weakness,
			Type resistance,
			Stage stage) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.damage = 0;
		this.type = type;
		this.retreatCost = retreatCost;
		this.weakness = weakness;
		this.resistance = resistance;
		this.stage = stage;
		attachedEnergy = new LinkedList<>();
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getRetreatCost() {
		return retreatCost;
	}

	public void setRetreatCost(int retreatCost) {
		this.retreatCost = retreatCost;
	}

	public Type getType() {
		return type;
	}


	@Override
	public CardType getCardType() {
		return cardType;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getWeakness() {
		return weakness;
	}

	public void setWeakness(Type weakness) {
		this.weakness = weakness;
	}

	public Type getResistance() {
		return resistance;
	}

	public void setResistance(Type resistance) {
		this.resistance = resistance;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public Stage getStage() {
		return stage;
	}

	public void retreat() {
		// check energy levels
	}

	public void doDamage(Pokemon pokemon, int damage) {
		pokemon.takeDamage(damage);
	}

	public void takeDamage(int damage) {
		if (damage >= 0) {
			this.damage += damage;
		}
	}

	public void attachEnergy(Energy energy) {
		attachedEnergy.add(energy);
	}

	@Override
	public String toString() {
		return name + " " + (hitPoints - damage) + "/" + hitPoints;
	}
}
