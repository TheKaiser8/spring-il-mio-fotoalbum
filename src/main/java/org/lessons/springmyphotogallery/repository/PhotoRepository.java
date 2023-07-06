package org.lessons.springmyphotogallery.repository;

import org.lessons.springmyphotogallery.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    // QUERY CUSTOM
    // metodo per filtrare le foto il cui titolo contiene una stringa
    List<Photo> findByTitleContainingIgnoreCase(String title);
}
