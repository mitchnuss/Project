package app;

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FilterInputStream;

/**
 * Store front with status and driver
 * 
 * @author mitch Ability to view, purchase and return merchandise
 *
 */
public class StoreFront {
	/**
	 * setting variables to access the different classes used for methods in the
	 * driver
	 */
	static InventoryManager inventoryManager = new InventoryManager();
	static ShoppingCart shoppingCart = new ShoppingCart();

	static FilterInputStream filterInputStream = new FilterInputStream(System.in) {
		public void close() throws IOException {

		}
	};
	static Scanner scnr = new Scanner(filterInputStream);
	/**
	 * Server parameters
	 */
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private int port;

	/**
	 * @param port
	 */
	public StoreFront(int port) {
		this.port = port;
	}

	/**
	 * method to view the products available for purchase
	 */
	private static void viewProducts() {
		inventoryManager.sortInventoryByName();
		inventoryManager.displaySalableProducts();
	}

	/**
	 * method to purchase the products the user inputs
	 */
	private static void purchaseProducts() {
		System.out.print("Enter the name of the product you want to purchase: ");
		scnr.nextLine(); // Consume newline character
		String productName = scnr.nextLine();

		SalableProduct productToPurchase = inventoryManager.findProductByName(productName);
		if (productToPurchase == null) {
			System.out.println("Product not found.");
		} else {
			if (productToPurchase.getQty() > 0) {
				shoppingCart.addItem(productToPurchase);
				productToPurchase.setQty(productToPurchase.getQty() - 1);
				productToPurchase.setQty1(1);
				System.out.println("Product purchased successfully.");
			} else {
				System.out.println("Product is out of stock.");
			}
		}

	}

	/**
	 * Method to view items in cart
	 */
	private static void viewCart() {
		List cart = shoppingCart.getItems();
		System.out.println(cart);
	}

	/**
	 * method to return the products the user inputs
	 */
	private static void returnProducts() {
		shoppingCart.returnProduct(null);
	}

	/**
	 * Method to start server
	 * 
	 * @param port port that is open for clients to connect to
	 * @throws IOException handles exceptions
	 */
	public void start(int port) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("StoreFront server started on port " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New Connection from " + clientSocket.getInetAddress());

				ServerThread serverThread = new ServerThread(clientSocket);
				serverThread.run();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update inventory from client
	 * 
	 * @param payload update inventory
	 */
	public void updateInventory(String payload) {
		try {
			JSONObject jsonPayload = new JSONObject(payload);
			JSONArray productsArray = jsonPayload.getJSONArray("products");

			for (int i = 0; 0 < productsArray.length(); i++) {
				JSONObject productObj = productsArray.getJSONObject(i);
				String name = productObj.getString("name");
				String description = productObj.getString("description");
				double price = productObj.getDouble("price");
				int qty = productObj.getInt("qty");

				SalableProduct existingProduct = inventoryManager.findProductByName(name);
				if (existingProduct != null) {
					existingProduct.setDescription(description);
					existingProduct.setPrice(price);
					existingProduct.setQty(qty);
				} else {
					SalableProduct newProduct = new SalableProduct(name, description, price, qty);
					inventoryManager.addProduct(newProduct);
				}

			}
			System.out.println("Inventory updated successfully.");
		} catch (JSONException e) {
			System.out.println("Invalid payload format. Update failed");
		}
	}

	/**
	 * properly closes server
	 * 
	 * @throws IOException handles exceptions
	 */
	public void cleanUp() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}

	/**
	 * handles multiple connections
	 * 
	 * @author mitch 6/25/23
	 *
	 */
	private class ServerThread extends Thread {
		private Socket clientSocket;

		public ServerThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		/**
		 * handles threads from admin service while running store front
		 */
		@Override
		public void run() {
			try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
				String command = in.readLine();
				String payload = in.readLine();

				if (command.equals("U")) {
					updateInventory(payload);
					out.println("Inventory updated successfully.");
				} else if (command.equals("R")) {
					// To Do create logic here for R command
				} else {
					out.println("Invalid Command");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * main driver program
	 * 
	 * @param args program to call different classes and provide a store front to
	 *             shop for items and purchase them.
	 */
	public static void main(String[] args) throws IOException {

		while (true) {
			System.out.println("Login to Admin site (1) or go to store front (2)?");

			int choice = scnr.nextInt();

			switch (choice) {
			case 1:
				StoreFront server = new StoreFront(0);
				server.start(6666);
				server.updateInventory(null);

				break;
			case 2:
				inventoryManager.initializeInventoryFromFile("inventory.json");
				System.out.println("Welcome to the Game Store \n");
				System.out.println("Please select an option from the menu");
				System.out.println("***************************************");
				System.out.println("************MAIN MENU ******************\n");
				while (true) {
					System.out.println(
							"Enter one of the following:\n\n" + "'1' : View Products\n" + "'2' : Purchase Products\n"
									+ "'3' : View Cart\n" + "'4' : Return Products\n" + "'5' : Exit");

					int menuChoice = scnr.nextInt();

					switch (menuChoice) {
					case 1:
						viewProducts();
						break;
					case 2:
						purchaseProducts();
						break;
					case 3:
						viewCart();
						break;
					case 4:
						returnProducts();
						break;
					case 5:
						System.out.println("Thank you for coming by. Please come again.");
						System.exit(0);
						break;
					default:
						System.out.println("INVALID OPTION. Please select an option 1-4");
						scnr.close();
						break;
					}
				} // server.cleanUp();
			}
		}
	}
}