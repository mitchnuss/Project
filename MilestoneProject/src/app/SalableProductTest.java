/**
 * 
 */
package app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author mitch
 *
 */
class SalableProductTest {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		SalableProduct product = new SalableProduct();
		product.setName("Test");
		assertEquals("Test", product.getName());
	}
	
	@ParameterizedTest
	 @CsvSource({
	     "apple, 1",
	     "banana, 2",
	     "'lemon, lime', 0xF1",
	     "strawberry, 700_000"
	 })
	void test1(String product, int qty) {
		SalableProduct prod = new SalableProduct();
		prod.setName(product);
		 assertEquals(product, prod.getName());
	}

}
