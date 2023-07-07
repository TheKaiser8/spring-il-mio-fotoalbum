package org.lessons.springmyphotogallery.controller;

import jakarta.validation.Valid;
import org.lessons.springmyphotogallery.messages.AlertMessage;
import org.lessons.springmyphotogallery.messages.AlertMessageType;
import org.lessons.springmyphotogallery.model.Category;
import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.repository.CategoryRepository;
import org.lessons.springmyphotogallery.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    // metodo index che restituisce sia la lista delle categorie che il form per creare o modificare una categoria
    // aggiungo un parametro opzionale con l'id della categoria che voglio modificare
    @GetMapping
    public String index(Model model, @RequestParam("editCategory") Optional<Integer> categoryId) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);

        // definisco un oggetto Category per creare una condizione in cui verifico che form (categoryObj) restituire
        Category categoryObj;
        // se ho il parametro categoryId cerco la categoria corrispondente su database
        if (categoryId.isPresent()) {
            Optional<Category> categoryDb = categoryRepository.findById(categoryId.get());
            // se è presente l'oggetto su db, categoryObj diventa l'oggetto categoryDb
            if (categoryDb.isPresent()) {
                categoryObj = categoryDb.get();
            } else {
                // se NON è presente l'oggetto su db, categoryObj diventa un nuovo oggetto vuoto
                categoryObj = new Category();
            }
        } else {
            // se NON ho il parametro opzionale, categoryObj diventa un nuovo oggetto vuoto
            categoryObj = new Category();
        }
        // passo al model un attributo categoryObj per mappare il form su un oggetto di tipo Category
        model.addAttribute("categoryObj", categoryObj);

        return "categories/index";
    }

    // metodo save per salvare su database l'attributo formCategory
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("categoryObj") Category formCategory, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Integer preFormCategoryId = formCategory.getId(); // ottengo id del form PRIMA del suo salvataggio
        // verifico l'id della formCategory per capire se mi trovo nella create (avrò id == null) o nella edit
        // e verifico che il nome sia univoco
        if (preFormCategoryId == null && !isUniqueName(formCategory)) {
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
        Integer postFormCategoryId = formCategory.getId(); // ottengo id del form DOPO il suo salvataggio
        // creo stringa con confronto tramite operatore ternario che mi permette di personalizzare il messaggio di modifica/creazione
        String action = preFormCategoryId != null && preFormCategoryId.equals(postFormCategoryId) ? " aggiornata" : " creata";
        // aggiungo un messaggio di successo come flash attribute utilizzando un classe CUSTOM per personalizzare i messaggi degli alert
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "La categoria " + "\"" + formCategory.getName() + "\"" + " è stata" + action + "!"));
        return "redirect:/categories";
    }

    // metodo delete per cancellare la categoria da database
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer categoryId, RedirectAttributes redirectAttributes) {
        // prima di eliminare la categoria la dissocio da tutte le categorie
        Optional<Category> result = categoryRepository.findById(categoryId);
        if (result.isEmpty()) {
            // ritorno un HTTP Status 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoria con ID " + categoryId + " non è stata trovata");
        }
        // ottengo l'oggetto categoria da eliminare
        Category categoryToDelete = result.get();
        // itero su ogni foto associata alla categoria per eliminare la categoria
        for (Photo photo : categoryToDelete.getPhotos()) {
            photo.getCategories().remove(categoryToDelete);
        }
        categoryRepository.deleteById(categoryId);
        // aggiungo un messaggio di successo come flash attribute utilizzando un classe CUSTOM per personalizzare i messaggi degli alert
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "La categoria " + "\"" + categoryToDelete.getName() + "\"" + " è stata cancellata!"));
        return "redirect:/categories";
    }

    // UTILITY METHODS
    // metodo per verificare se su database c'è già una categoria con lo stesso nome della categoria passata come parametro
    private boolean isUniqueName(Category formCategory) {
        Optional<Category> result = categoryRepository.findByName(formCategory.getName());
        return result.isEmpty();
    }
}
