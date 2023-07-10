package org.lessons.springmyphotogallery.exceptions;

public class PhotoNotFoundException extends RuntimeException {

    public PhotoNotFoundException(String message) {
        super(message);
    }
}
