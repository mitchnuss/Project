package app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Class to read JSON files and save to it
 * @author mitch 6/4/23
 *
 */
public class FileService <T extends SalableProduct> {
	
	/**
	 * Method to load products from JSON file
	 * @param filename name of the JSON file being used and parameter that holds the info
	 * @return returns contents of file
	 */
    public static ArrayList<SalableProduct> loadProductsFromFile(String filename) {
        // Read the JSON file and parse it
        // Create instances of SalableProduct based on the parsed data
        // Return the list of SalableProduct objects
    	
    		ArrayList<SalableProduct> products = new ArrayList<SalableProduct>();
    		try {
    			File file = new File(filename);
    			Scanner scnr = new Scanner(file);
    			ObjectMapper objectMapper = new ObjectMapper();
    			
    			while (scnr.hasNext()) {
    				String json = scnr.nextLine();
    				SalableProduct product = objectMapper.readValue(json, SalableProduct.class);
    				products.add(product);
    			}
    			scnr.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} return products;
    	}
    
    /**
     * Method to save info to a JSON file
     * @param filename name of the JSON file being used and parameter that holds the info
     * @param product The different products being loaded
     * @param append add to end of file. 
     */
   public static void saveToFile(String filename, SalableProduct product, boolean append) {
	   PrintWriter pw;
	   
	   try {
		   File file = new File(filename);
		   FileWriter fw = new FileWriter(file, append);
		   pw = new PrintWriter(fw);
		   
		   ObjectMapper objectMapper = new ObjectMapper();
		   String json = objectMapper.writeValueAsString(product);
		   pw.println(json);
		   
		   pw.close();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }
}

