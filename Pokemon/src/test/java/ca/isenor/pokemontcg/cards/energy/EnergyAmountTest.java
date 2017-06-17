package ca.isenor.pokemontcg.cards.energy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.grass.BasicGrassEnergy;

/**
 * Test the meetsRequirementsOf method
 *
 * @author dawud
 *
 */
public class EnergyAmountTest {
	@Test
	public void testRequirementsForTrue() {
		EnergyAmount pokemonEnergy = new EnergyAmount();
		pokemonEnergy.setEntry(Type.GRASS, 3);

		EnergyAmount requirement = new EnergyAmount();
		requirement.setEntry(Type.GRASS, 3);

		assertTrue(pokemonEnergy.meetsRequirementsOf(requirement));
	}

	@Test
	public void testRequirementsForFalse() {
		EnergyAmount pokemonEnergy = new EnergyAmount();
		pokemonEnergy.setEntry(Type.FIRE, 2);

		EnergyAmount requirement = new EnergyAmount();
		requirement.setEntry(Type.FIRE, 3);

		assertFalse(pokemonEnergy.meetsRequirementsOf(requirement));
	}

	@Test
	public void testRequirementsForFalseMixedType() {
		EnergyAmount pokemonEnergy = new EnergyAmount();
		pokemonEnergy.setEntry(Type.PSYCHIC, 2);
		pokemonEnergy.setEntry(Type.FIGHTING, 1);

		EnergyAmount requirement = new EnergyAmount();
		requirement.setEntry(Type.PSYCHIC, 2);
		requirement.setEntry(Type.FIGHTING, 2);

		assertFalse(pokemonEnergy.meetsRequirementsOf(requirement));
	}

	@Test
	public void testRequirementsForTrueMixedType() {
		EnergyAmount pokemonEnergy = new EnergyAmount();
		pokemonEnergy.setEntry(Type.LIGHTNING, 3);
		pokemonEnergy.setEntry(Type.DARKNESS, 1);

		EnergyAmount requirement = new EnergyAmount();
		requirement.setEntry(Type.LIGHTNING, 1);
		requirement.setEntry(Type.DARKNESS, 1);

		assertTrue(pokemonEnergy.meetsRequirementsOf(requirement));
	}

	@Test
	public void testRequirementsForTrueColorless() {
		EnergyAmount pokemonEnergy = new EnergyAmount();
		pokemonEnergy.setEntry(Type.LIGHTNING, 3);
		pokemonEnergy.setEntry(Type.FIRE, 1);

		EnergyAmount requirement = new EnergyAmount();
		requirement.setEntry(Type.LIGHTNING, 2);
		requirement.setEntry(Type.COLORLESS, 2);

		assertTrue(pokemonEnergy.meetsRequirementsOf(requirement));
	}

	@Test
	public void testRequirementsForFalseColorless() {
		EnergyAmount pokemonEnergy = new EnergyAmount();
		pokemonEnergy.setEntry(Type.LIGHTNING, 2);
		pokemonEnergy.setEntry(Type.FIRE, 1);

		EnergyAmount requirement = new EnergyAmount();
		requirement.setEntry(Type.LIGHTNING, 2);
		requirement.setEntry(Type.COLORLESS, 2);

		assertFalse(pokemonEnergy.meetsRequirementsOf(requirement));
	}

	@Test
	public void testRequirementsForTrueColorlessSimple() {
		EnergyAmount pokemonEnergy = new EnergyAmount();
		pokemonEnergy.setEntry(Type.GRASS, 4);

		EnergyAmount requirement = new EnergyAmount();
		requirement.setEntry(Type.GRASS, 2);
		requirement.setEntry(Type.COLORLESS, 2);

		assertTrue(pokemonEnergy.meetsRequirementsOf(requirement));
	}

	@Test
	public void testListConstructor() {
		List<Energy> energyList = new LinkedList<>();
		energyList.add(new BasicGrassEnergy());
		EnergyAmount amount = new EnergyAmount(energyList);
		assertEquals(1,amount.getEntry(Type.GRASS));
		assertEquals(0,amount.getEntry(Type.FIGHTING));
	}
}
