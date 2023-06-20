package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Shopping Cart Class to hold products
 * 
 * @author mitch Array to hold products so user can get and set products also
 *         add and remove
 *
 */
public class ShoppingCart {
	/**
	 * list that holds all the products being sold
	 */
	private ArrayList<SalableProduct> salableProduct;

	/**
	 * Assigns items in array to variable
	 */
	public ShoppingCart() {
		salableProduct = new ArrayList<>();
	}
	/**
	 * adds item to products for sale
	 * @param item place holder for name of product added
	 */
    public void addItem(SalableProduct item) {
        salableProduct.add(item);
    }
    /**
     * removes item from inventory or cart
     * @param item place holder for name of product removed
     */
    public void removeItem(SalableProduct item) {
        salableProduct.remove(item);
    }
    /**
     * method to check out and add total with qty - to do
     */
    public void checkout() {
        // Perform checkout logic here
    }
    /**
     * gets list of products for sale
     * @return will return all products available for sale and inventory
     */
    public List<SalableProduct> getItems() {
        return salableProduct;
    }
    /**
     * return product to store - remove from cart
     * @param name of product user input
     * @return will return the name of the product removed
     */
    public SalableProduct returnProduct(String name) {
    	Scanner scnr = new Scanner(System.in);
        System.out.println("Enter the name of the product you want to return: ");
        String returnProd = scnr.nextLine();

        SalableProduct productReturned = returnProduct(returnProd);
        if (productReturned == null) {
            System.out.println("Item not in cart");
        } else {
            removeItem(productReturned);
            addItem(productReturned);
            System.out.println(productReturned.getName() + " successfully returned");
        }scnr.close();
		return null;
    }

}