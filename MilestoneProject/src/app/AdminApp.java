package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Class to act as Client to initiate connection to server
 * @author mitch 6/15/23
 *
 */
public class AdminApp {
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
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
	
	public void cleanUp() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public static void main(String[] args) throws InterruptedException, IOException{
		
		AdminApp client = new AdminApp();
		client.start("127.0.0.1", 6666);
		
		String updateCmd = "U";
		String updatePyld = "{\"products\": [{\"name\": \"Product1\", \"description\": \"Description1\", \"price\": 10.0, \"qty\": 5}, {\"name\": \"Product2\", \"description\": \"Description2\", \"price\": 15.0, \"qty\": 3}]}";
	    String updateResponse = client.sendMessage(updateCmd, updatePyld);
	    System.out.println("Updated Inventory: " + updateResponse);
	    
	    String retrieveCmd = "R";
	    String retrievePyld = "";
	    String retrieveResponse = client.sendMessage(retrieveCmd, retrievePyld);
	    System.out.println("Retrieved Inventory" + retrieveResponse);
		
		client.cleanUp();
	}

}
