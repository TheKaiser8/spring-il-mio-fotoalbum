package org.lessons.springmyphotogallery.repository;

import org.lessons.springmyphotogallery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // metodo per cercare una categoria mediante il nome
    Optional<Category> findByName(String name);
}
