package ca.isenor.pokemontcg.networking.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import ca.isenor.pokemontcg.player.Player;

public class ServerPlayerThread extends Thread {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private PlayerTurnController controller;
	private int playerNumber;

	private String cmd = "onionsauce";

	public ServerPlayerThread(Socket socket, PlayerTurnController controller, int playerNumber) {
		super("ServerPlayerThread");
		this.socket = socket;
		this.controller = controller;
		this.playerNumber = playerNumber;
	}

	@Override
	public void run() {

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			objectInput = new ObjectInputStream(socket.getInputStream());
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			String inputLine;
			//out.print("Contact with server established. You are player number " +	(playerNumber + 1) + ". Awaiting player data.");
			//System.out.print("Contact with server established. You are player number " + (playerNumber + 1) + ". Awaiting player data.");

			controller.setPlayer((Player) objectInput.readObject(), playerNumber);

			out.println("Welcome Pokemon Trainer " + controller.getPlayer(playerNumber).getName() + "!");
			out.println(controller.getPlayer(playerNumber).getDeck());

			synchronized (controller) {
				if (playerNumber == 0) {
					out.println("Waiting for another player to join...");
					controller.wait();

					out.println("Another player has joined.");
				}
				else {
					out.println("Letting the other player know you're here");
					controller.notifyAll();
				}

				ServerInputThread chat = new ServerInputThread(controller,playerNumber);
				chat.start();

				boolean finished = false;
				while(!finished && controller.getPlayerThread((playerNumber + 1) % 2) != null) {
					inputLine = controller.takeTurn(playerNumber);

					if (inputLine.equals("end")) {
						finished = true;
					}
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	/**
	 * Get the PrintWriter of this thread so that messages can be
	 * directly passed.
	 *
	 * @return the PrintWriter of this thread
	 */
	public PrintWriter getOut() {
		return out;
	}

	public BufferedReader getIn() {
		return in;
	}

	public ObjectInputStream getObjectInput() {
		return objectInput;
	}

	public ObjectOutputStream getObjectOutput() {
		return objectOutput;
	}
}
