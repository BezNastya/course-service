package com.project.courseservice.dto;

import com.project.courseservice.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseDTO {

    private long id;
    private String name;
    private byte numberOfGroups;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.numberOfGroups = (byte) course.getGroups().size();
    }
}
