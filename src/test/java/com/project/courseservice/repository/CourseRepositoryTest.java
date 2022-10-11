package com.project.courseservice.repository;


import com.project.courseservice.entity.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseRepositoryTest {


    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Autowired
    CourseRepository courseRepository;

    private static final List<Course> COURSES = Arrays.asList(
            new Course("Algebra"),
            new Course("Database"),
            new Course(".NET"),
            new Course("JAVA")
    );

    @BeforeAll
    void saveCourses() {
        courseRepository.saveAll(COURSES);
    }

    @Test
    void returnAllWhenFindAll() {
        Assertions.assertEquals(COURSES, courseRepository.findAll());
    }

    @Test
    void checkExpected(){
        Course expected = COURSES.get(1);
        Optional<Course> course = courseRepository.findById((long) 2);
        Assertions.assertEquals(expected, course.get());
    }

    @Test
    void returnExisting(){
        Optional<Course> course = courseRepository.findById((long) 3);
        assertTrue(course.isPresent());
    }

    @Test
    void returnNotExisting(){
        Optional<Course> course = courseRepository.findById((long) 8);
        assertFalse(course.isPresent());
    }

    @Test
    void returnSizeWhenOneDeleted(){
        List<Course> before = courseRepository.findAll();
        courseRepository.deleteById((long) 1);
        List<Course> after = courseRepository.findAll();
        assertEquals(before.size() - 1, after.size());
    }

    @Test
    void returnSizeWhenOneAdded(){
        List<Course> before = courseRepository.findAll();
        Course course = new Course("New Course");
        courseRepository.save(course);
        List<Course> after = courseRepository.findAll();
        assertEquals(before.size() + 1, after.size());
    }

    @Test
    void returnNullWhenInvalidAdded() {
        Course course = new Course(null);
        Set<ConstraintViolation<Course>> constraintViolationSet = validator.validate(course);
        assertFalse(constraintViolationSet.isEmpty());
    }

    @Test
    void returnCourseByName() {
        Course expected = COURSES.get(2);
        Course actual = courseRepository.findCourseByName(".NET").get();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void returnNothingWhenAllDeleted() {
        courseRepository.deleteAll();
        Assertions.assertTrue(courseRepository.findAll().isEmpty());
    }
}
