package com.InventorySystem.service;


import com.InventorySystem.dao.ProductDAO;
import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.exception.DatabaseException;
import com.InventorySystem.exception.ResourceNotFoundException;
import com.InventorySystem.mapper.ProductMapper;
import com.InventorySystem.model.Product;
import com.InventorySystem.service.impl.ProductViewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductViewServiceImplTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductViewServiceImpl productViewService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setName("Test Product");
        product.setQuantity(10);

        productDTO = new ProductDTO();
        productDTO.setId(1);
        productDTO.setName("Test Product");
        productDTO.setQuantity(10);
    }

    @Test
    void getAllProduct_shouldReturnListOfProductDTOs() {
        List<Product> products = List.of(product);
        when(productDAO.getAll()).thenReturn(products);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        List<ProductDTO> result = productViewService.getAllProduct();

        assertEquals(1, result.size());
        assertEquals(productDTO.getId(), result.get(0).getId());
        verify(productDAO).getAll();
        verify(productMapper).toDTO(product);
    }

    @Test
    void getAllProduct_whenDatabaseFails_shouldThrowDatabaseException() {
        when(productDAO.getAll()).thenThrow(new DatabaseException("DB down"));

        DatabaseException exception = assertThrows(DatabaseException.class,
                () -> productViewService.getAllProduct());

        assertEquals("Issue occured while retrieving all products!", exception.getMessage());
    }

    @Test
    void getProductById_shouldReturnProductDTO() {
        when(productDAO.findById(1)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productViewService.getProductById(1);

        assertEquals(productDTO.getName(), result.getName());
        verify(productDAO).findById(1);
        verify(productMapper).toDTO(product);
    }

    @Test
    void getProductById_whenNotFound_shouldThrowResourceNotFoundException() {
        when(productDAO.findById(2)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productViewService.getProductById(2));

        assertEquals("Product with id 2 is not Found", exception.getMessage());
    }

    @Test
    void getProductById_whenDAOThrows_shouldThrowDatabaseException() {
        when(productDAO.findById(1)).thenThrow(new DatabaseException("DB Error"));

        DatabaseException exception = assertThrows(DatabaseException.class,
                () -> productViewService.getProductById(1));

        assertEquals("DB Error", exception.getMessage());
    }
}
