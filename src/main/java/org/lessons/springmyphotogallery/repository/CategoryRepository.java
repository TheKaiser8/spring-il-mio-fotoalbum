package org.lessons.springmyphotogallery.repository;

import org.lessons.springmyphotogallery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
