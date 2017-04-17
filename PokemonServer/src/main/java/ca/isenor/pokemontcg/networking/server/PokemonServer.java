package ca.isenor.pokemontcg.networking.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PokemonServer {
	private static final int MAX_PARTICIPANTS = 2;

	private PokemonServer() {}

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("Usage: java PokemonServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		int playerCount = 0;
		PlayerTurnController controller;
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			Socket socket;
			while (true) {
				controller = new PlayerTurnController();
				while (playerCount < MAX_PARTICIPANTS) {
					socket = serverSocket.accept();
					ServerPlayerThread player = new ServerPlayerThread(socket, controller, playerCount);
					player.start();
					String message = controller.addPlayerThread(player);
					System.err.println(message);
					playerCount++;
				}
				playerCount = 0;
			}

		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
}
