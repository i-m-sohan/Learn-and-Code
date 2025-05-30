package com.InventorySystem.dao;

import com.InventorySystem.dao.impl.InMemoryProductDAO;
import com.InventorySystem.exception.DatabaseException;
import com.InventorySystem.model.Product;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductDAOTest {

    private InMemoryProductDAO productDAO;

    @BeforeEach
    void setUp() {
        productDAO = new InMemoryProductDAO();
    }

    @AfterEach
    void resetStaticMap() throws Exception {
        Field field = InMemoryProductDAO.class.getDeclaredField("idToProductMap");
        field.setAccessible(true);
        field.set(null, new HashMap<>());
    }

    @Test
    void testSaveAndFindById_ShouldReturnProduct() {
        Product product = new Product();
        product.setId(1001);
        product.setName("Monitor");
        product.setQuantity(5);

        productDAO.save(product);

        Optional<Product> result = productDAO.findById(1001);

        assertTrue(result.isPresent());
        assertEquals("Monitor", result.get().getName());
        assertEquals(5, result.get().getQuantity());
    }

    @Test
    void testFindById_WhenNotPresent_ShouldReturnEmptyOptional() {
        Optional<Product> result = productDAO.findById(9999);
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAll_ShouldReturnAllProducts() {
        Product p1 = new Product();
        p1.setId(2001);
        p1.setName("Tablet");
        p1.setQuantity(20);

        Product p2 = new Product();
        p2.setId(2002);
        p2.setName("Charger");
        p2.setQuantity(40);

        productDAO.save(p1);
        productDAO.save(p2);

        List<Product> allProducts = productDAO.getAll();

        assertTrue(allProducts.contains(p1));
        assertTrue(allProducts.contains(p2));
    }

    @Test
    void testFindById_WhenExceptionOccurs_ShouldThrowDatabaseException() throws Exception {
        Field field = InMemoryProductDAO.class.getDeclaredField("idToProductMap");
        field.setAccessible(true);
        field.set(null, null);

        assertThrows(DatabaseException.class, () -> {
            productDAO.findById(3001);
        });
    }
}
