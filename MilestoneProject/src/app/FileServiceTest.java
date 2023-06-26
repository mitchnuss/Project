package app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileServiceTest {
    private static final String TEST_FILE_NAME = "test.json";

    @Test
    public void testLoadProductsFromFile() throws IOException {
       
        createTestJsonFile();

        
        List<SalableProduct> products = FileService.loadProductsFromFile(TEST_FILE_NAME);

        
        Assertions.assertEquals(2, products.size());

        
        SalableProduct product1 = products.get(0);
        Assertions.assertEquals("Product 1", product1.getName());
        Assertions.assertEquals("Description 1", product1.getDescription());
        Assertions.assertEquals(9.99, product1.getPrice());
        Assertions.assertEquals(10, product1.getQty());

        
        SalableProduct product2 = products.get(1);
        Assertions.assertEquals("Product 2", product2.getName());
        Assertions.assertEquals("Description 2", product2.getDescription());
        Assertions.assertEquals(19.99, product2.getPrice());
        Assertions.assertEquals(5, product2.getQty());
    }


    private void createTestJsonFile() throws IOException {
        // Create a test JSON file with product data
        File file = new File(TEST_FILE_NAME);
        FileWriter writer = new FileWriter(file);
        writer.write("{\"name\":\"Product 1\",\"description\":\"Description 1\",\"price\":9.99,\"qty\":10}\n");
        writer.write("{\"name\":\"Product 2\",\"description\":\"Description 2\",\"price\":19.99,\"qty\":5}\n");
        writer.close();
    }
    
}

