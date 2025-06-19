package com.InventorySystem.service;

import com.InventorySystem.dao.ProductDAO;
import com.InventorySystem.dto.ProductDTO;
import com.InventorySystem.exception.DatabaseException;
import com.InventorySystem.exception.DuplicateIdException;
import com.InventorySystem.exception.ResourceNotFoundException;
import com.InventorySystem.mapper.ProductMapper;
import com.InventorySystem.model.Product;
import com.InventorySystem.service.impl.ProductWriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductWriteServiceImplTest {

    @InjectMocks
    private ProductWriteServiceImpl productWriteService;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ProductMapper productMapper;

    private ProductDTO sampleProductDTO;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProductDTO = new ProductDTO();
        sampleProductDTO.setId(1);
        sampleProductDTO.setName("Laptop");
        sampleProductDTO.setQuantity(10);

        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setName("Laptop");
        sampleProduct.setQuantity(10);
    }


    @Test
    void addProduct_shouldSaveProduct_whenProductIdDoesNotExist() {
        when(productDAO.findById(1)).thenReturn(Optional.empty());
        when(productMapper.toEntity(sampleProductDTO)).thenReturn(sampleProduct);

        assertDoesNotThrow(() -> productWriteService.addProduct(sampleProductDTO));
        verify(productDAO).save(sampleProduct);
    }

    @Test
    void addProduct_shouldThrowDuplicateIdException_whenProductIdExists() {
        when(productDAO.findById(1)).thenReturn(Optional.of(sampleProduct));

        assertThrows(DuplicateIdException.class, () -> productWriteService.addProduct(sampleProductDTO));
        verify(productDAO, never()).save(any());
    }

    @Test
    void addProduct_shouldThrowDatabaseException_whenDAOThrowsException() {
        when(productDAO.findById(1)).thenThrow(new DatabaseException("DB error"));

        assertThrows(DatabaseException.class, () -> productWriteService.addProduct(sampleProductDTO));
    }

    @Test
    void updateProduct_shouldUpdateProduct_whenProductExists() {
        when(productDAO.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productMapper.toEntity(any())).thenReturn(sampleProduct);

        assertDoesNotThrow(() -> productWriteService.updateProduct(1, sampleProductDTO));
        verify(productDAO).save(sampleProduct);
    }

    @Test
    void updateProduct_shouldThrowResourceNotFoundException_whenProductDoesNotExist() {
        when(productDAO.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productWriteService.updateProduct(1, sampleProductDTO));
    }

    @Test
    void updateProduct_shouldThrowDatabaseException_whenDAOThrowsException() {
        when(productDAO.findById(1)).thenThrow(new DatabaseException("DAO error"));

        assertThrows(DatabaseException.class, () -> productWriteService.updateProduct(1, sampleProductDTO));
    }

    @Test
    void updateStock_shouldUpdateStock_whenProductExists() {
        when(productDAO.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productMapper.toDTO(sampleProduct)).thenReturn(sampleProductDTO);
        when(productMapper.toEntity(sampleProductDTO)).thenReturn(sampleProduct);

        assertDoesNotThrow(() -> productWriteService.updateStock(1, 50));
        verify(productDAO).save(sampleProduct);
    }

    @Test
    void updateStock_shouldThrowResourceNotFoundException_whenProductDoesNotExist() {
        when(productDAO.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productWriteService.updateStock(1, 50));
    }

    @Test
    void updateStock_shouldThrowDatabaseException_whenDAOThrowsException() {
        when(productDAO.findById(1)).thenThrow(new DatabaseException("DB error"));

        assertThrows(DatabaseException.class, () -> productWriteService.updateStock(1, 50));
    }
}

