package ca.isenor.pokemontcg.networking.client.exceptions;

/**
 * An exception to throw if the deck parser is unable to relate input to a card value.
 *
 * @author dawud
 *
 */
public class DeckParseException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -3119328774466245184L;

	public DeckParseException() {
		super();
	}

	public DeckParseException(String message) {
		super(message);
	}
}
