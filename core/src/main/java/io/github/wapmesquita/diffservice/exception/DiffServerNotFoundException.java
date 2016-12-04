package io.github.wapmesquita.diffservice.exception;

public class DiffServerNotFoundException extends Exception {

    public DiffServerNotFoundException(Class clazz) {
        super(clazz.getSimpleName() + " not found!");
    }

}
