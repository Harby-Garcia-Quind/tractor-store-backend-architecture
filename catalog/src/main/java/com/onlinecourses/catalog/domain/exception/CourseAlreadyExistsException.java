package com.onlinecourses.catalog.domain.exception;

public class CourseAlreadyExistsException extends RuntimeException {

    public CourseAlreadyExistsException(String courseName) {
        super("Ya existe un curso con el nombre: " + courseName);
    }

}
