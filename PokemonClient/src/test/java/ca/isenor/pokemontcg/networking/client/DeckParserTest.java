package ca.isenor.pokemontcg.networking.client;

import java.util.Scanner;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ca.isenor.pokemontcg.player.collections.Deck;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Scanner.class, DeckParser.class})
public class DeckParserTest {
	DeckParser parser;

	@Before
	public void testConstructor() {
		parser = new DeckParser();
		Assert.assertNotNull(parser);
	}

	@Test
	public void testEmptyDeck() throws Exception {
		Scanner deckScanner = PowerMock.createMock(Scanner.class);

		EasyMock.expect(deckScanner.hasNext()).andReturn(false);

		PowerMock.replay(deckScanner);
		Deck emptyDeck = parser.parse(deckScanner);
		Assert.assertEquals(0,emptyDeck.size());
		PowerMock.verify(deckScanner);
	}

	@Test
	public void testDeckWithOneCard() throws Exception {
		Scanner deckScanner = PowerMock.createStrictMock(Scanner.class);
		EasyMock.expect(deckScanner.hasNext()).andReturn(true);
		EasyMock.expect(deckScanner.nextInt()).andReturn(1);
		EasyMock.expect(deckScanner.skip(" ")).andReturn(deckScanner);
		EasyMock.expect(deckScanner.nextLine()).andReturn("Charmander");
		EasyMock.expect(deckScanner.hasNext()).andReturn(false);

		PowerMock.replay(deckScanner);
		Deck deck = parser.parse(deckScanner);
		Assert.assertEquals("Charmander",deck.draw().getName());
		PowerMock.verify(deckScanner);
	}

	@Test
	public void testDeckWithMultipleCardsOfSameType() throws Exception {
		Scanner deckScanner = PowerMock.createStrictMock(Scanner.class);
		EasyMock.expect(deckScanner.hasNext()).andReturn(true);
		EasyMock.expect(deckScanner.nextInt()).andReturn(4);
		EasyMock.expect(deckScanner.skip(" ")).andReturn(deckScanner);
		EasyMock.expect(deckScanner.nextLine()).andReturn("Squirtle");
		EasyMock.expect(deckScanner.hasNext()).andReturn(false);

		PowerMock.replay(deckScanner);
		Deck deck = parser.parse(deckScanner);
		Assert.assertEquals(4,deck.size());
		Assert.assertEquals("Squirtle",deck.draw().getName());
		PowerMock.verify(deckScanner);
	}

	@Test
	public void testDeckWithMultipleCardsOfDifferentTypes() throws Exception {
		Scanner deckScanner = PowerMock.createStrictMock(Scanner.class);
		EasyMock.expect(deckScanner.hasNext()).andReturn(true);
		EasyMock.expect(deckScanner.nextInt()).andReturn(1);
		EasyMock.expect(deckScanner.skip(" ")).andReturn(deckScanner);
		EasyMock.expect(deckScanner.nextLine()).andReturn("Fennekin").times(1);
		EasyMock.expect(deckScanner.hasNext()).andReturn(true);
		EasyMock.expect(deckScanner.nextInt()).andReturn(4);
		EasyMock.expect(deckScanner.skip(" ")).andReturn(deckScanner);
		EasyMock.expect(deckScanner.nextLine()).andReturn("Froakie");
		EasyMock.expect(deckScanner.hasNext()).andReturn(false);

		PowerMock.replay(deckScanner);
		Deck deck = parser.parse(deckScanner);
		Assert.assertEquals(5,deck.size());
		Assert.assertEquals("Fennekin",deck.draw().getName());
		Assert.assertEquals("Froakie",deck.draw().getName());
		PowerMock.verify(deckScanner);
	}
}
