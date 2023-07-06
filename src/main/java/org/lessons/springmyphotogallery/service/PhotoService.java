package org.lessons.springmyphotogallery.service;

import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    // metodo che restituisce la lista foto filtrata o no a seconda del parametro OPZIONALE
    public List<Photo> getAllWithOptParam(Optional<String> keywordOpt) {
        if (keywordOpt.isEmpty()) {
            return photoRepository.findAll();
        } else {
            return photoRepository.findByTitleContainingIgnoreCase(keywordOpt.get());
        }
    }
}
