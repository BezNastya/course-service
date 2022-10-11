package com.project.courseservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Valid
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Course {

    public Course(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Valid
    @NotNull
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="course")
    @JsonIgnore
    @ToString.Exclude
    private Set<GroupCourse> groups;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
