package org.lessons.springmyphotogallery.controller;

import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {

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
    
}
