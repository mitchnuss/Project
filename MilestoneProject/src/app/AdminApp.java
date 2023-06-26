package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Class to act as Client to initiate connection to server
 * 
 * @author mitch 6/15/23
 *
 */
public class AdminApp {

	/**
	 * variables for connections
	 */
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	/**
	 * method to start client
	 * 
	 * @param ip   ip address connected to
	 * @param port por connected to
	 * @throws UnknownHostException handles unknown hosts
	 * @throws IOException          handles exceptions
	 */
	public void start(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public String sendMessage(String command, String payload) throws IOException {
		out.println(command);
		out.println(payload);

		return in.readLine();
	}

	/**
	 * properly closes connection
	 * 
	 * @throws IOException handles exceptions
	 */
	public void cleanUp() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	/**
	 * Driver
	 * 
	 * @param args params
	 * @throws InterruptedException throws exceptions
	 * @throws IOException          throws exception
	 */
	public static void main(String[] args) throws InterruptedException, IOException {

		AdminApp client = new AdminApp();
		client.start("127.0.0.1", 6666);

		Scanner scnr = new Scanner(System.in);
		boolean running = true;

		while (running) {
			System.out.println("Enter a command (U for update, R for retrieve) or 'exit' to quit:");
			String input = scnr.nextLine();

			if (input.equalsIgnoreCase("U")) {
				System.out.println("Enter the payload for update command:");
				String payload = scnr.nextLine();
				String response = client.sendMessage("U", payload);
				System.out.println("Server response:" + response);
			} else if (input.equalsIgnoreCase("R")) {
				String response = client.sendMessage("R", "");
				System.out.println("Server response: " + response);
			} else if (input.equalsIgnoreCase("exit")) {
				running = false;
			} else {
				System.out.println("Invalid command.");
			}
		}
		client.cleanUp();
		scnr.close();
	}
}
