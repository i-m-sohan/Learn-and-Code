package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.CategoryDTO;
import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/newsportal.intimetec.com/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryDTO createCategoryDTO){
        categoryService.createCategory(createCategoryDTO);
        return new ResponseEntity<>(Map.of("Message","Category Created Successfully!"), HttpStatus.CREATED);
    }

    @PostMapping("/hide/{categoryId}")
    public ResponseEntity<?> hideCategory(@PathVariable Integer categoryId) {
        categoryService.hideCategory(categoryId);
        return new ResponseEntity<>(Map.of("Message","Category Hidden Successfully!"), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories(){
        List<CategoryDTO> categoryDTOList =  categoryService.getAllCategories();
        return new ResponseEntity<>(Map.of("categories",categoryDTOList), HttpStatus.OK);
    }
}
