package org.lessons.springmyphotogallery.controller;

import jakarta.validation.Valid;
import org.lessons.springmyphotogallery.model.Category;
import org.lessons.springmyphotogallery.repository.CategoryRepository;
import org.lessons.springmyphotogallery.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

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
        // passo al model un attributo categoryObj per mappare il form su un oggetto di tipo Category
        model.addAttribute("categoryObj", new Category());
        return "categories/index";
    }

    // metodo save per salvare su database l'attributo formCategory
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("categoryObj") Category formCategory, BindingResult bindingResult, Model model) {
        // verifichiamo se ci sono errori
        // se il nome NON è univoco
        if (!isUniqueName(formCategory)) {
            // aggiungo errore nella mappa BindingResult
            bindingResult.addError(new FieldError("categoryObj", "name", formCategory.getName(), false, null, null,
                    "Il nome della categoria deve essere univoco. Esiste già una categoria con questo nome."));
        }
        if (bindingResult.hasErrors()) {
            // per ricreare correttamente la index, oltre a categoryObj, passo al model anche la lista delle categorie
            model.addAttribute("categories", categoryRepository.findAll());
            return "categories/index";
        }
        // dopo la validazione salvo il formCategory
        categoryRepository.save(formCategory);

        return "redirect:/categories";
    }

    // UTILITY METHODS
    // metodo per verificare se su database c'è già una categoria con lo stesso nome della categoria passata come parametro
    private boolean isUniqueName(Category formCategory) {
        Optional<Category> result = categoryRepository.findByName(formCategory.getName());
        return result.isEmpty();
    }
}
