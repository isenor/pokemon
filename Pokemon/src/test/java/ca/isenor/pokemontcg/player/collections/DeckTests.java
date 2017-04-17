package ca.isenor.pokemontcg.player.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import ca.isenor.pokemontcg.player.cards.Card;

public class DeckTests {

	private Deck deck;

	@Before
	public void setUp() {
		deck = new Deck();
		Assert.assertNotNull(deck);

	}

	@Test
	public void testShuffle() {
		int deckSize = 60;
		Card[] mockCards = new Card[deckSize];
		for(int i = 0; i < deckSize; i++) {
			mockCards[i] = EasyMock.mock(Card.class);
			deck.putOnTop(mockCards[i]);
		}
		deck.shuffle();
		LinkedList<Card> cards = Whitebox.getInternalState(deck, "cards");
		for (Card card: mockCards) {
			assertTrue(cards.contains(card));
		}
		assertEquals(60,deck.size());
	}

	@Test
	public void testDraw() {
		Card card = EasyMock.mock(Card.class);
		deck.putOnBottom(card);
		assertSame(card,deck.draw());
	}

	//	@Test
	//	public void testIsValidPositive() {
	//		int deckSize = 60;
	//		for(int i = 0; i < deckSize; i++) {
	//			deck.putOnBottom(EasyMock.mock(Card.class));
	//		}
	//		deck.isValid()
	//
	//	}

}
