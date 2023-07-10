package org.lessons.springmyphotogallery.service;

import org.lessons.springmyphotogallery.dto.PhotoForm;
import org.lessons.springmyphotogallery.exceptions.PhotoNotFoundException;
import org.lessons.springmyphotogallery.model.Photo;
import org.lessons.springmyphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    // metodo che restituisce la lista foto visibili, filtrata o no a seconda del parametro OPZIONALE
    public List<Photo> getVisiblePhotosWithOptParam(String searchString) {
        Optional<String> keywordOpt = Optional.ofNullable(searchString); // se il parametro è nullo ritorno un Optional object vuoto
        if (keywordOpt.isEmpty()) {
            return photoRepository.findByIsVisibleTrue();
        } else {
            return photoRepository.findByTitleContainingIgnoreCaseAndIsVisibleTrue(keywordOpt.get());
        }
    }

    // metodo che restituisce, se presente, la foto presa per id oppure lancia un'eccezione
    public Photo getById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> photoOpt = photoRepository.findById(id);
        if (photoOpt.isPresent()) {
            return photoOpt.get();
        } else {
            // ritorno un eccezione CUSTOM
            throw new PhotoNotFoundException("La foto con ID " + id + " non è stata trovata");
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
        photoToPersist.setCategories(photo.getCategories());
        photoToPersist.setUrlUpload(photo.getUrlUpload());
        // persisto la foto
        return photoRepository.save(photoToPersist);
    }

    // crea una nuova foto a partire da un DTO PhotoForm
    public Photo create(PhotoForm photoForm) {
        // converto PhotoForm in una Photo
        Photo photo = mapPhotoFormToPhoto(photoForm);
        // salvo la Photo su database
        return create(photo);
    }

    // metodo per creare un PhotoForm a partire dall'id di una Photo salvata a database
    public PhotoForm getPhotoFormById(Integer id) throws PhotoNotFoundException {
        Photo photo = getById(id);
        return mapPhotoToPhotoForm(photo);
    }

    // metodo per aggiornare una foto
    public Photo update(PhotoForm photoForm) throws PhotoNotFoundException {
        // converto il PhotoForm in Photo
        Photo photo = mapPhotoFormToPhoto(photoForm);
        // cerco la foto su database
        Photo photoDb = getById(photo.getId());
        photoDb.setId(photo.getId());
        photoDb.setTitle(photo.getTitle());
        photoDb.setDescription(photo.getDescription());
        photoDb.setUrlUpload(photo.getUrlUpload());
        photoDb.setVisible(photo.isVisible());
        photoDb.setCategories(photo.getCategories());
        photoDb.setUrlUpload(photo.getUrlUpload());

        return photoRepository.save(photoDb);
    }

    // metodo per convertire un PhotoForm in una Photo
    private Photo mapPhotoFormToPhoto(PhotoForm photoForm) {
        // creo una nuova foto vuota
        Photo photo = new Photo();
        // copio i campi con corrispondenza esatta
        photo.setId(photoForm.getId());
        photo.setTitle(photoForm.getTitle());
        photo.setDescription(photoForm.getDescription());
        photo.setVisible(photoForm.isVisible());
        photo.setCategories(photoForm.getCategories());
        // converto il campo cover
        byte[] urlBytes = multipartFileToByteArray(photoForm.getUrlFile());
        photo.setUrlUpload(urlBytes);

        return photo;
    }

    // metodo per convertire una Photo un in un PhotoForm
    private PhotoForm mapPhotoToPhotoForm(Photo photo) {
        // creo una nuova foto vuota
        PhotoForm photoForm = new PhotoForm();
        // copio i campi con corrispondenza esatta
        photoForm.setId(photo.getId());
        photoForm.setTitle(photo.getTitle());
        photoForm.setDescription(photo.getDescription());
        photoForm.setUrl(photo.getUrl());
        photoForm.setVisible(photo.isVisible());
        photoForm.setCategories(photo.getCategories());

        return photoForm;
    }

    // metodo per convertire un multipart file in byte array
    private byte[] multipartFileToByteArray(MultipartFile mpf) {
        // dichiaro e inizializzo array di byte che devo restituire
        byte[] bytes = null;
        if (mpf != null && !mpf.isEmpty()) {
            try {
                bytes = mpf.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}