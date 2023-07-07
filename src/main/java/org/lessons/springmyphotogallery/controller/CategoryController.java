package org.lessons.springmyphotogallery.controller;

import org.lessons.springmyphotogallery.model.Category;
import org.lessons.springmyphotogallery.repository.CategoryRepository;
import org.lessons.springmyphotogallery.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    // metodo che restituisce la lista delle categorie
    @GetMapping
    public String index(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);
        return "categories/index";
    }
}
