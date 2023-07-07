package org.lessons.springmyphotogallery.controller;

import jakarta.validation.Valid;
import org.lessons.springmyphotogallery.messages.AlertMessage;
import org.lessons.springmyphotogallery.messages.AlertMessageType;
import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.repository.CategoryRepository;
import org.lessons.springmyphotogallery.repository.PhotoRepository;
import org.lessons.springmyphotogallery.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    PhotoService photoService;

    // READ METHODS
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Photo> photos = photoService.getAllWithOptParam(searchString);
        model.addAttribute("photoList", photos);
        // aggiungo un altro attributo al model per mantenere il valore della input dopo l'invio della ricerca filtrata
        model.addAttribute("searchInput", searchString);
        return "photos/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer photoId, Model model) {
        Photo photo = photoService.getById(photoId);
        model.addAttribute("photo", photo);
        return "photos/show";
    }

    // CREATE METHODS
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("photo", new Photo());
        // aggiungo al model la lista delle categorie per popolare le checkbox del form
        getCategoryList(model);
        return "photos/form";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // verifico se in validazione ci sono stati errori
        // se NON ci sono stati errori
        if (!bindingResult.hasErrors()) {
            photoService.create(formPhoto); // salvo la foto su database utilizzando il Service
        }
        // se ci sono stati errori restituisco il form con i campi precompilati
        if (bindingResult.hasErrors()) {
            // aggiungo al model la lista delle categorie per popolare le checkbox del form
            getCategoryList(model);
            return "photos/form";
        }

        // aggiungo un messaggio di successo come flash attribute utilizzando un classe CUSTOM per personalizzare i messaggi degli alert
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "La foto " + "\"" + formPhoto.getTitle() + "\"" + " è stata creata!"));
        // se l'operazione va a buon fine rimando alla lista delle foto
        return "redirect:/photos";
    }

    // EDIT METHODS
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer photoId, Model model) {
        Photo photo = photoService.getById(photoId);
        model.addAttribute("photo", photo);
        // aggiungo al model la lista delle categorie per popolare le checkbox del form
        getCategoryList(model);
        return "photos/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer photoId, @Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // cerco per id la versione vecchia della foto da modificare
        Photo photoToEdit = photoService.getById(photoId);
        // se ci sono stati errori restituisco il form con i campi precompilati
        if (bindingResult.hasErrors()) {
            // aggiungo al model la lista delle categorie per popolare le checkbox del form
            getCategoryList(model);
            return "photos/form";
        }
        // trasferisco su formPhoto tutti i valori dei campi che non sono presenti o non sono modificabili nel form (altrimenti vengono persi)
        formPhoto.setId(photoToEdit.getId());
        formPhoto.setCreatedAt(photoToEdit.getCreatedAt());
        // salvo la foto aggiornata
        photoRepository.save(formPhoto);
        // aggiungo un messaggio di successo come flash attribute utilizzando un classe CUSTOM per personalizzare i messaggi degli alert
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "La foto " + "\"" + formPhoto.getTitle() + "\"" + " è stata aggiornata!"));
        return "redirect:/photos";
    }

    // DELETE METHOD
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer photoId, RedirectAttributes redirectAttributes) {
        // verifico tramite ricerca per id se esiste la foto da cancellare
        Photo photoToDelete = photoService.getById(photoId);
        // cancello la foto da database
        photoRepository.delete(photoToDelete);
        // aggiungo un messaggio di successo come flash attribute utilizzando un classe CUSTOM per personalizzare i messaggi degli alert
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "La foto " + "\"" + photoToDelete.getTitle() + "\"" + " è stata cancellata!"));
        // facciamo la redirect alla lista delle pizze
        return "redirect:/photos";
    }

    // UTILITY METHODS
    // metodo per aggiungere al model la lista delle categorie
    private void getCategoryList(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
    }
}
