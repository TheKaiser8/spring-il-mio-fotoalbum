package org.lessons.springmyphotogallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.lessons.springmyphotogallery.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class PhotoForm {

    // FIELDS
    private Integer id;

    @NotBlank(message = "Il titolo è obbligatorio, il campo non può essere vuoto")
    @Size(min = 3, max = 100, message = "Il titolo deve avere un numero di caratteri compreso tra 3 e 100")
    private String title;

    @Size(max = 500, message = "La descrizione non può superare i 500 caratteri")
    private String description;

    //    @NotBlank(message = "L''URL della foto è obbligatorio, il campo non può essere vuoto")
    private String url;

    private boolean isVisible;

    //    @NotNull(message = "La foto è obbligatoria, il campo non può essere vuoto")
    private MultipartFile urlFile;

    // RELATIONSHIP ATTRIBUTES
    private List<Category> categories = new ArrayList<>();

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

    public MultipartFile getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(MultipartFile urlFile) {
        this.urlFile = urlFile;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
