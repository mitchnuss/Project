package app;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InventoryManager <T extends SalableProduct> {
	/**
	 * sets products to the list of salable products
	 */
	private List<T> products;

	/**
	 * Array to hold the items available for sale
	 */
	public InventoryManager() {
		products = new ArrayList<>();
	}

	/**
	 * add product to the array
	 * 
	 * @param product place holder of product name assigned
	 */
	public void addProduct(T product) {
		products.add(product);
	}

	/**
	 * holds the inventory in the array
	 * 
	 * @return the products available in invenotry
	 */
	public List<T> getInventory() {
		return products;
	}

	/**
	 * assigns the product to be removed
	 * 
	 * @param product assigns the product to be removed
	 */
	public void removeProduct(T product) {
		products.remove(product);
	}

	/**
	 * searches through the array of products and if it equals user input
	 * 
	 * @param name place holder for input name
	 * @return returns the name of the product user input
	 */
	public T findProductByName(String name) {
		for (T product : products) {
			if (product.getName().equalsIgnoreCase(name)) {
				return product;
			}
		}
		return null;
	}

	/**
	 * method to display the products with price and quantity
	 */
	public void displaySalableProducts() {
		for (T product : products) {
			System.out.println(product.getName() + " - " + product.getDescription() + " - Price: $" + product.getPrice()
					+ " - Quantity: " + product.getQty());
		}
	}

	/**
	 * method that sorts inventory - not in use yet
	 */
	public void sortInventoryByName() {
	    Collections.sort(products, Comparator.comparing(SalableProduct::getName, String.CASE_INSENSITIVE_ORDER));
	}
	/**
	 * Initializes inventory from JSON file to print in console
	 * @param filename name of JSON file passed to this parameter
	 */
	public void initializeInventoryFromFile(String filename) {
		 ArrayList<T> products = (ArrayList<T>) FileService.loadProductsFromFile("inventory.json");

		for (T product : products) {
			addProduct( product);
			
		}
	}
}