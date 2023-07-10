package org.lessons.springmyphotogallery.api;

import jakarta.validation.Valid;
import org.lessons.springmyphotogallery.exceptions.PhotoNotFoundException;
import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.repository.PhotoRepository;
import org.lessons.springmyphotogallery.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/photos")
public class PhotoRestController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoService photoService;

    // servizio per avere la lista delle foto con parametro opzionale di ricerca
    @GetMapping
    public List<Photo> index(@RequestParam(name = "keyword", required = false) String keyword) {
        return photoService.getAllWithOptParam(keyword);
    }

    // servizio per avere i dettagli della singola foto
    @GetMapping("/{id}")
    public Photo get(@PathVariable Integer id) {
        // cerco la foto su database
        try {
            return photoService.getById(id);
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // servizio per creare una nuova foto
    @PostMapping
    public Photo create(@Valid @RequestBody Photo photo) {
        return photoService.create(photo);
    }

    // servizio per aggiornare/modificare una foto
    @PutMapping("/{id}")
    public Photo update(@PathVariable Integer id, @RequestBody Photo photo) throws PhotoNotFoundException, ResponseStatusException {
        // verifico se esiste la foto da aggiornare/modificare
        try {
            Photo photoToUpdate = photoService.getById(id);
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        // se esiste setto l'id e salvo la foto aggiornata
        photo.setId(id); // setto id per sicurezza (nel caso mi arrivi una foto senza id)
        return photoRepository.save(photo);
    }

    // servizio per cancellare una pizza
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        // verifico se esiste la foto da cancellare
        Photo photoToDelete = null;
        try {
            photoToDelete = photoService.getById(id);
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        photoRepository.delete(photoToDelete);
    }
}
