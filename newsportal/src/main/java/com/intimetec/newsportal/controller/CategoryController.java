package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/newsportal.intimetec.com/")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/admin/category")
    public void createCategory(@RequestBody CreateCategoryDTO createCategoryDTO){
        categoryService.createCategory(createCategoryDTO);
    }

    @PostMapping("/admin/hide/{categoryId}")
    public ResponseEntity<?> hideCategory(@PathVariable Integer categoryId) {
        categoryService.hideCategory(categoryId);
        return new ResponseEntity<>(Map.of("Message","Category Hidden Successfully!"), HttpStatus.OK);
    }
}
