package com.project.courseservice.repository;


import com.project.courseservice.entity.Course;
import com.project.courseservice.entity.GroupCourse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupCourseJPATest {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    GroupCourseRepository groupCourseRepository;

    private static final List<Course> COURSES = Collections.singletonList(
            new Course("Algebra")
    );

    private static final List<GroupCourse> GROUPS = Arrays.asList(
            new GroupCourse(COURSES.get(0), (byte) 1),
            new GroupCourse(COURSES.get(0), (byte) 2),
            new GroupCourse(COURSES.get(0), (byte) 1),
            new GroupCourse(COURSES.get(0), (byte) 1)
    );

    @BeforeAll
    void initDb() {
        courseRepository.saveAll(COURSES);
        groupCourseRepository.saveAllAndFlush(GROUPS);
    }

    @Test
    void shouldReturnAll_whenExistingCourse() {
        assertEquals(GROUPS, groupCourseRepository.findAllGroupsCourseByCourse(COURSES.get(0)));
    }

    @Test
    void shouldReturnEmpty_whenEmptyExistingCourse() {
        Course course = new Course("C1");
        courseRepository.save(course);
        assertEquals(Collections.EMPTY_LIST, groupCourseRepository.findAllGroupsCourseByCourse(course));
    }

    @Test
    void shouldReturnExistingGroupCourse_whenExistingId(){
        GroupCourse expected = GROUPS.get(0);
        long id = groupCourseRepository.findAll().get(0).getId();
        Optional<GroupCourse> course = groupCourseRepository.findById(id);
        assertTrue(course.isPresent());
        Assertions.assertEquals(expected.getCourse().getName(), course.get().getCourse().getName());
    }

    @Test
    void shouldReturnEmptyOptional_whenNonExistingId(){
        Optional<GroupCourse> course = groupCourseRepository.findById((long) 8);
        assertFalse(course.isPresent());
    }

    @Test
    void shouldDeleteAnyGroupsCourse_whenNonExistingCourse(){
        List<GroupCourse> expected = groupCourseRepository.findAll();
        Course course = new Course("C2");
        courseRepository.save(course);
        groupCourseRepository.deleteGroupCoursesByCourse(course);
        List<GroupCourse> actual = groupCourseRepository.findAll();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd_whenValidGroupCourse(){
        List<GroupCourse> expected = groupCourseRepository.findAll();
        GroupCourse groupCourse = new GroupCourse(COURSES.get(0), (byte)5);
        groupCourseRepository.save(groupCourse);
        List<GroupCourse> actual = groupCourseRepository.findAll();
        assertEquals(expected.size() + 1, actual.size());
        expected.add(groupCourse);
        assertEquals(expected, actual);
    }

}
