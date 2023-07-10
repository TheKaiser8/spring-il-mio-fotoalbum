package org.lessons.springmyphotogallery.controller;

import org.lessons.springmyphotogallery.exceptions.PhotoNotFoundException;
import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    PhotoService photoService;

    // metodo che cerca la foto per id e ne restituisce l'url come immagine della foto
    @GetMapping("/url/{photoId}")
    public ResponseEntity<byte[]> getPhotoCover(@PathVariable Integer photoId) {
        try {
            Photo photo = photoService.getById(photoId);
            // setto il mediatype da restituire (il tipo di file che voglio ottenere dall'array di byte)
            MediaType mediaType = MediaType.IMAGE_JPEG;
            return ResponseEntity.ok().contentType(mediaType).body(photo.getUrlUpload());
        } catch (PhotoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
