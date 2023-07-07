package org.lessons.springmyphotogallery.service;

import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    // metodo che restituisce la lista foto filtrata o no a seconda del parametro OPZIONALE
    public List<Photo> getAllWithOptParam(String searchString) {
        Optional<String> keywordOpt = Optional.ofNullable(searchString); // se il parametro è nullo ritorno un Optional object vuoto
        if (keywordOpt.isEmpty()) {
            return photoRepository.findAll();
        } else {
            return photoRepository.findByTitleContainingIgnoreCase(keywordOpt.get());
        }
    }

    // metodo che restituisce, se presente, la foto presa per id oppure lancia un'eccezione
    public Photo getById(Integer id) throws ResponseStatusException {
        Optional<Photo> photoOpt = photoRepository.findById(id);
        if (photoOpt.isPresent()) {
            return photoOpt.get();
        } else {
            // ritorno un HTTP Status 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con ID " + id + " non è stata trovata");
        }
    }

    // metodo che salva una nuova foto a partire da quella passata come parametro
    public Photo create(Photo photo) {
        // istanzio nuova foto da salvare
        Photo photoToPersist = new Photo();
        // genero il timestamp di createdAt
        photoToPersist.setCreatedAt(LocalDateTime.now());
        // setto tutti i campi che mi interessano
        photoToPersist.setId(photo.getId());
        photoToPersist.setTitle(photo.getTitle());
        photoToPersist.setDescription(photo.getDescription());
        photoToPersist.setUrl(photo.getUrl());
        photoToPersist.setVisible(photo.isVisible());
        // persisto la foto
        return photoRepository.save(photoToPersist);
    }

}
