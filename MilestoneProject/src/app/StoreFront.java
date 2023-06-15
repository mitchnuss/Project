package app;

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Store front with status and driver
 * @author mitch Ability to view, purchase and return merchandise
 *
 */
public class StoreFront {
	/**
	 * setting variables to access the different classes used for methods in the driver
	 */
	static InventoryManager inventoryManager = new InventoryManager();
	static ShoppingCart shoppingCart = new ShoppingCart();
	static Scanner scnr = new Scanner(System.in);
//	static FileService fileService = new FileService();
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	/**
	 * method to view the products available for purchase
	 */
	private static void viewProducts() { 
		inventoryManager.displaySalableProducts();
	}
	/**
	 * method to purchase the products the user inputs
	 */
	private static void purchaseProducts() {
        System.out.print("Enter the name of the product you want to purchase: ");
 //       scnr.nextLine(); // Consume newline character
        String productName = scnr.nextLine();

        SalableProduct productToPurchase = inventoryManager.findProductByName(productName);
        if (productToPurchase == null) {
            System.out.println("Product not found.");
        } else {
            if (productToPurchase.getQty() > 0) {
                shoppingCart.addItem(productToPurchase);
                productToPurchase.setQty(productToPurchase.getQty() - 1);
                System.out.println("Product purchased successfully.");
            } else {
                System.out.println("Product is out of stock.");
            }
        }
		
	}
	/**
	 * method to return the products the user inputs
	 */
	private static void returnProducts() {
		shoppingCart.returnProduct(null);
	}
	
	public void start(int port) throws IOException {
		System.out.println("Waiting for Client connection.........");
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();
		
		System.out.println("Received connection on port: " + clientSocket.getLocalPort());
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public void cleanUp() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}

/**
 * main driver program
 * @param args program to call different classes
 * and provide a store front to shop for items
 * and purchase them.
 */
	public static void main(String[] args) throws IOException{

/*		
 * old method of manually adding inventory
		inventoryManager.addProduct(new Food("Goji Berry", "bright orange-red berry that comes from a shrub native to Asia", 8.32, 496));
		inventoryManager.addProduct(new Potion("Elixir", "Potion that grants eternal life", 3469.00, 4));
		inventoryManager.addProduct(new Helmet("Kabuto", "Ancient Japanese war helemt", 999.99, 43));
		inventoryManager.addProduct(new Tomahawk("Polished Tomahawk", "Benjamin Martins Tomahawk", 567.00, 1));
		inventoryManager.addProduct(new Shield("Heater Shield", "Used in the Middle Ages by knights and soldiers", 269.00, 57));
		inventoryManager.addProduct(new Sword("Katana", "single-edged sword used by Japanese samurai", 4420.00, 21));
*/		
		inventoryManager.initializeInventoryFromFile("inventory.json");
		System.out.println("Welcome to the Game Store \n");
		System.out.println("Please select an option from the menu");
		System.out.println("***************************************");
        System.out.println("************MAIN MENU ******************\n");
		while (true) {
			System.out.println("Enter one of the following:\n\n"+
			"'1' : View Products\n"+
			"'2' : Purchase Products\n"+
			"'3' : Return Products\n"+
			"'4' : Exit");
			
			int menuChoice = scnr.nextInt();
			
			switch(menuChoice) {
			case 1:
                viewProducts();
				break;
			case 2:
				purchaseProducts();
				break;
			case 3:
				returnProducts();
				break;
			case 4:
				System.out.println("Thank you for coming by. Please come again.");
				System.exit(0);
				break;
				
			} //scnr.close();
		}
	}
}
 