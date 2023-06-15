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
	
	public String sendMessage(String msg) throws IOException {
		out.println(msg);
		
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
		
		client.cleanUp();
	}

}
