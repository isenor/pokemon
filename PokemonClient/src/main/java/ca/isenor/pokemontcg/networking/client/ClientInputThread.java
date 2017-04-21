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
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			String userInput;
			boolean finished = false;
			// This while loop accepts commands from the user/player.
			// It handles the commands as required, almost always forwarding
			// the command off to the server.
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
				else if (userInput.equals("hand")) {
					out.println(userInput);
				}
				else {
					out.println("Unrecognised command: " + userInput);
				}

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Input thread closed");
	}
}