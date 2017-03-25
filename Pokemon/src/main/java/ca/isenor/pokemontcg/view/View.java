package ca.isenor.pokemontcg.view;

import ca.isenor.pokemontcg.model.Model;
import ca.isenor.pokemontcg.player.Player;
import ca.isenor.pokemontcg.player.cards.Card;
import ca.isenor.pokemontcg.player.collections.Hand;

/**
 * The View is responsible for presenting the output from the model
 *
 * @author dawud
 *
 */
public class View {
	private Model gameModel;

	public View(Model gameModel) {
		this.gameModel = gameModel;
	}

	//	/**
	//	 * Replace the current game model with this updated version.
	//	 *
	//	 * @param gameModel
	//	 */
	//	public void update(Model gameModel) {
	//		this.gameModel = gameModel;
	//	}

	/**
	 * Display the field of the current model,
	 * including the active and benched pokemon, and the current players hand.
	 */
	public void displayField() {
		Player pA = gameModel.getPlayerA();
		Player pB = gameModel.getPlayerB();
		//displayCard(pA.getActive());
		displayHand(pA.getHand());
		displayHand(pB.getHand());
		displayCard(pA.getActive());
	}

	/**
	 * Display the names of the cards in the current player's hand.
	 *
	 * @param hand
	 */
	private void displayHand(Hand hand) {
		System.out.println();
		for(int i = 0; i < hand.size(); i++ ) {
			System.out.println("(" + i + ") " +
					hand.getCard(i).getName() + " " + hand.getCard(i).getCardType());
		}
	}

	private void displayCard(Card card) {
		System.out.println(card);
	}
}
