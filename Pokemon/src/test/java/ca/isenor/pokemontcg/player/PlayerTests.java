package ca.isenor.pokemontcg.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import ca.isenor.pokemontcg.cards.Card;
import ca.isenor.pokemontcg.cards.Stage;
import ca.isenor.pokemontcg.cards.Type;
import ca.isenor.pokemontcg.cards.pokemon.Pokemon;
import ca.isenor.pokemontcg.player.collections.Deck;

public class PlayerTests {

	private Deck mockDeck;
	private Player player;

	@Before
	public void setUp() {
		mockDeck = EasyMock.mock(Deck.class);
		player = new Player("Player", mockDeck);
	}

	@Test
	public void testConstructor() {
		assertNotNull(player);
	}

	@Test
	public void testDraw() {
		EasyMock.expect(mockDeck.draw()).andReturn(EasyMock.mock(Card.class)).times(1);

		EasyMock.replay(mockDeck);
		player.draw();
		assertEquals(1,player.getHand().size());
		EasyMock.verify(mockDeck);
	}

	@Test
	public void testOpeningHand() {
		Player partMockPlayer = PowerMock.createPartialMock(Player.class, "draw");
		partMockPlayer.draw();
		PowerMock.expectLastCall().times(7);

		PowerMock.replay(partMockPlayer);
		partMockPlayer.openingHand();
		PowerMock.verify(partMockPlayer);
	}

	@Test
	public void testSetPrizeCards() {

	}

	@Test
	public void testGetSetActive() {
		Pokemon pokemon = EasyMock.mock(Pokemon.class);
		player.setActive(pokemon);
		assertEquals(pokemon, player.getActive());
	}

	@Test
	public void testGetName() {
		assertEquals("Player",player.getName());
	}

	/**
	 * Fake Pokemon for testing purposes
	 *
	 * @author dawud
	 */
	private class Fakeon extends Pokemon {
		public Fakeon() {
			super("Fakeon", 120, 3, Type.FIGHTING, Type.FAIRY, Type.NONE,
					Stage.STAGE1);
		}
	}

	@Test
	public void testDrawReturnValue() {
		Pokemon fakeon = new Fakeon();
		EasyMock.expect(mockDeck.draw()).andReturn(fakeon);

		EasyMock.replay(mockDeck);
		Card card = player.draw();
		assertEquals(card,fakeon);
		EasyMock.verify(mockDeck);
	}

}
