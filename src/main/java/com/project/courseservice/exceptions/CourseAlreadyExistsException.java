package com.project.courseservice.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {


    public CourseAlreadyExistsException(String name) {
        super(String.format("Course with name %s already exists", name));
    }
}
