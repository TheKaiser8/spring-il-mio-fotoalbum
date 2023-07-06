package org.lessons.springmyphotogallery.controller;

import org.lessons.springmyphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    // dipende da PhotoRepository
    @Autowired
    private PhotoRepository photoRepository;

    // READ METHODS
    @GetMapping
    public String index() {
        return "photos/index";
    }
}
