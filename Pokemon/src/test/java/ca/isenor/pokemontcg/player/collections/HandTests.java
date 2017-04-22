package ca.isenor.pokemontcg.player.collections;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.energy.fire.BasicFireEnergy;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;

public class HandTests {

	Hand hand;

	private class GenericBasic extends Pokemon {

		public GenericBasic() {
			super("GenericBasic", 100, 3, Type.COLORLESS, Type.COLORLESS, Type.COLORLESS, Stage.BASIC);
		}
	}

	private class GenericNonBasic extends Pokemon {
		public GenericNonBasic() {
			super("GenericNonBasic", 100, 3, Type.COLORLESS, Type.COLORLESS, Type.COLORLESS, Stage.STAGE1);
		}
	}

	@Before
	public void setUp() {
		hand = new Hand();
	}

	@Test
	public void testHasBasicOnEmpty() {
		assertFalse(hand.hasBasic());
	}

	@Test
	public void testHasBasicNegative() {
		hand.add(new BasicFireEnergy());
		assertFalse(hand.hasBasic());
	}

	@Test
	public void testHasBasicPositive() {
		hand.add(new GenericBasic());
		assertTrue(hand.hasBasic());
	}

	@Test
	public void testHasBasicNegativeWithNonBasic() {
		hand.add(new GenericNonBasic());
		assertFalse(hand.hasBasic());
	}

	@Test
	public void testHasBasicNegativeMixedNonBasic() {
		hand.add(new GenericNonBasic());
		hand.add(new BasicFireEnergy());
		hand.add(new GenericNonBasic());
		hand.add(new BasicFireEnergy());
		hand.add(new GenericNonBasic());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		assertFalse(hand.hasBasic());

	}

	@Test
	public void testHasBasicPositiveExtended1() {
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new GenericBasic());
		assertTrue(hand.hasBasic());
	}

	@Test
	public void testHasBasicPositiveExtended2() {
		hand.add(new GenericBasic());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		assertTrue(hand.hasBasic());
	}

	@Test
	public void testHasBasicPositiveExtended3() {
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new GenericBasic());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		hand.add(new BasicFireEnergy());
		assertTrue(hand.hasBasic());
	}

	@Test
	public void testHasBasicPositiveWithNonBasic() {
		hand.add(new BasicFireEnergy());
		hand.add(new GenericNonBasic());
		hand.add(new BasicFireEnergy());
		hand.add(new GenericBasic());
		hand.add(new BasicFireEnergy());
		hand.add(new GenericNonBasic());
		hand.add(new BasicFireEnergy());
		assertTrue(hand.hasBasic());
	}

	@Test
	public void testGetter() {
		Card generic = new GenericBasic();
		hand.add(generic);
		assertEquals(hand.getCard(0),generic);

	}

	@Test
	public void testSizeEmpty() {
		assertEquals(0,hand.size());
	}

	@Test
	public void testSizeNonEmpty() {
		hand.add(new BasicFireEnergy());
		hand.add(new GenericNonBasic());
		hand.add(new GenericBasic());
		assertEquals(3,hand.size());
	}

	@Test
	public void removeACard() {
		Card card = new GenericNonBasic();
		hand.add(card);
		assertSame(card,hand.getCard(0));
	}
}
