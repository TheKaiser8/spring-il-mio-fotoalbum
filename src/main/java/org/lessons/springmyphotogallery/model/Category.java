package org.lessons.springmyphotogallery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il nome è obbligatorio, il campo non può essere vuoto")
    @Size(min = 3, max = 50, message = "Il nome deve avere un numero di caratteri compreso tra 3 e 50")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 500, message = "La descrizione non può superare i 500 caratteri")
    @Column(columnDefinition = "TEXT")
    private String description;

    // RELATIONSHIP ATTRIBUTES
    @JsonIgnore // ignoro le photos per evitare la ricorsione infinita
    @ManyToMany(mappedBy = "categories")
    private List<Photo> photos = new ArrayList<>();

    // GETTERS & SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
