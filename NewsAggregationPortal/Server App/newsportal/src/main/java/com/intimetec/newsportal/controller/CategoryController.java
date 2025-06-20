package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsportal.intimetec.com/")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/admin/category")
    public void createCategory(@RequestBody CreateCategoryDTO createCategoryDTO){
        categoryService.createCategory(createCategoryDTO);
    }
}
