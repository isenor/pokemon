package ca.isenor.pokemontcg.cards.energy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import ca.isenor.pokemontcg.cards.Type;

/**
 * Represents how much energy is required for an attack, etc.
 *
 * @author dawud
 *
 */
public class EnergyAmount {
	private static final Type ORDERED_TYPES[] = new Type[] {Type.GRASS, Type.FIRE, Type.WATER,
			Type.LIGHTNING, Type.PSYCHIC, Type.FIGHTING,Type.DARKNESS, Type.METAL, Type.FAIRY,
			Type.DRAGON, Type.COLORLESS};
	private Map<Type, Integer> energyMap;

	public EnergyAmount() {
		energyMap = new EnumMap<>(Type.class);

		for (Type type : ORDERED_TYPES) {
			energyMap.put(type,0);
		}
	}

	public EnergyAmount(EnergyAmount other) {
		this.energyMap = new EnumMap<>(Type.class);
		for (Type type : ORDERED_TYPES) {
			this.setEntry(type, other.getEntry(type));
		}
	}

	public EnergyAmount(final List<Energy> energyList) {
		this();
		for (Energy energy : energyList) {
			if (energyMap.containsKey(energy.getType())) {
				energyMap.put(energy.getType(),energyMap.get(energy.getType()) + energy.getAmount());
			}
			else {
				energyMap.put(energy.getType(),energy.getAmount());
			}

		}
	}

	public void setEntry(Type type, int amount) {
		energyMap.put(type,amount);
	}

	public int getEntry(Type type) {
		return energyMap.get(type);
	}

	/**
	 * Check whether there is enough energy in <b>this</b> to meet the requirements of <b>other</b>
	 * @param other
	 * @return
	 */
	public boolean meetsRequirementsOf(EnergyAmount other) {
		for (Type type : ORDERED_TYPES) {
			int diff = this.energyMap.get(type) - other.energyMap.get(type);
			if (diff > 0) {
				this.energyMap.put(Type.COLORLESS, this.energyMap.get(Type.COLORLESS) + diff);
			}
			else if (diff < 0) {
				return false;
			}

		}
		return true;
	}

}
