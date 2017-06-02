package ca.isenor.pokemontcg.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Creates a new thread so that messages can be sent out while waiting for input
 *
 * @author dawud
 */
public class ClientInputThread extends Thread {
	private PrintWriter out;

	public ClientInputThread(PrintWriter out) {
		super("Input Thread");
		this.out = out;
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
				out.println(userInput);
				if ("quit".equals(userInput)) {
					finished = true;
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Input thread closed");
	}
}