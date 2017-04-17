package ca.isenor.pokemontcg.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import ca.isenor.pokemontcg.player.Player;
import ca.isenor.pokemontcg.player.collections.Deck;

/**
 * Creates a new thread so that messages can be sent out while waiting for input
 *
 * @author dawud
 */
public class ClientInputThread extends Thread {
	private BufferedReader stdIn;
	private PrintWriter out;
	private ObjectOutputStream objectOut;

	private Player player;

	public ClientInputThread(PrintWriter out, ObjectOutputStream objectOut) {
		super("Input Thread");
		this.out = out;
		this.objectOut = objectOut;
	}

	@Override
	public void run() {
		try {
			stdIn = new BufferedReader(new InputStreamReader(System.in));


			String userInput;
			boolean finished = false;
			while(!finished && (userInput = stdIn.readLine()) != null) {
				if (userInput.startsWith("chat")) {
					out.println(userInput);
				}
				else if (userInput.equals("end")) {
					out.println(userInput);
					finished = true;
				}
				else if (userInput.startsWith("load")) {
					out.println(userInput);
					Deck deck = new Deck();
					System.out.println(deck);
					objectOut.writeObject(deck);

				}
				else {
					out.println(userInput);
				}

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Input thread closed");
	}
}