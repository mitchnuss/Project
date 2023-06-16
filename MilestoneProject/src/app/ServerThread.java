package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	/**
	 * Constructor
	 * @param clientSocket
	 */
	public ServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String command;
			while ((command = in.readLine()) != null) {
				String response = processCommand(command);
				out.println(response);
			}
			
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String processCommand(String command) {
		
		return "Command received: " + command;
	}
	
	
	
}
