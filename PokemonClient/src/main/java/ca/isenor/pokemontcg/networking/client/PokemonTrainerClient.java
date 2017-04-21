package ca.isenor.pokemontcg.networking.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import ca.isenor.pokemontcg.networking.client.exceptions.DeckParseException;
import ca.isenor.pokemontcg.player.Player;
import ca.isenor.pokemontcg.player.collections.Deck;

public class PokemonTrainerClient {
	/**
	 * Hidden constructor
	 */
	private PokemonTrainerClient() {}

	/**
	 * Set up the player object with input from the player.
	 * @param stdIn
	 * @return
	 */
	private static Player playerSetup(BufferedReader stdIn) {

		try {
			System.out.println("Enter your name as you would like it to appear:");
			String playerName = stdIn.readLine();
			System.out.println("Enter a filename for a deck:");
			Deck playerDeck = new DeckParser().parse(new Scanner(new File(stdIn.readLine())));
			return new Player(playerName,playerDeck);
		} catch(IOException | DeckParseException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Main Client class - this is what the player will run.
	 * @param args <host name> <port number>
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println(
					"Usage: java PokemonTrainerClient <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		Player player = null;
		while (player == null) {
			player = playerSetup(stdIn);
		}

		try (
				Socket socket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
				) {

			System.out.println("Sending player data.");
			objectOut.writeObject(player);
			//System.out.println("Awaiting message from server.");
			String fromServer;// = in.readLine();
			//System.out.println("Input received.");
			// Wait for initial message from server
			//System.out.println("Server: " + in.readLine());

			ClientInputThread it = new ClientInputThread(out, objectOut);
			it.start();

			boolean finished = false;
			// This while loop expects commands and messages from the server.
			while (!finished && (fromServer = in.readLine()) != null) {
				if ("end".equals(fromServer)) {
					System.out.println("Ending session...");
					it.interrupt();
					finished = true;
				}
				else if ("hand".equals(fromServer)) {
					System.out.println("Cards in hand:");
					while (in.ready()) {
						System.out.println(in.readLine());
					}
					//Hand hand = (Hand)objectIn.readObject();
					//System.out.println(hand);
				}
				else {
					System.out.println("Server: " + fromServer);
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
		System.out.println("Bye bye!");
	}
}