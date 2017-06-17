package ca.isenor.pokemontcg.cards.pokemon;

import java.io.Serializable;

import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;

/**
 * Contains the key static details included on a Pokemon card
 *
 * @author dawud
 *
 */
public class PokemonCardDetails implements Serializable {
	private static final long serialVersionUID = 7284896455835940597L;

	private int hitPoints;
	private int retreatCost;
	private Type type;
	private Type weakness;
	private Type resistance;
	private Stage stage;

	public PokemonCardDetails(
			int hitPoints,
			int retreatCost,
			Type type,
			Type weakness,
			Type resistance,
			Stage stage) {
		this.hitPoints = hitPoints;
		this.retreatCost = retreatCost;
		this.type = type;
		this.weakness = weakness;
		this.resistance = resistance;
		this.stage = stage;
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

	public Stage getStage() {
		return stage;
	}
}
