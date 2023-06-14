package app;

/**
 * Products you are able to sell in the store
 * @author mitch 05/16/23
 *
 */
public class SalableProduct {
	/**
	 * placeholder for items to sell in store
	 */
	private String name;
	private String description;
	private double price;
	private int qty;
	/**
	 * name of item
	 * @return the name placeholder for name of item
	 */
	public String getName() {
		return name;
	}

	/**
	 * constructor
	 * @param name placeholder for when name is called
	 * @param description placeholder for description
	 * @param price placeholder for price
	 * @param qty placeholder for quantity
	 */
	public SalableProduct(String name, String description, double price, int qty) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.qty = qty;
	}
	/**
	 * displays name of description
	 * @return the description called from main driver
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets name of product
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * sets description of product
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * placeholder for price
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * displays price of item
	 * @return the price called from main driver
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * displays qty
	 * @return the qty called from main driver
	 */
	public int getQty() {
		return qty;
	}
	/*
	 * sets the qty of item of product
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	/**
	 * 
	 */
	public SalableProduct() {
		name = "";
		description = "";
		price = 0;
		qty = 0;
	}
}