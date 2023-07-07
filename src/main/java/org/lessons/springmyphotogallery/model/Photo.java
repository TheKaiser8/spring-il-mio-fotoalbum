package org.lessons.springmyphotogallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photos")
public class Photo {

    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il titolo è obbligatorio, il campo non può essere vuoto")
    @Size(min = 3, max = 100, message = "Il titolo deve avere un numero di caratteri compreso tra 3 e 100")
    @Column(nullable = false)
    private String title;

    @Size(max = 500, message = "La descrizione non può superare i 500 caratteri")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "L''URL della foto è obbligatorio, il campo non può essere vuoto")
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean isVisible;

    // RELATIONSHIP ATTRIBUTES
    @ManyToMany
    @JoinTable(
            name = "photo_category",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    private LocalDateTime createdAt;

    // GETTERS & SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // CUSTOM METHODS
//    public String getFormattedCreatedAt() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM dd 'at' HH:mm");
//        return createdAt.format(formatter);
//    }
}
