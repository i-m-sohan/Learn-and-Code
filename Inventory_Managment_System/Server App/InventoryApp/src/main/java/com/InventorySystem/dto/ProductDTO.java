package com.InventorySystem.dto;

import com.InventorySystem.validate.OnCreate;
import com.InventorySystem.validate.OnStockUpdate;
import com.InventorySystem.validate.OnUpdate;
import jakarta.validation.constraints.*;

public class ProductDTO {

    @Null(groups = OnUpdate.class, message = "id should not be included in request body")
    @Min(value = 0, groups = OnCreate.class)
    @NotNull(groups = OnCreate.class, message = "id must not be null while adding product")
    private Integer id;

    @NotBlank(groups = {OnCreate.class,OnUpdate.class}, message = "name must not be blank while adding product")
    @NotNull(groups = {OnCreate.class,OnUpdate.class}, message = "name must not be null while adding product")
    private String name;

    @Min(value = 0, groups = {OnCreate.class,OnUpdate.class,OnStockUpdate.class})
    @NotNull(groups = {OnCreate.class,OnUpdate.class}, message = "id must not be null while adding product")
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
