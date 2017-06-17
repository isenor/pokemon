package ca.isenor.pokemontcg.cards.pokemon;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.CardType;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.Energy;
import ca.isenor.pokemontcg.cards.energy.EnergyAmount;

public abstract class Pokemon implements Card, Serializable {
	private static final long serialVersionUID = 5432201733525003969L;

	private static final CardType cardType = CardType.POKEMON;

	private String name;
	private PokemonCardDetails details;
	private int damage;
	private List<Energy> attachedEnergy;
	private boolean knockedOut;
	private String[] attackNames;

	public Pokemon(String name,
			PokemonCardDetails details,
			String... attackNames) {
		this.name = name;
		this.damage = 0;
		attachedEnergy = new LinkedList<>();
		knockedOut = false;
		this.attackNames = attackNames;
		this.details = details;
	}

	@Override
	public CardType getCardType() {
		return cardType;
	}

	@Override
	public String getName() {
		return name;
	}

	public PokemonCardDetails getDetails() {
		return details;
	}

	public int getDamage() {
		return damage;
	}

	public void retreat() {
		// check energy levels
	}

	public EnergyAmount getRetreatCost() {
		EnergyAmount energy = new EnergyAmount();
		energy.setEntry(Type.COLORLESS, details.getRetreatCost());
		return energy;
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

	public Energy removeEnergy(int energyIndex) {
		return attachedEnergy.remove(energyIndex);
	}

	public Energy getEnergy(int energyIndex) {
		return attachedEnergy.get(energyIndex);
	}

	public List<Energy> getEnergyList() {
		return new LinkedList<>(attachedEnergy);
	}

	public void setEnergyList(List<Energy> list) {
		attachedEnergy = new LinkedList<>(list);
	}

	public int getEnergyListSize() {
		return attachedEnergy.size();
	}

	public boolean isKnockedOut() {
		return knockedOut;
	}

	public void setKnockedOut(boolean knockedOut) {
		this.knockedOut = knockedOut;
	}

	public boolean checkEnergy(EnergyAmount energy) {
		EnergyAmount thisPokemon = new EnergyAmount(attachedEnergy);
		return thisPokemon.meetsRequirementsOf(energy);
	}

	public String getAttackName(int attackNumber) {
		return attackNames[attackNumber];
	}

	public int getNumberOfAttacks() {
		return attackNames.length;
	}

	public abstract void attack(int attackNumber, Pokemon opponent);

	public abstract EnergyAmount getAttackCost(int attackNumber);

	@Override
	public String toString() {
		return name + " [" + (details.getHitPoints() - damage) + "/" + details.getHitPoints() + "]";
	}

	@Override
	public String longDescription() {
		StringBuilder attacks = new StringBuilder();
		for (String AttackName : attackNames) {
			attacks.append(AttackName + "\n");
		}
		return details.getStage() +  " " + details.getType() + " " + name + "[" + (details.getHitPoints() - damage) + "/" + details.getHitPoints() + "]\n" +
		attacks + "\nweakness: " + details.getWeakness() + "\nresistance: " + details.getResistance() +
		"\nretreat cost: " + details.getRetreatCost() + "\nAttached energy:\n" + attachedEnergy;
	}
}
